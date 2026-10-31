#!/bin/bash

chown -R devuser:devgroup /workspace/output
exec gosu devuser "$@"