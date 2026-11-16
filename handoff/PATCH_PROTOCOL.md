# Patch Protocol

All documentation, harness, script, and Javadoc changes in `FLC-IEC61131-7` follow a strict patch-review workflow so the repository owner retains full control over every modification and commit.

## 1. Mandatory Patch Contract

Every proposed patch MUST be accompanied by the following metadata block:

| Field | Requirement |
| :--- | :--- |
| **ID** | Sequential filename (`NNNN-short-kebab-description.patch`) |
| **Objective** | Concrete problem solved by the patch |
| **Motivation** | Why the change is necessary now |
| **Scope** | Exact list of added, modified, or deleted paths |
| **Changes** | Technical summary of modifications per file |
| **Risks** | Potential regressions, ordering constraints, or side effects |
| **Validation** | Exact commands used to verify patch application and behavior |
| **Dependencies** | Prior patch IDs required before applying, or `None` |

## 2. Granularity and Independence Rules

1. **Single responsibility**: Each patch addresses one coherent concern (for example, harness policy, orchestrator flow, module docs, or package Javadocs).
2. **Independence**: Documentation and Javadoc patches must not depend on compiler behavior changes unless explicitly justified.
3. **Clean formatting**: Patches must pass `git apply --check --whitespace=error` with zero trailing whitespace and standard `a/` and `b/` path prefixes.
4. **Evidence-backed content**: No invented metrics, package names, or behavioral claims. Every architectural statement must map to existing files under `src/`.

## 3. Review and Application Lifecycle

```text
Inspect baseline -> Design change -> Generate .patch -> User review
  -> [Rejected] Revise or discard
  -> [Approved] git apply --check --whitespace=error <patch>
               -> git apply <patch>
               -> Run validation commands
               -> User decides independently whether to git commit
               -> Update handoff/HANDOFF.txt
```

## 4. Patch Status Taxonomy

- `proposed`: Generated and awaiting user review.
- `candidate`: Legacy patch retained for selective review before application.
- `applied`: Applied cleanly to the working tree and validated.
- `committed`: Recorded in Git history by the user.
- `redundant`: Already superseded by the current branch state; do not apply.
- `requires-reconciliation`: Conflicts with current `compiler.*` structure; extract useful parts into a new patch instead of applying directly.
- `discarded`: Rejected during review.

## 5. Agent Execution Constraints

1. OpenCode agents run with `write: false` and `edit: false` in `opencode.json`.
2. Agents must never mutate tracked files via shell redirection, `sed -i`, `python`, or `git apply`.
3. Agents must never execute `git commit`, `git push`, `git checkout`, or `git reset`.
4. When asked to modify files, an agent outputs a valid unified diff patch conforming to this protocol.
