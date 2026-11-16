# Shared Agent Patch-First Policy

All subagents in `.opencode/agents/` operate under a strict read-and-propose contract:

1. **No direct repository mutations**: Never modify, create, or delete tracked files directly (`write` and `edit` are disabled in `opencode.json`). Do not use shell commands (`sed -i`, redirection `>`, `tee`, `python` file writes, `git apply`, `git commit`, `git push`) to alter the working tree or Git history.
2. **Patch-only change proposals**: Whenever your task requires adding, updating, or deleting repository files, output a self-contained unified diff patch (`diff --git a/... b/...`) that passes `git apply --check --whitespace=error`.
3. **Mandatory patch metadata**: Precede every proposed patch with:
   - **ID**: `NNNN-short-description.patch`
   - **Objective**: What problem the patch solves
   - **Motivation**: Why the change is needed
   - **Scope**: Exact files affected
   - **Changes**: Concise technical summary
   - **Risks**: Potential side effects or ordering constraints
   - **Validation**: Commands to verify the patch
   - **Dependencies**: Prior patch IDs required, or `None`
4. **Source-of-truth grounding**: Inspect the actual `compiler.*` source tree under `src/main/java/compiler/` and tests under `src/test/java/` before making claims. Never invent metrics, classes, packages, or behavior.
5. **Handoff & logging**: Do NOT write to `/.opencode/log.txt` (deprecated). Include any necessary update to `handoff/HANDOFF.txt` inside the proposed patch or report the status summary for the user to record upon approval.
