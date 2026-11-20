#!/usr/bin/env python3
"""
Deterministic, state-tracked documentation harness orchestrator for FLC-IEC61131-7.

Lifecycle per step:
    PENDING -> RUNNING -> SUCCEEDED | FAILED | SKIPPED

Persists an auditable JSON manifest (default: output/harness_state.json) so runs
can be inspected, filtered, dry-run, or resumed without mutating tracked files.
Under the P1 patch-first policy, OpenCode subagents run read-only and any
unified diff emitted on stdout is captured into output/proposed_patches/.

Usage examples:
    python3 run_all_agents.py
    python3 run_all_agents.py --list-steps
    python3 run_all_agents.py --dry-run
    python3 run_all_agents.py --skip-opencode --skip-thesis
    python3 run_all_agents.py --only general_statistics,generate_diagrams,validate_docs
    python3 run_all_agents.py --resume
"""
from __future__ import annotations

import argparse
import json
import re
import shutil
import subprocess
import sys
import time
from dataclasses import asdict, dataclass, field
from datetime import datetime, timezone
from enum import Enum
from pathlib import Path
from typing import Dict, List, Optional, Sequence, Set

ROOT = Path(__file__).resolve().parent
DEFAULT_STATE_FILE = ROOT / "output" / "harness_state.json"
DEFAULT_LOGS_DIR = ROOT / "output" / "harness_logs"
DEFAULT_PATCHES_DIR = ROOT / "output" / "proposed_patches"

# Current refactored compiler.* modules plus the test simulation harness.
DEFAULT_MODULES: Sequence[str] = (
    "compiler",
    "lexer",
    "parser",
    "ast",
    "generator",
    "repository",
    "simulation",
)


class StepState(str, Enum):
    PENDING = "PENDING"
    RUNNING = "RUNNING"
    SUCCEEDED = "SUCCEEDED"
    FAILED = "FAILED"
    SKIPPED = "SKIPPED"


ALLOWED_TRANSITIONS: Dict[StepState, Set[StepState]] = {
    StepState.PENDING: {StepState.RUNNING, StepState.SKIPPED},
    StepState.RUNNING: {StepState.SUCCEEDED, StepState.FAILED, StepState.SKIPPED},
    StepState.SUCCEEDED: set(),
    StepState.FAILED: set(),
    StepState.SKIPPED: set(),
}


@dataclass(frozen=True)
class StepSpec:
    step_id: str
    description: str
    command: List[str]
    kind: str
    blocking: bool = True
    opencode_command: Optional[str] = None
    opencode_args: List[str] = field(default_factory=list)


@dataclass
class StepRecord:
    step_id: str
    description: str
    kind: str
    blocking: bool
    command: List[str]
    state: StepState = StepState.PENDING
    started_at: Optional[str] = None
    finished_at: Optional[str] = None
    duration_sec: Optional[float] = None
    returncode: Optional[int] = None
    reason: Optional[str] = None
    log_file: Optional[str] = None
    proposed_patch_file: Optional[str] = None
    agent_outcome: Optional[str] = None
    inspected_sources: List[str] = field(default_factory=list)
    evidence: List[dict] = field(default_factory=list)

    def transition_to(
        self,
        new_state: StepState,
        *,
        reason: Optional[str] = None,
        returncode: Optional[int] = None,
        duration_sec: Optional[float] = None,
    ) -> None:
        if new_state not in ALLOWED_TRANSITIONS[self.state]:
            raise ValueError(
                f"Invalid state transition for '{self.step_id}': "
                f"{self.state.value} -> {new_state.value}"
            )
        now = utc_now_iso()
        if new_state == StepState.RUNNING:
            self.started_at = now
        elif new_state in {StepState.SUCCEEDED, StepState.FAILED, StepState.SKIPPED}:
            if self.started_at is None:
                self.started_at = now
            self.finished_at = now
        self.state = new_state
        if reason is not None:
            self.reason = reason
        if returncode is not None:
            self.returncode = returncode
        if duration_sec is not None:
            self.duration_sec = round(duration_sec, 3)


def utc_now_iso() -> str:
    return datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")


def build_opencode_argv(
    opencode_cmd: str,
    opencode_args: Sequence[str],
    mode: str,
) -> List[str]:
    if mode == "command":
        return ["opencode", "run", "--command", opencode_cmd, *opencode_args]
    return ["opencode", "run", f"/{opencode_cmd}", *opencode_args]


def build_pipeline(
    modules: Sequence[str],
    opencode_mode: str = "slash",
    include_legacy_stats: bool = True,
) -> List[StepSpec]:
    steps: List[StepSpec] = [
        StepSpec(
            step_id="general_statistics",
            description="Generate consolidated compiler.* inventory statistics",
            command=["python3", "scripts/generate_general_statistics.py"],
            kind="script",
            blocking=True,
        ),
    ]

    if include_legacy_stats:
        steps.append(
            StepSpec(
                step_id="extract_stats",
                description="Generate legacy statistical charts and doc/stats.json",
                command=["python3", "scripts/extract_stats.py"],
                kind="script",
                blocking=True,
            )
        )

    steps.append(
        StepSpec(
            step_id="generate_diagrams",
            description="Generate Mermaid diagrams in doc/diagrams/*.mmd",
            command=["python3", "scripts/generate_diagrams.py", "all"],
            kind="script",
            blocking=True,
        )
    )

    for pkg in modules:
        clean_pkg = pkg.strip()
        if not clean_pkg:
            continue
        steps.append(
            StepSpec(
                step_id=f"docs_update_{clean_pkg}",
                description=f"Propose module documentation patch for {clean_pkg}",
                command=build_opencode_argv("docs-update", [clean_pkg], opencode_mode),
                kind="opencode",
                blocking=False,
                opencode_command="docs-update",
                opencode_args=[clean_pkg],
            )
        )

    steps.extend(
        [
            StepSpec(
                step_id="synthesize_context",
                description="Synthesize doc/_synthesis_context.json",
                command=["python3", "scripts/synthesize_thesis_context.py"],
                kind="script",
                blocking=True,
            ),
            StepSpec(
                step_id="build_thesis",
                description="Render Mermaid diagrams and compile output/Tesis.docx",
                command=["bash", "scripts/build_thesis.sh"],
                kind="script",
                blocking=True,
            ),
            StepSpec(
                step_id="readme_update",
                description="Propose README.md patch via readme-writer",
                command=build_opencode_argv("readme-update", [], opencode_mode),
                kind="opencode",
                blocking=False,
                opencode_command="readme-update",
                opencode_args=[],
            ),
            StepSpec(
                step_id="javadoc_audit",
                description="Run read-only Javadoc audit via javadoc-updater",
                command=build_opencode_argv("javadoc-audit", [], opencode_mode),
                kind="opencode",
                blocking=False,
                opencode_command="javadoc-audit",
                opencode_args=[],
            ),
            StepSpec(
                step_id="validate_docs",
                description="Validate Markdown files, Mermaid blocks, and assets in doc/",
                command=["python3", "scripts/validate_docs.py"],
                kind="script",
                blocking=True,
            ),
        ]
    )

    return steps


def extract_unified_diff(output_text: str) -> Optional[str]:
    """Extract unified diff content if an OpenCode agent emitted a patch."""
    match = re.search(r"(^diff --git a/.+)", output_text, flags=re.MULTILINE | re.DOTALL)
    if not match:
        return None
    diff_body = match.group(1)
    diff_body = re.sub(r"\n```\s*$", "\n", diff_body)
    if not diff_body.endswith("\n"):
        diff_body += "\n"
    return diff_body


AGENT_RESULT_PREFIX = "HARNESS_RESULT_JSON:"
AGENT_OUTCOMES = {"NO_CHANGES", "PATCH_PROPOSED", "BLOCKED"}
PATCH_METADATA_FIELDS = (
    "id", "objective", "motivation", "scope", "changes",
    "risks", "validation", "dependencies",
)


def validate_agent_output(output_text: str, repo_root: Path) -> dict:
    """Validate an agent's explicit result and return its evidence and patch.

    This checks structural evidence, not the truth of natural-language claims.
    A Git dry-run never applies or commits an agent's proposed changes.
    """
    markers = [line[len(AGENT_RESULT_PREFIX):].strip()
               for line in output_text.splitlines()
               if line.startswith(AGENT_RESULT_PREFIX)]
    if len(markers) != 1:
        raise ValueError("expected exactly one HARNESS_RESULT_JSON line")
    try:
        report = json.loads(markers[0])
    except json.JSONDecodeError as exc:
        raise ValueError(f"invalid agent JSON: {exc}") from exc
    if not isinstance(report, dict) or report.get("outcome") not in AGENT_OUTCOMES:
        raise ValueError("agent outcome must be NO_CHANGES, PATCH_PROPOSED or BLOCKED")
    if not isinstance(report.get("reason"), str) or not report["reason"].strip():
        raise ValueError("agent result must explain its reason")

    outcome = report["outcome"]
    patch = extract_unified_diff(output_text)
    if outcome == "BLOCKED":
        if patch:
            raise ValueError("BLOCKED result cannot include a patch")
        return {"outcome": outcome, "reason": report["reason"], "inspected_sources": [], "evidence": [], "patch": None}

    paths = report.get("inspected_sources")
    evidence = report.get("evidence")
    if not isinstance(paths, list) or not paths or not all(isinstance(p, str) for p in paths):
        raise ValueError("inspected_sources must list at least one source path")
    if not isinstance(evidence, list) or not evidence:
        raise ValueError("evidence must contain at least one cited finding")
    base = repo_root.resolve()
    for path in paths:
        source = (base / path).resolve()
        if source == base or base not in source.parents or not source.is_file():
            raise ValueError(f"inspected source is missing or outside repository: {path}")
    for item in evidence:
        if not isinstance(item, dict) or item.get("path") not in paths or not isinstance(item.get("detail"), str) or not item["detail"].strip():
            raise ValueError("each evidence entry needs an inspected path and explanation")

    if outcome == "NO_CHANGES":
        if patch:
            raise ValueError("NO_CHANGES result cannot include a patch")
    else:
        metadata = report.get("metadata")
        if not isinstance(metadata, dict) or any(
            not isinstance(metadata.get(key), str) or not metadata[key].strip()
            for key in PATCH_METADATA_FIELDS
        ):
            raise ValueError("PATCH_PROPOSED requires all patch metadata fields")
        if not patch:
            raise ValueError("PATCH_PROPOSED requires a unified diff")
        result = subprocess.run(
            ["git", "apply", "--check", "--whitespace=error", "-"],
            cwd=str(base), input=patch, text=True, capture_output=True,
        )
        if result.returncode:
            raise ValueError("proposed patch does not apply: " +
                             (result.stderr or result.stdout).strip()[:400])
    return {"outcome": outcome, "reason": report["reason"],
            "inspected_sources": paths, "evidence": evidence, "patch": patch}


def load_previous_succeeded(state_file: Path) -> Dict[str, StepRecord]:
    if not state_file.exists():
        return {}
    try:
        payload = json.loads(state_file.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return {}
    recovered: Dict[str, StepRecord] = {}
    for item in payload.get("steps", []):
        if item.get("state") == StepState.SUCCEEDED.value and "step_id" in item:
            rec = StepRecord(
                step_id=item["step_id"],
                description=item.get("description", ""),
                kind=item.get("kind", "script"),
                blocking=bool(item.get("blocking", True)),
                command=list(item.get("command", [])),
                state=StepState.SUCCEEDED,
                started_at=item.get("started_at"),
                finished_at=item.get("finished_at"),
                duration_sec=item.get("duration_sec"),
                returncode=item.get("returncode", 0),
                reason=item.get("reason") or "resumed-from-previous-run",
                log_file=item.get("log_file"),
                proposed_patch_file=item.get("proposed_patch_file"),
                agent_outcome=item.get("agent_outcome"),
                inspected_sources=list(item.get("inspected_sources") or []),
                evidence=list(item.get("evidence") or []),
            )
            recovered[rec.step_id] = rec
    return recovered


def persist_manifest(
    state_file: Path,
    run_id: str,
    started_at: str,
    opencode_available: bool,
    records: Sequence[StepRecord],
) -> None:
    state_file.parent.mkdir(parents=True, exist_ok=True)
    counts = {s.value: 0 for s in StepState}
    for rec in records:
        counts[rec.state.value] += 1

    manifest = {
        "run_id": run_id,
        "started_at": started_at,
        "updated_at": utc_now_iso(),
        "repo_root": str(ROOT),
        "opencode_available": opencode_available,
        "summary": counts,
        "steps": [
            {
                **asdict(rec),
                "state": rec.state.value,
            }
            for rec in records
        ],
    }
    state_file.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")


def execute_step(
    spec: StepSpec,
    rec: StepRecord,
    *,
    logs_dir: Path,
    patches_dir: Path,
    capture_logs: bool,
) -> None:
    print(f"\n{'=' * 68}")
    print(f"[{StepState.RUNNING.value}] {spec.step_id}: {spec.description}")
    print(f"  $ {' '.join(spec.command)}")
    rec.transition_to(StepState.RUNNING)

    t0 = time.monotonic()
    try:
        proc = subprocess.run(
            spec.command,
            cwd=str(ROOT),
            text=True,
            capture_output=capture_logs or spec.kind == "opencode",
        )
    except FileNotFoundError as exc:
        elapsed = time.monotonic() - t0
        if spec.kind == "opencode":
            rec.transition_to(
                StepState.SKIPPED,
                reason=f"opencode-binary-not-found ({exc.filename})",
                duration_sec=elapsed,
            )
            print(f"[{StepState.SKIPPED.value}] {spec.step_id}: {rec.reason}")
            return
        rec.transition_to(
            StepState.FAILED,
            reason=f"executable-not-found ({exc.filename})",
            returncode=127,
            duration_sec=elapsed,
        )
        print(f"[{StepState.FAILED.value}] {spec.step_id}: {rec.reason}")
        return

    elapsed = time.monotonic() - t0

    if capture_logs:
        combined = ""
        if proc.stdout:
            print(proc.stdout, end="" if proc.stdout.endswith("\n") else "\n")
            combined += proc.stdout
        if proc.stderr:
            print(proc.stderr, end="" if proc.stderr.endswith("\n") else "\n", file=sys.stderr)
            combined += "\n--- STDERR ---\n" + proc.stderr

        logs_dir.mkdir(parents=True, exist_ok=True)
        log_path = logs_dir / f"{spec.step_id}.log"
        log_path.write_text(combined, encoding="utf-8")
        rec.log_file = str(log_path.relative_to(ROOT))

    if spec.kind == "opencode" and proc.returncode == 0:
        try:
            agent_result = validate_agent_output(proc.stdout or "", ROOT)
        except (ValueError, OSError) as exc:
            rec.transition_to(StepState.FAILED,
                              reason=f"invalid-agent-result: {exc}",
                              returncode=1, duration_sec=elapsed)
            print(f"[{StepState.FAILED.value}] {spec.step_id}: {rec.reason}")
            return
        rec.agent_outcome = agent_result["outcome"]
        rec.inspected_sources = agent_result["inspected_sources"]
        rec.evidence = agent_result["evidence"]
        if rec.agent_outcome == "BLOCKED":
            rec.transition_to(StepState.FAILED,
                              reason="agent-blocked: " + agent_result["reason"],
                              returncode=1, duration_sec=elapsed)
            print(f"[{StepState.FAILED.value}] {spec.step_id}: {rec.reason}")
            return
        if rec.agent_outcome == "PATCH_PROPOSED":
            patches_dir.mkdir(parents=True, exist_ok=True)
            patch_path = patches_dir / f"{spec.step_id}.patch"
            patch_path.write_text(agent_result["patch"], encoding="utf-8")
            rec.proposed_patch_file = str(patch_path.relative_to(ROOT))
            print(f"  -> Validated proposed patch: {rec.proposed_patch_file}")

    if proc.returncode == 0:
        rec.transition_to(
            StepState.SUCCEEDED,
            reason=("agent-" + rec.agent_outcome.lower()) if rec.agent_outcome else "ok",
            returncode=0,
            duration_sec=elapsed,
        )
        print(f"[{StepState.SUCCEEDED.value}] {spec.step_id} ({rec.duration_sec}s)")
    else:
        rec.transition_to(
            StepState.FAILED,
            reason=f"exit-code-{proc.returncode}",
            returncode=proc.returncode,
            duration_sec=elapsed,
        )
        print(f"[{StepState.FAILED.value}] {spec.step_id}: {rec.reason} ({rec.duration_sec}s)")


def parse_args(argv: Optional[Sequence[str]] = None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Deterministic state-tracked orchestrator for the FLC documentation harness."
    )
    parser.add_argument(
        "--modules",
        default=",".join(DEFAULT_MODULES),
        help="Comma-separated module names for /docs-update (default: %(default)s)",
    )
    parser.add_argument(
        "--only",
        default=None,
        help="Comma-separated list of step IDs to execute; all others are marked SKIPPED.",
    )
    parser.add_argument(
        "--from-step",
        default=None,
        help="Start execution at this step ID; earlier steps are marked SKIPPED.",
    )
    parser.add_argument(
        "--resume",
        action="store_true",
        help="Preserve SUCCEEDED steps from --state-file and execute remaining steps.",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Plan and validate step transitions without executing external commands.",
    )
    parser.add_argument(
        "--skip-opencode",
        action="store_true",
        help="Skip steps requiring the OpenCode CLI.",
    )
    parser.add_argument(
        "--skip-thesis",
        action="store_true",
        help="Skip thesis DOCX compilation step (build_thesis).",
    )
    parser.add_argument(
        "--skip-legacy-stats",
        action="store_true",
        help="Skip legacy scripts/extract_stats.py step.",
    )
    parser.add_argument(
        "--continue-on-error",
        action="store_true",
        help="Continue executing independent steps even if a blocking step fails.",
    )
    parser.add_argument(
        "--opencode-mode",
        choices=("slash", "command"),
        default="slash",
        help="OpenCode invocation format: 'slash' (/cmd arg) or 'command' (--command cmd arg).",
    )
    parser.add_argument(
        "--state-file",
        type=Path,
        default=DEFAULT_STATE_FILE,
        help="Path to JSON state manifest (default: output/harness_state.json).",
    )
    parser.add_argument(
        "--no-capture-logs",
        action="store_true",
        help="Stream subprocess output directly without writing output/harness_logs/*.log.",
    )
    parser.add_argument(
        "--list-steps",
        action="store_true",
        help="Print ordered pipeline step IDs and exit.",
    )
    return parser.parse_args(argv)


def main(argv: Optional[Sequence[str]] = None) -> int:
    args = parse_args(argv)
    modules = [m.strip() for m in args.modules.split(",") if m.strip()]
    specs = build_pipeline(
        modules=modules,
        opencode_mode=args.opencode_mode,
        include_legacy_stats=not args.skip_legacy_stats,
    )

    if args.list_steps:
        for idx, spec in enumerate(specs, 1):
            print(f"{idx:02d}. {spec.step_id:<26} [{spec.kind:<8}] {spec.description}")
        return 0

    known_ids = {s.step_id for s in specs}
    only_ids: Optional[Set[str]] = None
    if args.only:
        only_ids = {s.strip() for s in args.only.split(",") if s.strip()}
        unknown = sorted(only_ids - known_ids)
        if unknown:
            print(f"ERROR: Unknown step IDs in --only: {', '.join(unknown)}", file=sys.stderr)
            return 2

    if args.from_step and args.from_step not in known_ids:
        print(f"ERROR: Unknown step ID in --from-step: {args.from_step}", file=sys.stderr)
        return 2

    state_file = args.state_file if args.state_file.is_absolute() else (ROOT / args.state_file)
    resumed_map = load_previous_succeeded(state_file) if args.resume else {}

    opencode_available = shutil.which("opencode") is not None
    run_id = datetime.now(timezone.utc).strftime("run-%Y%m%dT%H%M%SZ")
    run_started_at = utc_now_iso()

    records: List[StepRecord] = []
    for spec in specs:
        previous = resumed_map.get(spec.step_id)
        # A command change invalidates the previous step result.
        # This does not yet detect source or tool-version changes.
        if (previous is not None and previous.command == spec.command
                and (spec.kind != "opencode" or previous.agent_outcome in
                     {"NO_CHANGES", "PATCH_PROPOSED"})):
            records.append(previous)
        else:
            records.append(
                StepRecord(
                    step_id=spec.step_id,
                    description=spec.description,
                    kind=spec.kind,
                    blocking=spec.blocking,
                    command=list(spec.command),
                )
            )

    persist_manifest(state_file, run_id, run_started_at, opencode_available, records)

    reached_from_step = args.from_step is None
    aborted_by_step: Optional[str] = None

    for spec, rec in zip(specs, records):
        if rec.state == StepState.SUCCEEDED:
            print(f"[{StepState.SUCCEEDED.value}] {spec.step_id} (resumed)")
            continue

        if not reached_from_step:
            if spec.step_id == args.from_step:
                reached_from_step = True
            else:
                rec.transition_to(StepState.SKIPPED, reason=f"before-from-step:{args.from_step}")
                persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
                continue

        if aborted_by_step is not None:
            rec.transition_to(StepState.SKIPPED, reason=f"blocked-by-failed-step:{aborted_by_step}")
            persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
            continue

        if only_ids is not None and spec.step_id not in only_ids:
            rec.transition_to(StepState.SKIPPED, reason="filtered-by-only")
            persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
            continue

        if args.skip_thesis and spec.step_id == "build_thesis":
            rec.transition_to(StepState.SKIPPED, reason="skipped-by-flag:--skip-thesis")
            persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
            continue

        if spec.kind == "opencode" and args.skip_opencode:
            rec.transition_to(StepState.SKIPPED, reason="skipped-by-flag:--skip-opencode")
            persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
            continue

        if spec.kind == "opencode" and not opencode_available and not args.dry_run:
            rec.transition_to(StepState.SKIPPED, reason="opencode-cli-not-found")
            print(f"[{StepState.SKIPPED.value}] {spec.step_id}: opencode CLI not found on PATH")
            persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
            continue

        if args.dry_run:
            rec.transition_to(StepState.SKIPPED, reason="dry-run")
            print(f"[DRY-RUN -> {StepState.SKIPPED.value}] {spec.step_id}: {' '.join(spec.command)}")
            persist_manifest(state_file, run_id, run_started_at, opencode_available, records)
            continue

        execute_step(
            spec,
            rec,
            logs_dir=DEFAULT_LOGS_DIR,
            patches_dir=DEFAULT_PATCHES_DIR,
            capture_logs=not args.no_capture_logs,
        )
        persist_manifest(state_file, run_id, run_started_at, opencode_available, records)

        if rec.state == StepState.FAILED and spec.blocking and not args.continue_on_error:
            aborted_by_step = spec.step_id

    print(f"\n{'=' * 68}")
    print(f"HARNESS RUN SUMMARY ({run_id})")
    for rec in records:
        detail = f" ({rec.reason})" if rec.reason and rec.reason != "ok" else ""
        print(f"  - {rec.step_id:<26} {rec.state.value:<10}{detail}")
    try:
        rel_state: object = state_file.relative_to(ROOT)
    except ValueError:
        rel_state = state_file
    print(f"Manifest written to: {rel_state}")

    any_failed = any(r.state == StepState.FAILED for r in records)
    # Skips caused by missing OpenCode are not user-requested skips.
    unexpected_skip = any(
        r.state == StepState.SKIPPED
        and (
            r.reason == "opencode-cli-not-found"
            or (r.reason or "").startswith("opencode-binary-not-found")
        )
        for r in records
    )
    return 1 if any_failed or unexpected_skip else 0


if __name__ == "__main__":
    sys.exit(main())
