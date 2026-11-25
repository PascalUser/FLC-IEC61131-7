"""One bounded follow-up to repair an invalid OpenCode agent result.

This is opt-in because it makes an additional paid/provider-limited request.
Only the new assistant turn is returned for independent contract verification.
"""
from __future__ import annotations

import re
import subprocess
from pathlib import Path
from typing import Optional, Sequence

from scripts.opencode_sessions import extract_json_event_errors, parse_json_events
from scripts.provider_errors import classify_provider_failure

ANSI = re.compile(r"\x1b\[[0-?]*[ -/]*[@-~]")


def repair_agent_turn(
    original_command: Sequence[str], step_id: str, root: Path,
    session_id: str, model: Optional[str], failure: str, *,
    logs_dir: Path, capture_logs: bool,
) -> str:
    """Request one corrected answer in the same session; never accept old output.

    The caller must validate the returned turn using validate_agent_output.
    We never modify tracked files, run git apply, or retry an API failure.
    """
    # Original argv always ends in the user's task prompt.
    if len(original_command) < 4 or original_command[:2] != ["opencode", "run"]:
        raise ValueError("repair requires a configured OpenCode command")
    hint = ANSI.sub("", failure).replace("\n", " ")[:360]
    prompt = (
        "Your immediately preceding HARNESS answer FAILED validation: " + hint + ". "
        "Reinspect the current source where necessary. Emit a single NEW "
        "HARNESS_RESULT_JSON line for this turn, containing reason, inspected_sources, "
        "evidence and metadata. For PATCH_PROPOSED prefer 'edits': a JSON array of "
        "objects with exactly path, old (literal unique current-file text), new. "
        "Escape embedded line breaks inside JSON strings. Do NOT hand-write a unified diff when "
        "using edits; do NOT repeat your prior diff or earlier result markers. "
        "Follow .opencode/agents/PATCH_POLICY.md; do not edit tracked files."
    )
    cmd = list(original_command[:-1])
    if model:
        cmd += ["--model", model]
    cmd += ["--session", session_id, "--format", "json", prompt]
    print(f"[REPAIR] {step_id}: one additional turn in session {session_id}")
    proc = subprocess.run(cmd, cwd=str(root), text=True, capture_output=True)
    seen_session_id, new_text = parse_json_events(proc.stdout or "")
    if seen_session_id is not None and seen_session_id != session_id:
        raise ValueError("repair returned a different OpenCode session")
    diagnostics = ANSI.sub("", proc.stderr or "")
    events_error = extract_json_event_errors(proc.stdout or "")
    if events_error:
        diagnostics = diagnostics.rstrip() + "\n" + events_error
    if capture_logs:
        logs_dir.mkdir(parents=True, exist_ok=True)
        with (logs_dir / f"{step_id}.log").open("a", encoding="utf-8") as log:
            log.write("\n--- ONE-TURN REPAIR ---\n" + ANSI.sub("", new_text))
            if diagnostics.strip():
                log.write("\n--- REPAIR STDERR ---\n" + diagnostics.strip() + "\n")
    if new_text.strip():
        print(ANSI.sub("", new_text))
    if diagnostics.strip():
        print("[REPAIR] " + diagnostics.strip()[:500])
    if proc.returncode:
        reason = classify_provider_failure(diagnostics) or f"exit-code-{proc.returncode}"
        raise ValueError("repair request failed: " + reason)
    return new_text
