# HARNESS de documentación

## Propósito

El **HARNESS de documentación** es un conjunto de 8 agentes de [OpenCode](https://opencode.ai) que mantienen README, documentación de módulos, gráficos estadísticos, diagramas relacionales y la tesis sincronizados con el código real. Ningún agente escribe números o afirmaciones que no pueda señalar en un archivo `.java` concreto — esa restricción está en el prompt de cada agente.

## Agentes

| Agente               | Rol                                                               | Comando                                             |
|----------------------|-------------------------------------------------------------------|-----------------------------------------------------|
| `readme-writer`      | Mantiene `README.md`                                              | `/readme-update`                                    |
| `doc-writer`         | Documenta un paquete en `doc/modules/<paquete>.md`                | `/docs-update <paquete>`                            |
| `chart-artist`       | Genera gráficos estadísticos (pie/bar/histograma)                 | `/chart-refresh`                                    |
| `diagram-architect`  | Genera diagramas Mermaid (class, sequence, flow, package, object) | `/diagram-refresh <scope>`                          |
| `thesis-writer`      | Escribe capítulos de tesis y compila `Tesis.docx`                 | `/thesis-build [foco]`                              |
| `doc-validator`      | Valida todo markdown en `doc/`                                    | `/doc-validate`                                     |
| `harness-documenter` | Documenta el harness en capítulo de tesis (interno)               | (invocado por thesis-writer)                        |
| `javadoc-updater`    | Mantiene Javadoc con cero enlaces rotos                           | `/javadoc-audit`, `/javadoc-fix`, `/javadoc-verify` |

También invocables directamente con `@nombre-agente` dentro de una sesión de OpenCode.

## Pipeline de documentación

```
Cambio de código
    │
    ├─► /chart-refresh
    │     extract_stats.py → doc/assets/*.png + stats.json
    │
    ├─► /diagram-refresh <scope>
    │     generate_diagrams.py → doc/diagrams/*.mmd (raw mermaid)
    │
    ├─► /docs-update <paquete>
    │     doc-writer lee síntesis + mermaid → doc/modules/<paquete>.md
    │
    ├─► /synthesis
    │     synthesize_thesis_context.py → doc/_synthesis_context.json
    │
    ├─► /thesis-build [foco]
    │     thesis-writer lee síntesis + mermaid → doc/thesis/*.md
    │     render_mermaid.py → ../assets/rendered_diagrams/*.png
    │     build_thesis.sh → Tesis.docx
    │
    └─► /readme-update
          readme-writer lee pom.xml + síntesis → README.md
```

## Scripts del pipeline

| Script                                 | Propósito                                                                                           |
|----------------------------------------|-----------------------------------------------------------------------------------------------------|
| `scripts/extract_stats.py`             | Gráficos estadísticos (pie/bar/histograma) + métricas (CYCLO, LOC, COVERAGE, coupling) → PNG + JSON |
| `scripts/generate_diagrams.py`         | Diagramas relacionales (class, sequence, flow, package, object) → `.mmd`                            |
| `scripts/render_mermaid.py`            | Renderiza ```mermaid``` en capítulos tesis → PNG en `../assets/rendered_diagrams/`                  |
| `scripts/synthesize_thesis_context.py` | Lee modules + stats + diagrams → `doc/_synthesis_context.json` (single source of truth)             |
| `scripts/build_thesis.sh`              | Concatena capítulos + pandoc (markdown-raw_tex) → `Tesis.docx`                                      |
| `scripts/validate_docs.py`             | Valida markdown (sin imágenes markdown, sin paréntesis en headings, PNGs existen, mermaid válido)   |

## Flujo mermaid → PNG en tesis

1. `thesis-writer` escribe capítulos con bloques ```mermaid``` (contenido desde `thesis_view.diagrams` en síntesis)
2. `build_thesis.sh` ejecuta `render_mermaid.py`:
   - Busca bloques ```mermaid``` en `doc/thesis/*.md`
   - Extrae contenido, renderiza con `npx mmdc` (config puppeteer: `--no-sandbox --disable-setuid-sandbox`)
   - Guarda PNG en `../assets/rendered_diagrams/diagram_<hash>.png`
   - Reemplaza bloque ```mermaid``` por `![Caption](../assets/rendered_diagrams/diagram_<hash>.png)`
3. `pandoc` compila con `--resource-path="$ROOT_DIR:$ROOT_DIR/doc/assets:$ROOT_DIR/doc/assets/rendered_diagrams"`

## Agents y validación

Cada agente que genera markdown **debe** ejecutar `python3 scripts/validate_docs.py` al finalizar. El validador verifica:

| Regla                     | Check                                              |
|---------------------------|----------------------------------------------------|
| No markdown images        | `grep -r '!\[.*\](' doc/` = vacío (excepto thesis) |
| No paréntesis en headings | `grep '^#.*[()]' doc/` = vacío                     |
| No brackets en headings   | `grep '^#.*\[\]' doc/` = vacío                     |
| No braces en headings     | `grep '^#.*{}' doc/` = vacío                       |
| PNG refs existen          | Todo `assets/*.png` referenciado existe            |
| Mermaid válido            | Todos bloques ```mermaid``` parsean                |
| No LucaInfo refs          | `grep -r LucaInfo doc/` = vacío                    |
| Heading hierarchy         | No saltos de nivel                                 |

## Comandos disponibles

| Comando                    | Agente            | Descripción                                  |
|----------------------------|-------------------|----------------------------------------------|
| `/readme-update`           | readme-writer     | Actualiza README.md desde pom.xml + síntesis |
| `/docs-update <pkg>`       | doc-writer        | Documenta módulo en `doc/modules/<pkg>.md`   |
| `/chart-refresh`           | chart-artist      | Regenera gráficos estadísticos PNG           |
| `/diagram-refresh <scope>` | diagram-architect | Regenera diagramas Mermaid                   |
| `/thesis-build [foco]`     | thesis-writer     | Escribe capítulos + compila Tesis.docx       |
| `/doc-validate`            | doc-validator     | Valida todo markdown en doc/                 |
| `/javadoc-audit`           | javadoc-updater   | Escanea y reporta issues Javadoc             |
| `/javadoc-fix`             | javadoc-updater   | Arregla links rotos, parámetros faltantes    |
| `/javadoc-verify`          | javadoc-updater   | Confirma `mvn javadoc:javadoc` sin warnings  |

## Instalación

```bash
# Copiar a la raíz del repo
cp -r opencode.json .opencode/ scripts/ doc/thesis/ doc/assets/ doc/diagrams/ doc/modules/ .

# Requisitos: python3 + matplotlib, pandoc, nodejs/npm, @mermaid-js/mermaid-cli, yauzl
# Docker (recomendado):
docker compose build
docker compose up -d docs
docker compose exec docs bash
python run_all_agents.py
```

## Extensibilidad

Para agregar un agente nuevo:
1. Crear `.opencode/agents/nuevo-agente.md` con prompt
2. Añadir a `opencode.json` bajo `agent`
3. Añadir comando bajo `command` si necesario
4. Actualizar `doc/HARNESS.md`
5. Añadir reglas de validación si genera markdown