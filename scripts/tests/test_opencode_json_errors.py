"""OpenCode JSONL error diagnostics survive capture without trusting tool output."""
import json
import sys
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
from scripts.opencode_sessions import extract_json_event_errors, parse_json_events
from scripts.provider_errors import classify_provider_failure


class OpenCodeJsonErrorTests(unittest.TestCase):
    def test_google_daily_quota_event_is_visible_and_classified(self):
        raw = json.dumps({
            "type": "error", "sessionID": "ses_abc1234567",
            "error": {"name": "APIError", "data": {
                "message": "Quota exceeded for metric: generate_content_free_tier_requests, "
                           "requests per day limit exceeded on Free Tier"}},
        })
        error = extract_json_event_errors(raw)
        self.assertIn("per day", error)
        self.assertEqual(classify_provider_failure(error), "provider-rate-limit:daily-quota")
        self.assertEqual(parse_json_events(raw)[1], "")

    def test_nested_error_and_bounded_message(self):
        raw = json.dumps({"type": "error", "error": {"data": {
            "error": {"message": "Rate limit exceeded: 429" + "z" * 5000}}}})
        errors = extract_json_event_errors(raw)
        self.assertIn("Rate limit exceeded", errors)
        self.assertLessEqual(len(errors), 4000)

    def test_tool_output_and_assistant_text_not_treated_as_error(self):
        raw = "\n".join(json.dumps(item) for item in [
            {"type": "tool_use", "part": {"state": {"output": "fake quota exceeded"}}},
            {"type": "text", "part": {"text": "quota exceeded"}},
            {"type": "step_finish", "part": {"reason": "stop"}},
        ])
        self.assertEqual(extract_json_event_errors(raw), "")

    def test_plain_text_and_error_without_detail(self):
        self.assertEqual(extract_json_event_errors("not json"), "")
        self.assertIn("without details", extract_json_event_errors('{"type":"error"}'))


if __name__ == "__main__":
    unittest.main()
