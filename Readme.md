## Doctrine
1. Must be as fast as native java 
2. Compile time unit saftey
3. Explicit Conversions

## Usage
Add jSI to both configurations. It is one artifact; the second line is what
lets javac find the unit checker, and without it the checker silently does not
run in any project that also uses an annotation processor (Lombok, MapStruct, ...).

```groovy
dependencies {
    implementation      'io.github.jacobboling2040:jsi:0.1.0-SNAPSHOT'
    annotationProcessor 'io.github.jacobboling2040:jsi:0.1.0-SNAPSHOT'
}
```
