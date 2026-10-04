#!/usr/bin/env python3
"""
Runs all documentation agents in sequence for full documentation refresh.
Usage: python run_all_agents.py

Note: Some steps require the opencode CLI which is not installed in the Docker container.
Those steps will be skipped with a warning. Run them manually on the host:
  opencode run docs-update utils
  opencode run docs-update parser
  opencode run docs-update lexer
  opencode run readme-update
  opencode run javadoc-fix
"""
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).parent

def run(cmd, desc):
    print(f"\n{'='*60}")
    print(f"🔄 {desc}")
    print(f"   $ {' '.join(str(c) for c in cmd)}")
    result = subprocess.run([str(c) for c in cmd], cwd=str(ROOT))
    if result.returncode != 0:
        print(f"❌ Failed: {desc}")
        sys.exit(1)
    print(f"✅ Done: {desc}")

def try_opencode(cmd, desc):
    """Try to run opencode command, skip gracefully if not available"""
    print(f"\n{'='*60}")
    print(f"🔄 {desc}")
    print(f"   $ opencode run {' '.join(str(c) for c in cmd)}")
    
    try:
        # Intentamos ejecutar el comando directamente
        result = subprocess.run(["opencode", "run"] + [str(c) for c in cmd], cwd=str(ROOT))
        if result.returncode != 0:
            print(f"❌ Failed: {desc}")
            return False
        print(f"✅ Done: {desc}")
        return True
        
    except FileNotFoundError:
        # Si 'opencode' no está instalado/disponible, subprocess lanza FileNotFoundError
        print(f"⚠️  SKIP: {desc} (opencode not available in container)")
        print(f"   Run manually on host: opencode run {' '.join(str(c) for c in cmd)}")
        return False

def main():
    # 1. Statistical charts (PNGs)
    run(["python3", "scripts/extract_stats.py"], "Statistical charts")
    
    # 2. Mermaid diagrams (raw .mmd files)
    run(["python3", "scripts/generate_diagrams.py", "all"], "Mermaid diagrams")
    
    # 3. Module documentation (requires opencode)
    for pkg in ["utils", "parser", "lexer"]:
        try_opencode(["docs-update", pkg], f"Module docs: {pkg}")
    
    # 4. Synthesis
    run(["python3", "scripts/synthesize_thesis_context.py"], "Synthesis")
    
    # 5. Thesis build (renders mermaid, compiles docx)
    run(["bash", "scripts/build_thesis.sh"], "Thesis build")
    
    # 6. README (via opencode if available)
    try_opencode(["readme-update"], "README update")
    
    # 7. Javadoc fix
    try_opencode(["javadoc-fix"], "Javadoc fix")
    
    # 8. Validation
    run(["python3", "scripts/validate_docs.py"], "Validation")

if __name__ == "__main__":
    main()