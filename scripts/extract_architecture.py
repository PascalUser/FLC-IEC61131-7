#!/usr/bin/env python3
"""Extract a deterministic, evidence-backed Java architecture inventory.

This lightweight scanner identifies Java package declarations, explicit imports,
and top-level type declarations. It is NOT a Java parser: it does not infer
runtime calls, implicit same-package dependencies, or semantic type bindings.
Only imports resolved to packages present in the scanned source trees become
internal dependency edges. Generated files are included when present.
"""
from __future__ import annotations

import argparse
import json
import re
import sys
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
SOURCE_ROOTS = {
    "production": Path("src/main/java"),
    "test": Path("src/test/java"),
}
DEFAULT_OUTPUT = Path("output/architecture_inventory.json")
PACKAGE_PATTERN = re.compile(r"^\s*package\s+([\w.]+)\s*;", re.MULTILINE)
IMPORT_PATTERN = re.compile(
    r"^\s*import\s+(static\s+)?([\w.]+)(\.\*)?\s*;", re.MULTILINE
)
TYPE_OR_BRACE = re.compile(
    r"[{}]|(?<![\w$])(@interface|class|interface|enum|record)\s+([A-Za-z_$][\w$]*)\b"
)


def without_comments_and_literals(source: str) -> str:
    """Mask Java comments, string literals and char literals, retaining newlines."""
    output = list(source)
    i = 0
    length = len(source)
    while i < length:
        start = i
        if source.startswith("//", i):
            end = source.find("\n", i)
            i = length if end == -1 else end
        elif source.startswith("/*", i):
            end = source.find("*/", i + 2)
            i = length if end == -1 else end + 2
        elif source[i] in ('"', "'"):
            quote = source[i]
            i += 1
            while i < length:
                if source[i] == "\\":
                    i += 2
                elif source[i] == quote:
                    i += 1
                    break
                else:
                    i += 1
            i = min(i, length)
        else:
            i += 1
            continue
        for pos in range(start, i):
            if source[pos] not in "\r\n":
                output[pos] = " "
    return "".join(output)


def top_level_types(clean_source: str, package: str) -> list[dict[str, str]]:
    """Read declarations only at brace depth zero (not nested classes)."""
    depth = 0
    result = []
    for match in TYPE_OR_BRACE.finditer(clean_source):
        token = match.group(0)
        if token == "{":
            depth += 1
        elif token == "}":
            depth -= 1
            if depth < 0:
                raise ValueError("Unbalanced Java braces")
        elif depth == 0:
            kind, name = match.group(1), match.group(2)
            result.append({
                "name": name,
                "kind": "annotation" if kind == "@interface" else kind,
                "qualified_name": f"{package}.{name}" if package else name,
            })
    if depth != 0:
        raise ValueError("Unbalanced Java braces")
    return result


def scan_source(root: Path, rel_root: Path, scope: str) -> list[dict]:
    base = root / rel_root
    if not base.is_dir():
        raise ValueError(f"Missing {scope} source root: {rel_root.as_posix()}")
    records = []
    for path in sorted(base.rglob("*.java")):
        if path.name in {"package-info.java", "module-info.java"}:
            continue
        rel_path = path.relative_to(root).as_posix()
        cleaned = without_comments_and_literals(path.read_text(encoding="utf-8"))
        found = PACKAGE_PATTERN.search(cleaned)
        package = found.group(1) if found else ""
        expected = ".".join(path.parent.relative_to(base).parts)
        if package != expected:
            raise ValueError(
                f"Package/path mismatch in {rel_path}: declared {package!r}, expected {expected!r}"
            )
        imports = []
        for match in IMPORT_PATTERN.finditer(cleaned):
            imports.append({
                "name": match.group(2) + (".*" if match.group(3) else ""),
                "static": bool(match.group(1)),
            })
        imports.sort(key=lambda item: (item["name"], item["static"]))
        try:
            types = top_level_types(cleaned, package)
        except ValueError as exc:
            raise ValueError(f"{rel_path}: {exc}") from exc
        records.append({
            "path": rel_path,
            "scope": scope,
            "package": package,
            "imports": imports,
            "types": types,
        })
    return records


def resolve_package(import_name: str, packages: set[str]) -> str | None:
    """Match the longest known internal package prefix, not external imports."""
    full = import_name.removesuffix(".*")
    matches = [p for p in packages if p and (full == p or full.startswith(p + "."))]
    return max(matches, key=len) if matches else None


def build_inventory(root: Path) -> dict:
    files = []
    for scope, rel_root in SOURCE_ROOTS.items():
        files.extend(scan_source(root, rel_root, scope))
    files.sort(key=lambda item: item["path"])

    packages_by_scope: dict[str, set[str]] = defaultdict(set)
    for record in files:
        packages_by_scope[record["scope"]].add(record["package"])
    all_packages = set().union(*packages_by_scope.values()) if packages_by_scope else set()
    packages = [
        {"scope": scope, "name": name,
         "source_files": [f["path"] for f in files if f["scope"] == scope and f["package"] == name]}
        for scope in SOURCE_ROOTS
        for name in sorted(packages_by_scope[scope])
    ]

    edge_evidence: dict[tuple[str, str, str, str], set[str]] = defaultdict(set)
    for record in files:
        for imp in record["imports"]:
            target = resolve_package(imp["name"], all_packages)
            if target is None or target == record["package"]:
                continue
            # Prefer production for imports of a package present in both trees.
            target_scope = "production" if target in packages_by_scope["production"] else "test"
            edge = (record["scope"], record["package"], target_scope, target)
            edge_evidence[edge].add(record["path"])
    dependencies = [
        {"from_scope": src_scope, "from_package": src,
         "to_scope": dst_scope, "to_package": dst,
         "evidence_files": sorted(evidence)}
        for (src_scope, src, dst_scope, dst), evidence in sorted(edge_evidence.items())
    ]
    return {
        "schema_version": 1,
        "source_roots": {scope: path.as_posix() for scope, path in SOURCE_ROOTS.items()},
        "method": "Java declaration and explicit-import scanning; not semantic resolution",
        "limitations": [
            "Only top-level type declarations are inventoried; nested types are excluded.",
            "Dependency edges are based on explicit imports, not inferred runtime calls.",
            "Implicit same-package references and fully qualified inline references are not resolved.",
            "When both scopes declare a target package, an import resolves to production.",
        ],
        "packages": packages,
        "files": files,
        "dependencies": dependencies,
    }


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output", type=Path, default=DEFAULT_OUTPUT,
                        help=f"JSON path relative to repository root (default: {DEFAULT_OUTPUT})")
    parser.add_argument("--check", action="store_true",
                        help="Compare the existing JSON to the current source without writing it")
    args = parser.parse_args(argv)
    output = args.output if args.output.is_absolute() else ROOT / args.output
    try:
        inventory = build_inventory(ROOT)
        serialized = json.dumps(inventory, ensure_ascii=False, indent=2) + "\n"
        if args.check:
            if not output.is_file() or output.read_text(encoding="utf-8") != serialized:
                print(f"Inventory missing or stale: {output}", file=sys.stderr)
                return 1
            print(f"Inventory is current: {output}")
            return 0
        output.parent.mkdir(parents=True, exist_ok=True)
        output.write_text(serialized, encoding="utf-8")
        print(f"Wrote {output} ({len(inventory['files'])} files, "
              f"{len(inventory['packages'])} packages, "
              f"{len(inventory['dependencies'])} import dependencies)")
        return 0
    except (OSError, UnicodeError, ValueError) as exc:
        print(f"Architecture inventory failed: {exc}", file=sys.stderr)
        return 2


if __name__ == "__main__":
    sys.exit(main())
