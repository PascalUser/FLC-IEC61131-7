"""Opt-in, source-bound OpenCode v2 session handoff for HARNESS agents.

Only a session ID is persisted. No claim is made about completed subtasks until
validate_agent_output verifies the current turn's HARNESS_RESULT_JSON and diff.
"""
from __future__ import annotations

import hashlib
import json
import re
import subprocess
from datetime import datetime, timezone
from pathlib import Path
from typing import Dict, Optional, Sequence, Tuple

SESSION_ID = re.compile(r"^ses_[A-Za-z0-9_-]{6,128}$")
STEP_ID = re.compile(r"^[a-z][a-z0-9_]{0,100}$")
CONTINUE_PROMPT = (
    "Continue the unfinished HARNESS task in this session. Inspect the previous "
    "messages and tool results; do not assume that inspected files are already "
    "documented. Verify evidence against the current repository. Follow "
    ".opencode/agents/PATCH_POLICY.md. Emit exactly one HARNESS_RESULT_JSON "
    "line for THIS turn. For PATCH_PROPOSED, prefer exact JSON edits "
    "(path, old, new) and no raw diff. Do not edit tracked files."
)


def parse_json_events(raw: str) -> Tuple[Optional[str], str]:
    """Extract this run's assistant text and primary session ID from OpenCode JSONL.

    Tool results are never treated as agent conclusions or accepted patch text.
    Unknown/invalid events are ignored; missing text still fails the contract.
    """
    ids = set()
    texts = []
    for line in raw.splitlines():
        try:
            event = json.loads(line)
        except json.JSONDecodeError:
            continue
        if not isinstance(event, dict):
            continue
        sid = event.get("sessionID")
        if isinstance(sid, str) and SESSION_ID.fullmatch(sid):
            ids.add(sid)
        part = event.get("part")
        if (event.get("type") == "text" and isinstance(part, dict)
                and isinstance(part.get("text"), str)):
            texts.append(part["text"])
    # Multiple session IDs could be child-agent events; never pick one arbitrarily.
    session_id = next(iter(ids)) if len(ids) == 1 else None
    return session_id, "\n".join(texts)


def extract_json_event_errors(raw: str) -> str:
    """Extract diagnostic messages from OpenCode JSONL error events only.

    Never treat tool outputs as diagnostics or agent conclusions. Restrict the
    amount of displayed text; provider errors can be verbose.
    """
    messages = []
    for line in raw.splitlines():
        try:
            event = json.loads(line)
        except json.JSONDecodeError:
            continue
        if not isinstance(event, dict) or event.get("type") != "error":
            continue
        error = event.get("error")
        message = None
        if isinstance(error, str):
            message = error
        elif isinstance(error, dict):
            data = error.get("data")
            if isinstance(data, dict):
                nested = data.get("error")
                for candidate in (data.get("message"),
                                  nested.get("message") if isinstance(nested, dict) else None,
                                  data.get("detail")):
                    if isinstance(candidate, str) and candidate.strip():
                        message = candidate
                        break
            if not message:
                for candidate in (error.get("message"), error.get("name")):
                    if isinstance(candidate, str) and candidate.strip():
                        message = candidate
                        break
        messages.append((message or "OpenCode emitted an error event without details")[:4000])
        if len(messages) >= 5:
            break
    return "\n".join(messages)


def task_digest(command: Sequence[str]) -> str:
    """Stable task identity independent of the selected model and session ID."""
    return hashlib.sha256(json.dumps(list(command), ensure_ascii=False).encode()).hexdigest()


def repository_digest(root: Path) -> Optional[str]:
    """Invalidate handoff after a tracked source change, including dirty files.

    Git HEAD alone is insufficient, because an uncommitted edit may invalidate
    previously inspected evidence. Untracked inputs are not captured; the agent
    must still verify relevant sources before claiming a completed patch.
    """
    head = subprocess.run(["git", "rev-parse", "HEAD"], cwd=root,
                          capture_output=True, text=True, check=False)
    if head.returncode or not re.fullmatch(r"[0-9a-f]{40,64}", head.stdout.strip()):
        return None
    diff = subprocess.run(["git", "diff", "--binary", "HEAD", "--"], cwd=root,
                          capture_output=True, check=False)
    if diff.returncode:
        return None
    return hashlib.sha256(head.stdout.strip().encode() + b"\n" + diff.stdout).hexdigest()


class SessionStore:
    """One checkpoint per step, stored under an untracked output directory."""

    def __init__(self, directory: Path):
        self.directory = Path(directory)

    def _path(self, step_id: str) -> Path:
        if not STEP_ID.fullmatch(step_id):
            raise ValueError(f"Unsafe step ID: {step_id!r}")
        return self.directory / f"{step_id}.json"

    def load(self, step_id: str, command: Sequence[str], repo_digest: str) -> Optional[Dict]:
        try:
            payload = json.loads(self._path(step_id).read_text(encoding="utf-8"))
        except (OSError, ValueError):
            return None
        if not isinstance(payload, dict):
            return None
        sid = payload.get("session_id")
        if (payload.get("state") == "PAUSED" and isinstance(sid, str)
                and SESSION_ID.fullmatch(sid)
                and payload.get("command_sha256") == task_digest(command)
                and payload.get("repository_sha256") == repo_digest):
            return payload
        return None

    def save(self, step_id: str, command: Sequence[str], repo_digest: str,
             session_id: str, state: str, model: Optional[str]) -> None:
        if not SESSION_ID.fullmatch(session_id) or state not in {"PAUSED", "COMPLETED"}:
            raise ValueError("Invalid OpenCode checkpoint")
        path = self._path(step_id)
        path.parent.mkdir(parents=True, exist_ok=True)
        payload = {
            "schema_version": 1,
            "step_id": step_id,
            "state": state,
            "session_id": session_id,
            "model": model,
            "command_sha256": task_digest(command),
            "repository_sha256": repo_digest,
            "updated_at": datetime.now(timezone.utc).isoformat(),
        }
        temp = path.with_suffix(".json.tmp")
        temp.write_text(json.dumps(payload, indent=2) + "\n", encoding="utf-8")
        temp.replace(path)
