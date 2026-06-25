# CottonCrafting

Paper plugin that registers a small cotton-adjacent crafting recipe for the
server: one white wool block can be unpacked into four string.

## Build

```bash
./scripts/build-release.sh
```

The release jar is named:

```text
build/libs/CottonCrafting-<pluginVersion>-paper-<paperApiVersion>.jar
```

## Release Pinning

Release metadata is pinned in `gradle.properties`.

The `Release for Paper Version` GitHub workflow accepts a Paper version and full
Paper API dependency version, updates the pin, bumps the plugin patch version
when the Paper pin changes, commits that change to `main`, and creates a GitHub
release with the built jar.
