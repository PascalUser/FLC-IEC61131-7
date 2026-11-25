#!/bin/bash

mkdir -p /home/devuser/.local/share/opencode /home/devuser/.local/state \
         /home/devuser/.cache /home/devuser/.config
chown -R devuser:devgroup /workspace/output /home/devuser/.local \
         /home/devuser/.cache /home/devuser/.config
exec gosu devuser "$@"
