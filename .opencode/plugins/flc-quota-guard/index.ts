/** OpenCode 2 server plugin: rate limit the actual Gemini API call loop. */
import { Plugin } from "@opencode/plugin";
import { join } from "node:path";
import {
  modelIntervals, modelStateFile, outputBudget, positiveMilliseconds, reserveSharedSlot, retryPolicy,
} from "./quota.mjs";

export default Plugin.define({
  id: "flc.gemini-quota-guard",
  async setup(ctx) {
    const interval = positiveMilliseconds(process.env.FLC_LLM_INTERVAL_MS);
    const maxOutput = outputBudget(process.env.FLC_LLM_MAX_OUTPUT_TOKENS);
    const intervals = modelIntervals(process.env.FLC_LLM_MODEL_INTERVALS_JSON);
    const projectRoot = ctx.location.project?.canonical ?? ctx.location.directory;
    const shared = join(projectRoot, "output", "gemini_request_slots.json");

    // This fires for EVERY native HTTP call, including tool continuations,
    // compaction and retries. A Python sleep between agents cannot do this.
    await ctx.session.hook("http.request", async (event) => {
      const modelID = event.model.id;
      const milliseconds = intervals[modelID] ?? interval;
      await reserveSharedSlot(modelStateFile(shared, modelID), milliseconds);
    }, { providerID: "google" });

    await ctx.session.hook("retry", (event) => {
      const decision = retryPolicy(event.error, event.attempt);
      if (decision) event.decision = decision;
    }, { providerID: "google" });

    // Optional. A forced small default would truncate longer unified diffs.
    if (maxOutput) {
      for (const kind of ["context", "compaction", "generate", "title"] as const) {
        await ctx.session.hook(kind, (event) => {
          event.options.maxTokens = maxOutput;
        }, { providerID: "google" });
      }
    }
  },
});
