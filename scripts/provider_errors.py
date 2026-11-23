"""Classify provider failures without confusing them with agent-contract errors."""
from __future__ import annotations

import re


def classify_provider_failure(stderr: str) -> str | None:
    """Return a bounded manifest reason; never store provider text or API keys."""
    msg = (stderr or "").lower()
    if re.search(r"free-models-per-day|free usage limit reached|daily quota|per.day", msg):
        return "provider-rate-limit:daily-quota"
    if re.search(r"generate_content_free_tier_requests|requests per minute|free_tier_requests", msg):
        return "provider-rate-limit:requests-per-minute"
    if re.search(r"insufficient.credits|requires more credits|can only afford", msg):
        return "provider-insufficient-credits"
    if re.search(r"quota exceeded|rate limit exceeded|\b429\b", msg):
        return "provider-rate-limit:unknown-window"
    return None
