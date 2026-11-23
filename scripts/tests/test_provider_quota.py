"""Free-tier safety contract for OpenCode's per-request Gemini plugin."""
from __future__ import annotations

import contextlib
import io
import json
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT))
sys.path.insert(0, str(ROOT / 'scripts'))
from provider_errors import classify_provider_failure  # noqa: E402


class ProviderQuotaTests(unittest.TestCase):
    def test_classifies_google_and_openrouter_failures(self):
        self.assertEqual(classify_provider_failure(
            'Quota exceeded for metric: generate_content_free_tier_requests; limit: 5'),
            'provider-rate-limit:requests-per-minute')
        self.assertEqual(classify_provider_failure('Rate limit exceeded: free-models-per-day'),
                         'provider-rate-limit:daily-quota')
        self.assertEqual(classify_provider_failure('Free usage limit reached.'),
                         'provider-rate-limit:daily-quota')
        self.assertEqual(classify_provider_failure('requires more credits; can only afford 3400'),
                         'provider-insufficient-credits')
        self.assertIsNone(classify_provider_failure('Parser failed with a missing token'))

    def test_orchestrator_reason_uses_provider_category(self):
        from run_all_agents import StepRecord, StepSpec, StepState, execute_step
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            spec = StepSpec('docs_update_parser', 'agent', ['opencode', 'run'], 'opencode')
            rec = StepRecord(spec.step_id, spec.description, spec.kind, True, spec.command)
            proc = subprocess.CompletedProcess(spec.command, 1, stdout='',
                stderr='Quota exceeded: generate_content_free_tier_requests. Please retry in 33s')
            with patch('run_all_agents.ROOT', root), patch(
                'run_all_agents.subprocess.run', return_value=proc
            ), contextlib.redirect_stdout(io.StringIO()), contextlib.redirect_stderr(io.StringIO()):
                execute_step(spec, rec, logs_dir=root / 'output/logs',
                             patches_dir=root / 'output/patches', capture_logs=True)
            self.assertEqual(rec.state, StepState.FAILED)
            self.assertEqual(rec.reason, 'provider-rate-limit:requests-per-minute')

    @unittest.skipUnless(shutil.which('node'), 'Node.js unavailable: run JS tests with node manually')
    def test_node_policy_and_shared_reservations(self):
        with tempfile.TemporaryDirectory() as tmp:
            state = Path(tmp) / 'slots.json'
            module = (ROOT / '.opencode/plugins/flc-quota-guard/quota.mjs').as_uri()
            js = '''
import { positiveMilliseconds, outputBudget, retryPolicy, reserveSharedSlot } from URL;
import assert from 'node:assert/strict';
assert.equal(positiveMilliseconds(undefined), 16000);
assert.equal(outputBudget(undefined), 0);
assert.throws(() => positiveMilliseconds('0'));
assert.throws(() => outputBudget('999999'));
assert.deepEqual(retryPolicy({status:429,message:'Please retry in 33.539s'},1),{retry:true,delay:34539});
assert.deepEqual(retryPolicy({status:429,message:'Free usage limit reached'},1),{retry:false});
assert.deepEqual(retryPolicy({status:429,message:'requests per minute'},3),{retry:false});
assert.equal(retryPolicy({status:400,message:'bad request'},1),null);
let clock=100000;
const slept=[];
const fake={now:()=>clock,sleep:async(n)=>{slept.push(n)}};
const first=await reserveSharedSlot(STATE,16000,fake);
const second=await reserveSharedSlot(STATE,16000,fake);
assert.equal(first,100000);
assert.equal(second,116000);
assert.ok(slept.includes(16000));
console.log('OK');
'''.replace('URL', json.dumps(module)).replace('STATE', json.dumps(str(state)))
            proc = subprocess.run(['node', '--input-type=module', '-e', js],
                                  capture_output=True, text=True)
            self.assertEqual(proc.returncode, 0, proc.stderr)
            self.assertIn('OK', proc.stdout)

    def test_plugin_hooks_are_scoped_to_google(self):
        plugin = (ROOT / '.opencode/plugins/flc-quota-guard/index.ts').read_text()
        self.assertIn('"http.request"', plugin)
        self.assertIn('"retry"', plugin)
        self.assertGreaterEqual(plugin.count('providerID: "google"'), 2)


if __name__ == '__main__':
    unittest.main()
