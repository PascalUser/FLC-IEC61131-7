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

## Machine-readable result contract (required for every OpenCode invocation)

Print exactly one single-line `HARNESS_RESULT_JSON: {...}` marker on stdout.
The JSON MUST include:

- `outcome`: `NO_CHANGES`, `PATCH_PROPOSED`, or `BLOCKED`.
- `reason`: a nonempty explanation of the decision.
- For `NO_CHANGES` or `PATCH_PROPOSED`, `inspected_sources`: nonempty list of
  **existing repository-relative file paths** that were actually inspected.
- `evidence`: nonempty list of objects `{"path": "one/inspected/file", "detail": "what was checked"}`.
  Each path must occur in `inspected_sources`.
- For `PATCH_PROPOSED`, `metadata`: a JSON object with nonempty string fields
  `id`, `objective`, `motivation`, `scope`, `changes`, `risks`, `validation`,
  and `dependencies` (use `"None"` where applicable).

A `NO_CHANGES` result must not include a diff. A `BLOCKED` result must have a
reason and must not include a diff. A `PATCH_PROPOSED` result must be followed
by the **raw** unified diff (`diff --git a/... b/...`) as the final content of
stdout, without Markdown fences or trailing prose. Keep the JSON marker and
metadata before the diff, never inside it. The orchestrator checks patch
applicability read-only with `git apply --check --whitespace=error` and rejects
missing, empty or malformed results. Agent success does not authorize applying
or committing any patch.

Example of a no-change result (replace the source/evidence with actual checks):

```text
HARNESS_RESULT_JSON: {"outcome":"NO_CHANGES","reason":"Verified current output","inspected_sources":["pom.xml"],"evidence":[{"path":"pom.xml","detail":"Checked compiler configuration"}]}
```
