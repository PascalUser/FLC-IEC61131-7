# Gemini quota guard for the FLC documentation HARNESS

OpenCode 2 automatically loads this local server plugin from `.opencode/plugins/`.
It applies only to provider ID `google`; it does not alter other providers.

- **Request pacing:** reserves a shared slot before every native Gemini HTTP
  request, including model continuations after `Read`, `Glob`, or `Grep`.
  Default: `16000` ms between reserved starts (under the observed five/minute
  free-tier limit). Multiple OpenCode processes in the same repository share
  `output/gemini_request_slots.json`. This is an admission interval, not a
  throughput or quota guarantee; calls made outside this plugin count too.
- **Retry:** respects `Please retry in Ns` on temporary rate-limit failures,
  with at most two attempts after the initial request (subject to OpenCode's
  own hard retry cap). Daily-limit and insufficient-credit failures are not
  retried. OpenCode session state is *not* automatically resumed after a
  terminal failure.
- **Output budget:** disabled by default to avoid truncating unified diffs.
  To impose an output token limit, set `FLC_LLM_MAX_OUTPUT_TOKENS=8192` (or a
  value between 1024 and 65536). This is separate from the input context.

Optional Docker environment via the existing `env_file: .env`:

```dotenv
FLC_LLM_INTERVAL_MS=16000
# FLC_LLM_MAX_OUTPUT_TOKENS=8192
```

After adding the plugin, restart the OpenCode process; the Docker service does
not need rebuilding for files mounted under `.opencode/`. The Python unit tests
exercise the quota policy and request reservations without contacting Google.
**A live OpenCode 2 session is still needed** to verify that the installed CLI
registers the `http.request` and `retry` hooks. Providers may impose additional
quotas (requests per day, input/output tokens per minute) which this plugin
cannot increase. WebSocket-only routes do not pass through `http.request`.
