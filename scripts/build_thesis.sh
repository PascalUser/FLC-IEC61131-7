#!/usr/bin/env bash
# Compiles doc/thesis/*.md (in alphabetical file order) to a single
# Tesis.docx using pandoc. Does not write content: only concatenates
# and converts what is already in Markdown + doc/assets/*.png.
#
# Usage:
#   bash scripts/build_thesis.sh                 -> generates Tesis.docx in root
#   bash scripts/build_thesis.sh --out other.docx  -> chooses output name
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
THESIS_DIR="$ROOT_DIR/doc/thesis"
OUT="$ROOT_DIR/Tesis.docx"

if [[ "${1:-}" == "--out" ]]; then
  OUT="$2"
fi

if ! command -v pandoc >/dev/null 2>&1; then
  echo "ERROR: pandoc not installed. Install it (e.g., apt-get install pandoc) and retry." >&2
  exit 1
fi

if [[ ! -d "$THESIS_DIR" ]]; then
  echo "ERROR: $THESIS_DIR does not exist" >&2
  exit 1
fi

CHAPTERS=("$THESIS_DIR"/*.md)
if [[ ${#CHAPTERS[@]} -eq 0 ]]; then
  echo "ERROR: no .md chapters in $THESIS_DIR" >&2
  exit 1
fi

echo "Chapters to compile (in this order):"
printf '  - %s\n' "${CHAPTERS[@]##*/}"

# Verify that every image referenced in chapters exists, to avoid
# generating a docx with silently broken figures.
missing=0
for chapter in "${CHAPTERS[@]}"; do
  while IFS= read -r img; do
    resolved="$THESIS_DIR/$img"
    if [[ ! -f "$resolved" ]]; then
      echo "MISSING IMAGE: '$img' referenced in $(basename "$chapter")" >&2
      missing=1
    fi
  done < <(grep -oE '!\[[^]]*\]\(([^)]+)\)' "$chapter" | sed -E 's/.*\(([^)]+)\).*/\1/')
done
if [[ "$missing" -eq 1 ]]; then
  echo "Fix image references (or run scripts/generate_charts.py) before compiling." >&2
  exit 1
fi

REF_DOC_ARG=()
if [[ -f "$THESIS_DIR/reference.docx" ]]; then
  echo "Using style template: doc/thesis/reference.docx"
  REF_DOC_ARG=(--reference-doc="$THESIS_DIR/reference.docx")
fi

pandoc "${CHAPTERS[@]}" \
  --from=markdown \
  --to=docx \
  --resource-path="$THESIS_DIR" \
  --toc --toc-depth=2 \
  --number-sections \
  "${REF_DOC_ARG[@]}" \
  -o "$OUT"

echo "OK -> $(realpath --relative-to="$ROOT_DIR" "$OUT" 2>/dev/null || echo "$OUT")"