#!/usr/bin/env bash
# Compiles doc/thesis/*.md (in alphabetical file order) to a single
# Tesis.docx using pandoc. First renders Mermaid diagrams to PNG via mmdc.
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

if ! command -v npx >/dev/null 2>&1; then
  echo "ERROR: npx not installed. Install Node.js/npm and retry." >&2
  exit 1
fi

if [[ ! -d "$THESIS_DIR" ]]; then
  echo "ERROR: $THESIS_DIR does not exist" >&2
  exit 1
fi

echo "Rendering Mermaid diagrams to PNG..."
python3 "$ROOT_DIR/scripts/render_mermaid.py"

CHAPTERS=("$THESIS_DIR"/*.md)
if [[ ${#CHAPTERS[@]} -eq 0 ]]; then
  echo "ERROR: no .md chapters in $THESIS_DIR" >&2
  exit 1
fi

echo "Chapters to compile (in this order):"
printf '  - %s\n' "${CHAPTERS[@]##*/}"

REF_DOC_ARG=()
if [[ -f "$THESIS_DIR/reference.docx" ]]; then
  echo "Using style template: doc/thesis/reference.docx"
  REF_DOC_ARG=(--reference-doc="$THESIS_DIR/reference.docx")
fi

echo "Compiling thesis with pandoc..."
pandoc "${CHAPTERS[@]}" \
  --from=markdown-raw_tex \
  --to=docx \
  --resource-path="$ROOT_DIR:$ROOT_DIR/doc/assets/rendered_diagrams" \
  --toc --toc-depth=2 \
  --number-sections \
  "${REF_DOC_ARG[@]}" \
  -o "$OUT"

echo "OK -> $(realpath --relative-to="$ROOT_DIR" "$OUT" 2>/dev/null || echo "$OUT")"