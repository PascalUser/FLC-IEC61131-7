/** Provider quota policy. No OpenCode dependency, so behavior is testable offline. */
import { mkdir, readFile, rename, rm, stat, writeFile } from "node:fs/promises";
import { dirname } from "node:path";
import { createHash } from "node:crypto";

export const DEFAULT_INTERVAL_MS = 16000; // 3.75 requests/minute vs observed 5/minute cap

export function positiveMilliseconds(value, fallback = DEFAULT_INTERVAL_MS) {
  if (value === undefined || value === "") return fallback;
  const parsed = Number(value);
  if (!Number.isSafeInteger(parsed) || parsed < 1000 || parsed > 300000) {
    throw new Error("FLC_LLM_INTERVAL_MS must be an integer between 1000 and 300000");
  }
  return parsed;
}

/** Optional exact model-ID to interval mapping. No guessed provider limits. */
export function modelIntervals(value) {
  if (value === undefined || value.trim() === "") return {};
  const input = JSON.parse(value);
  if (!input || typeof input !== "object" || Array.isArray(input)) {
    throw new Error("FLC_LLM_MODEL_INTERVALS_JSON must be a JSON object");
  }
  const result = {};
  for (const [modelID, milliseconds] of Object.entries(input)) {
    if (!modelID || modelID.length > 180 || modelID.includes("/")) {
      throw new Error(`Invalid Gemini model ID: ${modelID}`);
    }
    result[modelID] = positiveMilliseconds(milliseconds);
  }
  return result;
}

/** One throttling state per model; hashes prevent filesystem traversal. */
export function modelStateFile(sharedBase, modelID) {
  if (typeof modelID !== "string" || !modelID) throw new Error("Missing Gemini model ID");
  const id = createHash("sha256").update(modelID).digest("hex").slice(0, 24);
  return `${sharedBase}.${id}.json`;
}

export function outputBudget(value) {
  if (value === undefined || value === "") return 0; // no forced cap by default
  const parsed = Number(value);
  if (!Number.isSafeInteger(parsed) || parsed < 1024 || parsed > 65536) {
    throw new Error("FLC_LLM_MAX_OUTPUT_TOKENS must be an integer from 1024 to 65536");
  }
  return parsed;
}

export function retryPolicy(error, attempt) {
  const message = String(error?.message ?? "").toLowerCase();
  if (/free-models-per-day|free usage limit reached|daily quota|per.day|insufficient.credits|more credits|billing/.test(message)) {
    return { retry: false };
  }
  if (error?.status !== 429 && !/free_tier_requests|requests per minute|rate limit exceeded|quota exceeded/.test(message)) {
    return null; // leave OpenCode's built-in decision unchanged
  }
  if (attempt >= 3) return { retry: false }; // bounded retry budget
  const match = /(?:please\s+)?retry\s+(?:in|after)\s+(\d+(?:\.\d+)?)\s*s(?:econds?)?\b/i.exec(message);
  const suggested = match ? Math.ceil(Number(match[1]) * 1000) + 1000 : 36000;
  return { retry: true, delay: Math.max(16000, Math.min(120000, suggested)) };
}

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

/**
 * Reserve one timestamp under an inter-process lock before awaiting it.
 * State lives in output/, not tracked files. A fresh process continues spacing
 * requests from previous OpenCode invocations; the lock protects concurrency.
 */
export async function reserveSharedSlot(stateFile, intervalMs, options = {}) {
  const now = options.now ?? Date.now;
  const wait = options.sleep ?? sleep;
  const lock = `${stateFile}.lock`;
  await mkdir(dirname(stateFile), { recursive: true });
  const lockDeadline = now() + 30000;
  while (true) {
    try {
      await mkdir(lock);
      break;
    } catch (error) {
      if (error?.code !== "EEXIST") throw error;
      // Only reap abandoned locks; a live lock should last milliseconds.
      try {
        const info = await stat(lock);
        if (now() - info.mtimeMs > 60000) {
          await rm(lock, { recursive: true, force: true });
          continue;
        }
      } catch (e) {
        if (e?.code !== "ENOENT") throw e;
      }
      if (now() >= lockDeadline) throw new Error("FLC quota reservation lock timed out");
      await wait(100);
    }
  }
  let reserved;
  try {
    let nextAllowed = 0;
    try {
      const saved = JSON.parse(await readFile(stateFile, "utf8"));
      if (Number.isFinite(saved.nextAllowedAt)) nextAllowed = saved.nextAllowedAt;
    } catch (error) {
      if (error?.code !== "ENOENT") throw error;
    }
    reserved = Math.max(now(), nextAllowed);
    const temporary = `${stateFile}.${process.pid}.${Math.random().toString(36).slice(2)}.tmp`;
    try {
      await writeFile(temporary, `${JSON.stringify({ schemaVersion: 1, nextAllowedAt: reserved + intervalMs })}\n`, { flag: "wx" });
      await rename(temporary, stateFile);
    } finally {
      await rm(temporary, { force: true });
    }
  } finally {
    await rm(lock, { recursive: true, force: true });
  }
  await wait(Math.max(0, reserved - now()));
  return reserved;
}
