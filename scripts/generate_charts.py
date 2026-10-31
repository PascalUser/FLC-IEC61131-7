#!/usr/bin/env python3
"""Thin wrapper: delegates to scripts/extract_stats.py (single source of truth)."""
import runpy
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
sys.argv = ["extract_stats.py"] + sys.argv[1:]
runpy.run_path(str(ROOT / "scripts" / "extract_stats.py"), run_name="__main__")
