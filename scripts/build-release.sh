#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

"$ROOT_DIR/gradlew" -p "$ROOT_DIR" clean build
artifact="$("$ROOT_DIR/scripts/artifact-name.sh" path)"
test -f "$artifact" || {
  echo "Expected release artifact was not built: $artifact" >&2
  exit 1
}
echo "Built release artifact: $artifact"
