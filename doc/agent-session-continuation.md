# Continuación de sesiones de agentes OpenCode (HARNESS)

Desde el patch 0037, la continuación de sesiones es **optativa**. No es lo mismo
que `--resume`: ese flag omite etapas `SUCCEEDED` del manifiesto; el nuevo
`--resume-agent-sessions` retoma una conversación OpenCode que quedó incompleta.

## Ejecución

```bash
docker compose exec -u devuser docs python3 run_all_agents.py \
  --modules parser \
  --only documentation_inventory,docs_update_parser \
  --resume-agent-sessions \
  --opencode-model google/gemini-3.6-flash
```

Si el agente devuelve un error de cuota, el HARNESS conserva el ID de sesión
**si OpenCode lo emitió** mediante `--format json`. Cuando tengas otro modelo
disponible, podés continuar la misma tarea en su historial:

```bash
docker compose exec -u devuser docs python3 run_all_agents.py \
  --modules parser \
  --only docs_update_parser \
  --resume-agent-sessions \
  --opencode-model google/gemini-3.5-flash
```

El flag `--opencode-model` selecciona el modelo solo para esa invocación; no
requiere modificar `opencode.json`. No cambia el nombre ni las instrucciones
del agente, aunque puede cambiarse el modelo de la misma sesión.

## Checkpoints

- `output/agent_sessions/docs_update_parser.json` conserva estado `PAUSED` o
  `COMPLETED`, ID de sesión, modelo utilizado e identificadores SHA256.
- El historial real de herramientas y mensajes vive en la base local de
  OpenCode, no en este JSON. Docker Compose incluye un volumen nombrado
  `flc-opencode-data` que conserva el historial ante recreaciones **futuras**.
  Las sesiones que ya existían exclusivamente en el contenedor anterior no
  migran automáticamente al volumen nuevo.
- La continuación se cancela si cambia el comando/tarea, el commit o el diff
  de archivos versionados. Los archivos no versionados no se incluyen en ese
  fingerprint: el agente siempre debe volver a verificar la evidencia.
- Si se perdió la sesión de OpenCode, eliminá únicamente el checkpoint de la
  etapa (por ejemplo, `rm output/agent_sessions/docs_update_parser.json`) y
  ejecutá de nuevo. No utilices `--continue`, que podría seleccionar otra tarea.
- Si OpenCode falla antes de emitir un ID de sesión, el HARNESS no inventa uno.
- El estado `PAUSED` **no certifica progreso**. Sólo `HARNESS_RESULT_JSON` del
  turno actual, validado por `validate_agent_output` y `git apply --check`,
  permite aprobar un resultado. Los patches siguen sin aplicarse automáticamente.

## Intervalos de Gemini por modelo

Podés configurarlos en `.env`:

```dotenv
FLC_LLM_INTERVAL_MS=16000
FLC_LLM_MODEL_INTERVALS_JSON={"gemini-3.6-flash":16000,"gemini-3.5-flash":25000}
```

Esos intervalos son **ejemplos**, no las cuotas reales. Consultá
[Google AI Studio](https://aistudio.google.com/rate-limit) para tu cuenta y
proyecto. Si un modelo no aparece en el mapa, usa el intervalo predeterminado.
El plugin sólo actúa para `providerID: google`; el estado de cada modelo se
guarda por separado en `output/`. Se respeta la frecuencia de llamadas, pero
**no se garantiza** la disponibilidad de una cuota diaria o TPM. Las solicitudes
hechas desde otras aplicaciones consumen la cuota del proveedor igualmente.

Para verificar el resultado, ejecutá la suite de Python y revisá
`output/harness_state.json`, los checkpoints y `output/harness_logs/`.
