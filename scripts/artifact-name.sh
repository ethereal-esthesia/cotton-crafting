#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PROPERTIES_FILE="$ROOT_DIR/gradle.properties"
MODE="${1:-path}"

property() {
  sed -n "s/^$1=//p" "$PROPERTIES_FILE" | head -n 1
}

plugin_version="$(property pluginVersion)"
paper_version="$(property paperApiVersion)"
artifact_base_name="$(property artifactBaseName)"

case "$MODE" in
  filename)
    printf '%s-%s-paper-%s.jar\n' "$artifact_base_name" "$plugin_version" "$paper_version"
    ;;
  display-name)
    printf '%s %s for Paper %s\n' "$artifact_base_name" "$plugin_version" "$paper_version"
    ;;
  path)
    printf '%s/build/libs/%s-%s-paper-%s.jar\n' "$ROOT_DIR" "$artifact_base_name" "$plugin_version" "$paper_version"
    ;;
  *)
    echo "Unknown mode: $MODE" >&2
    exit 1
    ;;
esac
