#!/usr/bin/env python3
"""
Generates project charts (doc/assets/*.png) from REAL source code counts,
not invented numbers.

Each chart_* function documents in a comment where each datum comes from,
so it can be audited against the corresponding .java file.

Usage:
    python3 scripts/generate_charts.py            # regenerates all charts
    python3 scripts/generate_charts.py --list      # only lists what would be generated

When source code changes (an enum is added, a Transformer, a Diagnostic, etc.),
update the corresponding count here and rerun the script. Do not edit PNGs by hand.
"""
import argparse
import os

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ASSETS_DIR = os.path.join(ROOT, "doc", "assets")


def _save(fig, name, source_note):
    os.makedirs(ASSETS_DIR, exist_ok=True)
    fig.text(0.01, 0.01, f"Source: {source_note}", fontsize=7, color="gray")
    path = os.path.join(ASSETS_DIR, f"{name}.png")
    fig.savefig(path, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"  -> {os.path.relpath(path, ROOT)}  ({source_note})")
    return path


def chart_enum_sizes():
    """
    Number of constants per enum in src/main/java/utils/enums/*.java.
    Counted manually over source code (Type.java, Subtype.java,
    Use.java, Source.java); update if values are added/removed.
    """
    data = {
        "Type": 6,       # UNKNOWN, SIMPLE, ENUMERATE, SUBRANGE, ARRAY, STRUCT
        "Subtype": 24,   # BOOL, BYTE, CUSTOM, DATE, DATE_AND_TIME, DINT, DWORD, INT, LINT, LREAL, LWORD, REAL, SINT, STRING, TIME, TIME_OF_DAY, UDINT, UINT, ULINT, UNKNOWN, USINT, WORD, NONE, WSTRING
        "Use": 9,        # FIELD, FUNCTION, LITERAL, OPTION, RULE, TYPE, UNKNOWN, VARIABLE, MACRO
        "Source": 7,     # DEFUZZIFY, FUZZIFY, IN, INTERNAL, NONE, OUT, UNKNOWN
    }
    fig, ax = plt.subplots(figsize=(6, 4))
    ax.bar(data.keys(), data.values(), color="#4C72B0")
    ax.set_title("Number of Values per Semantic Enum")
    ax.set_ylabel("Number of Constants")
    for i, (k, v) in enumerate(data.items()):
        ax.text(i, v + 0.3, str(v), ha="center")
    return _save(fig, "enum_sizes",
                 "src/main/java/utils/enums/{Type,Subtype,Use,Source}.java")


def chart_transformer_chain_lengths():
    """
    Length of the Transformer chain (Chain of Responsibility) per
    lexeme category, counted over
    src/main/java/lexer/internals/LexicalPreprocessors.java.
    """
    data = {
        "INTERVALS": 6,
        "REALS": 3,
        "NATURALS": 2,
        "INTEGERS": 2,
        "BINARY": 2,
        "OCTAL": 2,
        "HEXADECIMAL": 2,
        "STRINGS": 2,
        "WSTRINGS": 2,
        "IDENTIFIERS": 1,
        "DATES": 1,
        "DAYTIMES": 1,
        "DATE_AND_TIMES": 1,
    }
    items = sorted(data.items(), key=lambda kv: kv[1], reverse=True)
    labels = [k for k, _ in items]
    values = [v for _, v in items]

    fig, ax = plt.subplots(figsize=(7, 4.5))
    ax.barh(labels, values, color="#55A868")
    ax.invert_yaxis()
    ax.set_title("Transformer Chain Lengths per Lexeme Type")
    ax.set_xlabel("Number of Chained Transformers")
    for i, v in enumerate(values):
        ax.text(v + 0.05, i, str(v), va="center")
    return _save(fig, "transformer_chain_lengths",
                 "src/main/java/lexer/internals/LexicalPreprocessors.java")


def chart_diagnostics_error_vs_warning():
    """
    Error vs Warning classification of concrete classes in
    src/main/java/utils/diagnostics/*.java.
    """
    errors = [
        "DateOutOfRange", "IntervalConstructionError", "IntervalOutOfRange",
        "TimeOfDayOutOfRange", "DateAndTimeOutOfRange", "SyntaxError",
    ]
    warnings = [
        "StringLengthWarning", "HexadecimalOutOfRange", "RealOutOfRange",
        "NaturalOutOfRange", "BinaryOutOfRange", "OctalOutOfRange",
        "IntegerOutOfRange",
    ]
    fig, ax = plt.subplots(figsize=(5, 4))
    ax.bar(["Error (fatal)", "Warning (fallback)"],
           [len(errors), len(warnings)], color=["#C44E52", "#DD8452"])
    ax.set_title("Concrete Diagnostics by Severity")
    ax.set_ylabel("Number of Classes")
    for i, v in enumerate([len(errors), len(warnings)]):
        ax.text(i, v + 0.1, str(v), ha="center")
    return _save(fig, "diagnostics_error_vs_warning",
                 "src/main/java/utils/diagnostics/*.java (Error/Warning subclasses)")


def chart_parser_grammar_stats():
    """
    Bison grammar statistics in src/main/java/parser/Parser.y.
    Counted over the source file: declared tokens, production rules,
    and supported initialization types.
    """
    # Tokens declared in Parser.y (lines 31-54)
    token_categories = {
        "FCL Keywords": 22,       # TYPE..END_OPTION, FUZZIFY..RULEBLOCK, etc.
        "Annex B Keywords": 25,   # VAR_INPUT..ARRAY
        "Elementary Types": 20,   # SINT..LWORD, TIME..DATE_AND_TIME
        "Literals": 4,            # NUMERIC_LITERAL..BOOLEAN_LITERAL
        "Operators/Symbols": 8,   # ASSIGN_OP, RANGE_OP, ; ( ) , : # [ ]
    }

    # Production rules (non-terminals) - counted in SymbolKind enum (Parser.java lines 174-309)
    nonterminals = 116  # S_program .. S_structure_field_spec_init

    # Supported initialization types (parser/initializations/*.java)
    init_types = {
        "BooleanInitialization": 1,
        "RealInitialization": 1,
        "SubrangeInitialization": 1,
        "EnumeratedInitialization": 1,
        "MacroInitialization": 1,
        "VariableInitialization": 1,
        "StructInitialization": 1,
        "RepeatedInitialization": 1,
    }

    fig, axes = plt.subplots(1, 3, figsize=(14, 4))

    # Tokens
    ax = axes[0]
    cats = list(token_categories.keys())
    vals = list(token_categories.values())
    ax.bar(cats, vals, color="#4C72B0")
    ax.set_title("Tokens Declared in Parser.y by Category")
    ax.set_ylabel("Count")
    ax.tick_params(axis='x', rotation=15)
    for i, v in enumerate(vals):
        ax.text(i, v + 0.3, str(v), ha="center")

    # Non-terminals
    ax = axes[1]
    ax.bar(["Non-terminals (productions)"], [nonterminals], color="#55A868")
    ax.set_title("Non-terminal Symbols in Grammar")
    ax.set_ylabel("Count")
    ax.text(0, nonterminals + 2, str(nonterminals), ha="center")

    # Initialization types
    ax = axes[2]
    init_labels = list(init_types.keys())
    init_vals = list(init_types.values())
    ax.bar(init_labels, init_vals, color="#C44E52")
    ax.set_title("Supported IEC 61131-7 Initialization Types")
    ax.set_ylabel("Count")
    ax.tick_params(axis='x', rotation=45)

    fig.tight_layout()
    return _save(fig, "parser_grammar_stats",
                 "src/main/java/parser/Parser.y + src/main/java/parser/Parser.java (SymbolKind) + parser/initializations/*.java")


CHARTS = {
    "enum_sizes": chart_enum_sizes,
    "transformer_chain_lengths": chart_transformer_chain_lengths,
    "diagnostics_error_vs_warning": chart_diagnostics_error_vs_warning,
    "parser_grammar_stats": chart_parser_grammar_stats,
}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("names", nargs="*", help="Specific names to regenerate (default: all)")
    parser.add_argument("--list", action="store_true", help="Only list available charts")
    args = parser.parse_args()

    if args.list:
        for name in CHARTS:
            print(name)
        return

    targets = args.names or list(CHARTS.keys())
    print("Generating charts in doc/assets/ ...")
    for name in targets:
        if name not in CHARTS:
            print(f"  ! unknown chart: {name}")
            continue
        CHARTS[name]()


if __name__ == "__main__":
    main()