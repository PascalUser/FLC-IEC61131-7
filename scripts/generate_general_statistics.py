#!/usr/bin/env python3
"""Create a consolidated overview from the current production source tree.

Counts are descriptive inventory metrics, not quality scores. JaCoCo coverage
is included only when its XML report is present. No estimated complexity,
hard-coded distributions, or synthetic one-category charts are generated.
"""
import argparse
import json
import xml.etree.ElementTree as ET
from pathlib import Path

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

ROOT = Path(__file__).resolve().parent.parent
PRODUCTION = ROOT / "src/main/java/compiler"
TESTS = ROOT / "src/test/java"
OUTPUT = ROOT / "doc/assets/general_statistics.png"
JSON_OUTPUT = ROOT / "doc/stats_general.json"
MODULES = ("lexer", "parser", "ast", "generator", "repository")


def java_sources(path):
    return [p for p in path.rglob("*.java") if p.name != "package-info.java"]


def count_source_lines(path):
    """Count non-blank, non-comment-only Java lines (a lightweight LOC proxy)."""
    count = 0
    in_block_comment = False
    for line in path.read_text(encoding="utf-8").splitlines():
        s = line.strip()
        if not s:
            continue
        if in_block_comment:
            if "*/" in s:
                in_block_comment = False
            continue
        if s.startswith("//"):
            continue
        if s.startswith("/*"):
            if "*/" not in s[2:]:
                in_block_comment = True
            continue
        if s.startswith("*") or s.startswith("*/"):
            continue
        count += 1
    return count


def module_inventory():
    inventory = {}
    for module in MODULES:
        files = java_sources(PRODUCTION / module)
        inventory[module] = {
            "java_files": len(files),
            "non_comment_lines": sum(count_source_lines(p) for p in files),
        }
    return inventory


def test_inventory():
    paths = [p for p in TESTS.rglob("*.java") if p.name != "package-info.java"]
    return {
        "java_files": len(paths),
        "unit_test_classes": sum(p.name.endswith("Test.java") for p in paths),
        "integration_test_classes": sum(p.name.endswith("IT.java") for p in paths),
    }


def jacoco_coverage():
    report = ROOT / "target/site/jacoco/jacoco.xml"
    if not report.exists():
        return None
    root = ET.parse(report).getroot()
    totals = {m: [0, 0] for m in MODULES}  # covered, missed
    for package in root.findall(".//package"):
        parts = package.get("name", "").split("/")
        if len(parts) < 2 or parts[0] != "compiler" or parts[1] not in totals:
            continue
        for counter in package.findall("counter"):
            if counter.get("type") == "LINE":
                totals[parts[1]][0] += int(counter.get("covered", 0))
                totals[parts[1]][1] += int(counter.get("missed", 0))
    covered = sum(v[0] for v in totals.values())
    missed = sum(v[1] for v in totals.values())
    by_module = {}
    for module, (hit, miss) in totals.items():
        if hit + miss:
            by_module[module] = round(100 * hit / (hit + miss), 1)
    return {
        "overall_percent": round(100 * covered / (covered + missed), 1)
        if covered + missed else None,
        "by_module_percent": by_module,
        "covered_lines": covered,
        "missed_lines": missed,
    }


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--json-only", action="store_true",
                        help="Write JSON without rendering the PNG.")
    args = parser.parse_args()

    data = {
        "production_root": "src/main/java/compiler",
        "modules": module_inventory(),
        "tests": test_inventory(),
        "jacoco_line_coverage": jacoco_coverage(),
        "notes": [
            "Lowering is included in compiler.generator.lowers, not a separate module.",
            "Simulation helpers under src/test are test support, not production code.",
            "LOC is a simple source-line inventory, not a standardized complexity metric.",
            "Coverage is omitted when target/site/jacoco/jacoco.xml is unavailable.",
        ],
    }
    JSON_OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    JSON_OUTPUT.write_text(json.dumps(data, indent=2) + "\n", encoding="utf-8")
    print(f"Wrote {JSON_OUTPUT.relative_to(ROOT)}")

    if args.json_only:
        return

    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    names = list(MODULES)
    file_counts = [data["modules"][m]["java_files"] for m in names]
    loc_counts = [data["modules"][m]["non_comment_lines"] for m in names]
    fig, axes = plt.subplots(1, 3, figsize=(15, 5))
    fig.suptitle("FLC compiler — general statistics", fontsize=15, fontweight="bold")

    axes[0].barh(names, file_counts)
    axes[0].invert_yaxis()
    axes[0].set_title("Production Java files")
    axes[0].set_xlabel("Files")
    for i, value in enumerate(file_counts):
        axes[0].text(value, i, f" {value}", va="center")

    axes[1].barh(names, loc_counts)
    axes[1].invert_yaxis()
    axes[1].set_title("Non-comment source lines")
    axes[1].set_xlabel("Lines")
    for i, value in enumerate(loc_counts):
        axes[1].text(value, i, f" {value:,}", va="center")

    coverage = data["jacoco_line_coverage"]
    if coverage and coverage["by_module_percent"]:
        labels = list(coverage["by_module_percent"])
        values = [coverage["by_module_percent"][m] for m in labels]
        axes[2].bar(labels, values)
        axes[2].set_ylim(0, 100)
        axes[2].set_ylabel("Line coverage (%)")
        overall = coverage["overall_percent"]
        if overall is not None:
            axes[2].axhline(overall, linestyle="--",
                            label=f"Overall: {overall:.1f}%")
            axes[2].legend()
        axes[2].set_title("JaCoCo line coverage")
        axes[2].tick_params(axis="x", rotation=20)
    else:
        axes[2].text(0.5, 0.5, "JaCoCo report unavailable\n(run mvn test first)",
                     ha="center", va="center", transform=axes[2].transAxes)
        axes[2].set_title("Test coverage")
        axes[2].set_axis_off()

    tests = data["tests"]
    footer = (f"{tests['java_files']} test-support/source files · "
              f"{tests['unit_test_classes']} unit test classes · "
              f"{tests['integration_test_classes']} integration test classes")
    fig.text(0.5, 0.02, footer, ha="center", fontsize=9)
    fig.tight_layout(rect=(0, 0.07, 1, 0.92))
    fig.savefig(OUTPUT, dpi=180, bbox_inches="tight")
    plt.close(fig)
    print(f"Wrote {OUTPUT.relative_to(ROOT)}")


if __name__ == "__main__":
    main()
