#!/usr/bin/env python3
"""Read-only baseline inventory for HARNESS modernization (patch 0041).

This is an evidence inventory, not proof that an unreferenced file is unused.
Never reads .env values, invokes generators, deletes files, or changes Git.
Only its designated JSON report under output/ may be written.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import re
import subprocess
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUTPUT = Path("output/repository_baseline.json")
CONFIG_FILES = (
    "Dockerfile", "docker-compose.yml", "entrypoint.sh", ".gitignore",
    ".dockerignore", ".env.example", "requirements.txt", "opencode.json",
    "pom.xml", "README.md", "doc/HARNESS.md", "run_all_agents.py",
)
DOC_FILES = ("README.md", "doc/HARNESS.md")
# Explicit backtick-wrapped source/script paths, not arbitrary prose.
LITERAL_REF = re.compile(r"`((?:scripts/[\w./-]+\.py|src/(?:main|test)/java/[\w./-]+))`")
# Shell command references to local scripts, including unquoted ones.
PYTHON_CMD = re.compile(r"\bpython3?\s+((?:\./)?scripts/[\w.-]+\.py)\b")
# References from source code are candidates, not a proof of runtime use.
SCRIPT_PATH = re.compile(r"(?<![\w/])scripts/([\w.-]+\.py)\b")
# Environment variable names only; never consume actual .env content.
ENV_VAR = re.compile(r"\$\{([A-Za-z_][A-Za-z_0-9]*)(?::[-?][^}]*)?\}")
SUFFIXES = {".py", ".md", ".txt", ".sh", ".json", ".xml", ".yaml", ".yml", ".toml", ".properties", ".mmd", ".java", ".flex", ".y", ".js", ".ts", ".mjs"}
EXACT_NAMES = {"Dockerfile", "docker-compose.yml", "entrypoint.sh", ".gitignore", ".dockerignore", ".env.example", "requirements.txt", "Makefile", "pom.xml"}
MAX_BYTES = 1_000_000


def tracked_paths(root: Path) -> list[str]:
    """Use Git's index as inventory; never traverse .git or untracked secrets."""
    run = subprocess.run(["git", "-C", str(root), "ls-files", "-z"],
                         capture_output=True, check=False)
    if run.returncode != 0:
        raise RuntimeError("git ls-files failed; run this inside a Git working tree")
    return sorted(p.decode("utf-8", "surrogateescape") for p in run.stdout.split(b"\0") if p)


def readable_text(root: Path, path: str) -> str | None:
    """Read small tracked text files, never .env or other credential containers."""
    if path == ".env" or path.startswith(".git/"):
        return None
    file = root / path
    if not file.is_file() or file.is_symlink():
        return None
    if file.suffix not in SUFFIXES and file.name not in EXACT_NAMES and not path.startswith(".opencode/agents/"):
        return None
    try:
        if file.stat().st_size > MAX_BYTES:
            return None
        return file.read_text(encoding="utf-8")
    except (OSError, UnicodeError):
        return None


def fingerprint(root: Path, path: str) -> str | None:
    file = root / path
    if not file.is_file() or file.is_symlink():
        return None
    try:
        if file.stat().st_size > MAX_BYTES:
            return None
        return hashlib.sha256(file.read_bytes()).hexdigest()
    except OSError:
        return None


def finding(code: str, severity: str, path: str, line: int | None, detail: str,
            *, status: str = "por_verificar", related: str | None = None) -> dict:
    data = {"code": code, "severity": severity, "status": status,
            "path": path, "detail": detail}
    if line is not None:
        data["line"] = line
    if related is not None:
        data["related_path"] = related
    return data


def env_names(text: str) -> list[str]:
    """Extract .env.example keys without persisting values or comments."""
    return sorted(set(re.findall(r"^\s*([A-Za-z_][A-Za-z_0-9]*)\s*=", text, re.MULTILINE)))


def audit(root: Path) -> dict:
    """Produce deterministic evidence grounded in the Git index and local files."""
    root = root.resolve()
    tracked = tracked_paths(root)
    tracked_set = set(tracked)
    texts = {p: value for p in tracked if (value := readable_text(root, p)) is not None}
    findings: list[dict] = []

    for path in CONFIG_FILES:
        if path not in tracked_set:
            findings.append(finding("MISSING_CONFIGURATION_FILE", "info", path, None,
                                    "Configuration file is not tracked; verify if needed for this project."))

    # Inspect textual references in user-facing documentation; a source path that
    # doesn't exist is a verifiable broken literal reference. Other claims remain unknown.
    for path in DOC_FILES:
        for line_no, line in enumerate(texts.get(path, "").splitlines(), 1):
            candidates = {match.group(1).removeprefix("./") for match in LITERAL_REF.finditer(line)}
            candidates.update(match.group(1).removeprefix("./") for match in PYTHON_CMD.finditer(line))
            for reference in sorted(candidates):
                if not (root / reference).exists():
                    findings.append(finding("MISSING_DOCUMENTED_PATH", "warning", path, line_no,
                                            "Documented literal path or command target does not exist.",
                                            status="confirmado", related=reference))

    # A no-longer-existing script may also be mentioned by a tracked script or
    # by an agent prompt. It is only a candidate for repair, never a deletion.
    script_refs: dict[str, list[dict]] = defaultdict(list)
    for path, body in texts.items():
        for line_no, line in enumerate(body.splitlines(), 1):
            for match in SCRIPT_PATH.finditer(line):
                target = "scripts/" + match.group(1)
                if target == path:  # self-references do not indicate another consumer
                    continue
                script_refs[target].append({"path": path, "line": line_no})
    for path, refs in sorted(script_refs.items()):
        if path not in tracked_set and not (root / path).exists():
            example = refs[0]
            findings.append(finding("MISSING_SCRIPT_REFERENCE", "warning", example["path"],
                                    example["line"], "Tracked source refers to a script that does not exist.",
                                    status="confirmado", related=path))

    # Suspected unconsumed scripts are only leads; imports, dynamic dispatch,
    # and non-repository callers are not decidable by a textual search.
    candidates = []
    for path in tracked:
        if path.startswith("scripts/") and path.endswith(".py") and "/tests/" not in path:
            refs = script_refs.get(path, [])
            if not refs:
                candidates.append({"path": path, "status": "por_verificar",
                                   "reason": "No literal scripts/<name>.py reference in scanned tracked text; dynamic/import use not excluded.",
                                   "action": "review_only_no_deletion"})

    compose = texts.get("docker-compose.yml", "")
    dockerfile = texts.get("Dockerfile", "")
    if re.search(r"^\s*env_file\s*:\s*", compose, re.MULTILINE) and re.search(r"(?:\./)?\.env:/", compose):
        findings.append(finding("REDUNDANT_ENV_MOUNT_CANDIDATE", "warning", "docker-compose.yml", None,
                                "Compose uses env_file and also bind-mounts .env; review necessity and secret exposure."))
    if re.search(r"pip(?:3)?\s+install\b[^\n]*\bmatplotlib\b", dockerfile) and "requirements.txt" in tracked_set:
        findings.append(finding("DEPENDENCY_SOURCE_DUPLICATION", "warning", "Dockerfile", None,
                                "Dockerfile installs matplotlib independently of requirements.txt; verify dependency policy."))
    if ".dockerignore" not in tracked_set:
        findings.append(finding("DOCKERIGNORE_ABSENT", "info", ".dockerignore", None,
                                "No tracked .dockerignore; inspect build-context exposure and size."))
    if "scripts/generate_charts.py" in tracked_set and "scripts/extract_stats.py" not in tracked_set:
        # Keep this heuristic explicit, not claim that a wrapper is unused.
        body = texts.get("scripts/generate_charts.py", "")
        if "extract_stats.py" in body:
            findings.append(finding("BROKEN_CHART_WRAPPER", "warning", "scripts/generate_charts.py", None,
                                    "Wrapper references absent scripts/extract_stats.py; inspect runtime uses.",
                                    status="confirmado", related="scripts/extract_stats.py"))
    if "doc/stats.json" in tracked_set and "doc/stats_general.json" in tracked_set:
        findings.append(finding("MULTIPLE_STATISTICS_SNAPSHOTS", "info", "doc/stats.json", None,
                                "Two versioned statistics files exist; audit their consumers and provenance before any removal."))

    example_text = texts.get(".env.example", "")
    configured = env_names(example_text)
    used_names = sorted({x for p, body in texts.items() if p != ".env.example" for x in ENV_VAR.findall(body)})
    # Observed usage only: getenv and external tools also consume variables.
    configuration = {"declared_env_example_keys": configured,
                     "shell_interpolations_in_tracked_text": used_names,
                     "note": "Static partial scan; not proof of missing or unused environment variables."}
    inventory = {"tracked_file_count": len(tracked),
                 "tracked_paths": tracked,
                 "scanned_text_file_count": len(texts),
                 "tracked_by_area": {prefix: sum(p.startswith(prefix) for p in tracked)
                                     for prefix in ("src/", "scripts/", "doc/", "config/", "handoff/", ".opencode/")},
                 "configuration": {p: {"tracked": p in tracked_set,
                                        "sha256": fingerprint(root, p) if p in tracked_set else None}
                                   for p in CONFIG_FILES},
                 "script_reference_count": len(script_refs),
                 "script_references": {path: refs for path, refs in sorted(script_refs.items())}}
    findings.sort(key=lambda x: (x["code"], x["path"], x.get("line", -1), x.get("related_path", "")))
    return {"schema_version": 1, "purpose": "read_only_baseline_not_semantic_validation",
            "inventory": inventory, "environment": configuration,
            "findings": findings, "cleanup_candidates": candidates,
            "limits": ["Static references cannot establish that a file is safe to delete.",
                       "Documentation claims, charts and metrics require later source-backed audits.",
                       "Local untracked files and real .env secrets are intentionally excluded.",
                       "No Docker build, package installation, Maven build or agent calls were executed."]}


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=ROOT)
    parser.add_argument("--output", type=Path, default=DEFAULT_OUTPUT,
                        help="Relative output must be under root/output; absolute output must also be under root/output")
    args = parser.parse_args(argv)
    root = args.root.resolve()
    output = (root / args.output).resolve() if not args.output.is_absolute() else args.output.resolve()
    if not output.is_relative_to(root / "output") or output.suffix != ".json":
        parser.error("report destination must be a JSON file under the repository's output/ directory")
    report = audit(root)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(report, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    counts = defaultdict(int)
    for finding_ in report["findings"]:
        counts[finding_["severity"]] += 1
    print(f"Repository baseline: {report['inventory']['tracked_file_count']} tracked files, "
          f"{len(report['findings'])} findings ({dict(sorted(counts.items()))}), "
          f"{len(report['cleanup_candidates'])} cleanup candidates (review only)")
    print("Report:", output.relative_to(root))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
