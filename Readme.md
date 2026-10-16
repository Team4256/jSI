## Doctrine
1. Must be as fast as native java 
2. Compile time unit saftey
3. Explicit Conversions

## Usage
jSI is published to a Maven repository on GitHub Pages. Add the repository, then
add jSI to both configurations. It is one artifact; the second line is what lets
javac find the unit checker, and without it the checker silently does not run in
any project that also uses an annotation processor (Lombok, MapStruct, WPILib,
AdvantageKit, ...).

```groovy
repositories {
    maven { url = 'https://team4256.github.io/jSI/maven' }
}

dependencies {
    implementation      'io.github.jacobboling2040:jsi:0.1.3'
    annotationProcessor 'io.github.jacobboling2040:jsi:0.1.3'
}
```

Unit mistakes are reported as compiler warnings, so they never stop a build.

## Releasing
Versions come from git tags. Tag a commit and push the tag:

```
git tag v0.1.0
git push origin v0.1.0
```

The Publish workflow builds and tests that commit, then adds the version to the
`gh-pages` branch, which GitHub Pages serves. Published versions are never
replaced; to fix a release, tag a new version.
