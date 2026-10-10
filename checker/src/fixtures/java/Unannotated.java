import org.jsi.annotations.Units.*;

/**
 * Values from code without units are trusted, not checked: these all compile
 * cleanly. Annotating the code they come from is what brings them under checking.
 */
class Unannotated {

  double plainField = 5;

  double plainMethod() {
    return 5;
  }

  void takesPlain(double anything) {}

  void unannotatedVariableIntoAUnit() {
    @s double time = plainField;
  }

  void unannotatedMethodResultIntoAUnit() {
    @s double time = plainMethod();
  }

  void unannotatedLibraryResult() {
    @m double distance = 10;
    @s double time = Math.abs(distance);
  }

  void arithmeticWithAnUnannotatedValue() {
    @m double distance = 10;
    @s double time = distance * plainField;
  }

  void piIsUnannotated() {
    @m double radius = 1;
    @s double time = 2 * Math.PI * radius;
  }

  void anyUnitIntoAnUnannotatedParameter() {
    @m double distance = 10;
    takesPlain(distance);
  }
}
