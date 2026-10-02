#!/usr/bin/env python3
import re
import subprocess
import hashlib
import json
import tempfile
import os
from pathlib import Path

ROOT = Path(__file__).parent.parent
THESIS_DIR = Path("/workspace/doc/thesis")
RENDERED_DIR = Path("/workspace/doc/assets/rendered_diagrams")

MERMAID_PATTERN = re.compile(r"", re.DOTALL)

PUPPETEER_CONFIG = {
    "args": ["--no-sandbox", "--disable-setuid-sandbox"]
}

PUPPETEER_CONFIG_FILE = Path("/opt/puppeteer/puppeteer-config.json")

def extract_caption(content, match_start):
    before = content[:match_start]
    headings = re.findall(r"^(#+)\s+(.+)$", before, re.MULTILINE)
    return headings[-1][1] if headings else "Diagram"

def ensure_puppeteer_config():
    PUPPETEER_CONFIG_FILE.parent.mkdir(parents=True, exist_ok=True)
    if not PUPPETEER_CONFIG_FILE.exists():
        PUPPETEER_CONFIG_FILE.write_text(json.dumps(PUPPETEER_CONFIG, indent=2))

def find_chrome_executable():
    cache_dir = Path("/opt/puppeteer/cache")
    if not cache_dir.exists():
        return None
    chrome_binaries = list(Path("/opt/puppeteer/cache").rglob("chrome-headless-shell"))
    if not chrome_binaries:
        return None
    for binary in chrome_binaries:
        if binary.is_file() and os.access(binary, os.X_OK):
            return str(binary)
    if chrome_binaries:
        binary = chrome_binaries[0]
        binary.chmod(0o755)
        return str(binary)
    return None

def render_mermaid(mermaid_code, output_path):
    ensure_puppeteer_config()
    chrome_path = find_chrome_executable()
    if not chrome_path:
        print("  Error: Could not find chrome-headless-shell executable")
        return False
    env = os.environ.copy()
    env.update({
        "PUPPETEER_CACHE_DIR": "/opt/puppeteer/cache",
        "PUPPETEER_EXECUTABLE_PATH": chrome_path,
    })
    with tempfile.NamedTemporaryFile(mode="w", suffix=".mmd", delete=False) as f:
        f.write(mermaid_code)
        temp_mmd = f.name
    try:
        result = subprocess.run([
            "npx", "mmdc",
            "-i", temp_mmd,
            "-o", str(output_path),
            "-e", "png",
            "-b", "transparent",
            "--size", "1200",
            "-p", "/opt/puppeteer/puppeteer-config.json"
        ], capture_output=True, text=True, timeout=120, env=env)
        if result.returncode != 0:
            print(f"  mmdc error: {result.stderr}")
            return False
        return True
    finally:
        Path(temp_mmd).unlink(missing_ok=True)

def extract_caption(content, match_start):
    before = content[:match_start]
    headings = re.findall(r"^(#+)\s+(.+)$", before, re.MULTILINE)
    return headings[-1][1] if headings else "Diagram"

def process_chapter(chapter_path):
    content = chapter_path.read_text(encoding="utf-8")
    original = content
    def replace_mermaid(match):
        mermaid_code = match.group(1).strip()
        caption = extract_caption(content, match.start())
        code_hash = hashlib.md5(mermaid_code.encode()).hexdigest()[:8]
        img_name = f"diagram_{code_hash}.png"
        img_path = Path("/workspace/doc/assets/rendered_diagrams") / img_name
        if not Path(img_name).exists():
            print(f"  Rendering: {img_name}")
            if not render_mermaid(mermaid_code, Path("/workspace/doc/assets/rendered_diagrams") / img_name):
                return match.group(0)
        return f"![{caption}](doc/assets/rendered_diagrams/{img_name})"
    new_content = re.sub(r"", replace_mermaid, content, flags=re.DOTALL)
    if new_content != content:
        chapter_path.write_text(new_content, encoding="utf-8")
        print(f"  Updated: {chapter_path.name}")
    return True

def extract_caption(content, match_start):
    before = content[:match_start]
    headings = re.findall(r"^(#+)\s+(.+)$", before, re.MULTILINE)
    return headings[-1][1] if headings else "Diagram"

def process_chapter(chapter_path):
    content = chapter_path.read_text(encoding="utf-8")
    original = content
    def replace_mermaid(match):
        mermaid_code = match.group(1).strip()
        caption = extract_caption(content, match.start())
        code_hash = hashlib.md5(mermaid_code.encode()).hexdigest()[:8]
        img_name = f"diagram_{code_hash}.png"
        img_path = Path("/workspace/doc/assets/rendered_diagrams") / img_name
        if not Path(img_name).exists():
            print(f"  Rendering: {img_name}")
            if not render_mermaid(mermaid_code, Path("/workspace/doc/assets/rendered_diagrams") / img_name):
                return match.group(0)
        return f"![{caption}](doc/assets/rendered_diagrams/{img_name})"
    new_content = re.sub(r"", replace_mermaid, content, flags=re.DOTALL)
    if new_content != content:
        chapter_path.write_text(new_content, encoding="utf-8")
        print(f"  Updated: {chapter_path.name}")
    return True

def extract_caption(content, match_start):
    before = content[:match_start]
    headings = re.findall(r"^(#+)\s+(.+)$", before, re.MULTILINE)
    return headings[-1][1] if headings else "Diagram"

def process_chapter(chapter_path):
    content = chapter_path.read_text(encoding="utf-8")
    original = content
    def replace_mermaid(match):
        mermaid_code = match.group(1).strip()
        caption = extract_caption(content, match.start())
        code_hash = hashlib.md5(mermaid_code.encode()).hexdigest()[:8]
        img_name = f"diagram_{code_hash}.png"
        img_path = Path("/workspace/doc/assets/rendered_diagrams") / img_name
        if not Path(img_name).exists():
            print(f"  Rendering: {img_name}")
            if not render_mermaid(mermaid_code, Path("/workspace/doc/assets/rendered_diagrams") / img_name):
                return match.group(0)
        return f"![{caption}](doc/assets/rendered_diagrams/{img_name})"
    new_content = re.sub(r"", replace_mermaid, content, flags=re.DOTALL)
    if new_content != content:
        chapter_path.write_text(new_content, encoding="utf-8")
        print(f"Updated: {chapter_path.name}")
    return True

def extract_caption(content, match_start):
    before = content[:match_start]
    headings = re.findall(r"^(#+)\s+(.+)$", before, re.MULTILINE)
    return headings[-1][1] if headings else "Diagram"

def main():
    RENDERED_DIR.mkdir(parents=True, exist_ok=True)
    Path("/opt/puppeteer").mkdir(parents=True, exist_ok=True)
    for chapter in sorted(Path("/workspace/doc/thesis").glob("*.md")):
        process_chapter(Path(chapter))
    print("Mermaid rendering complete.")

if __name__ == "__main__":
    main()
