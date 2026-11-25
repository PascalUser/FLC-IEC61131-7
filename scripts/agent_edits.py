"""Deterministic, read-only unified diffs from exact agent-proposed text edits.

The agent describes intent and exact anchors. Python owns hunk locations,
context, and counts. A stale or ambiguous anchor is a rejected proposal, not a
best-effort edit. The repository itself is never written by this module.
"""
from __future__ import annotations

import difflib
import re
from pathlib import Path

MAX_EDITS = 40
MAX_PATCH_BYTES = 512_000
SAFE_PATH = re.compile(r"^[A-Za-z0-9_][A-Za-z0-9_./-]*$")


def build_patch_from_edits(edits: object, repo_root: Path) -> str:
    """Build an applicable unified diff using original, unique text anchors.

    Edit shape: {"path": "repo/relative.ext", "old": "exact text", "new": "text"}.
    All matches refer to *original* file contents. Overlap, ambiguity, absent
    paths, no-ops and non-newline-terminated files are deliberately rejected.
    """
    if not isinstance(edits, list) or not 1 <= len(edits) <= MAX_EDITS:
        raise ValueError(f"edits must contain 1..{MAX_EDITS} replacements")
    root = repo_root.resolve()
    grouped: dict[str, list[tuple[int, int, str]]] = {}
    sources: dict[str, str] = {}
    for idx, edit in enumerate(edits, 1):
        if not isinstance(edit, dict) or set(edit) != {"path", "old", "new"}:
            raise ValueError(f"edit {idx} must contain only path, old and new")
        path, old, new = edit["path"], edit["old"], edit["new"]
        if (not isinstance(path, str) or not SAFE_PATH.fullmatch(path)
                or path.startswith("/") or ".." in Path(path).parts
                or path.startswith(".git/")):
            raise ValueError(f"edit {idx}: unsafe repository path")
        if (not isinstance(old, str) or not old or not isinstance(new, str)
                or old == new or "\x00" in old or "\x00" in new):
            raise ValueError(f"edit {idx}: old/new must be distinct valid text")
        source_path = (root / path).resolve()
        if root not in source_path.parents or not source_path.is_file():
            raise ValueError(f"edit {idx}: path missing or outside repository: {path}")
        if path not in sources:
            try:
                original = source_path.read_text(encoding="utf-8")
            except (OSError, UnicodeError) as exc:
                raise ValueError(f"edit {idx}: file is not readable UTF-8: {path}") from exc
            if not original.endswith("\n"):
                raise ValueError(f"edit {idx}: file must end with a newline: {path}")
            sources[path] = original
        original = sources[path]
        count = original.count(old)
        if count != 1:
            raise ValueError(f"edit {idx}: anchor occurs {count} times in {path}; expected exactly once")
        start = original.find(old)
        grouped.setdefault(path, []).append((start, start + len(old), new))

    sections: list[str] = []
    for path in sorted(grouped):
        original = sources[path]
        replacements = sorted(grouped[path], key=lambda entry: entry[0])
        for previous, current in zip(replacements, replacements[1:]):
            if previous[1] > current[0]:
                raise ValueError(f"overlapping edits in {path}")
        updated = original
        for start, end, new in reversed(replacements):
            updated = updated[:start] + new + updated[end:]
        if not updated.endswith("\n"):
            raise ValueError(f"updated file must end with a newline: {path}")
        if updated == original:
            raise ValueError(f"all edits cancel out in {path}")
        section = "".join(difflib.unified_diff(
            original.splitlines(keepends=True),
            updated.splitlines(keepends=True),
            fromfile=f"a/{path}", tofile=f"b/{path}", n=3,
        ))
        sections.append(f"diff --git a/{path} b/{path}\n" + section)

    patch = "".join(sections)
    if len(patch.encode("utf-8")) > MAX_PATCH_BYTES:
        raise ValueError("proposed patch exceeds size limit")
    return patch
