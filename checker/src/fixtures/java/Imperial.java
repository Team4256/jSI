import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** Imperial lengths are lengths at their own scales. */
class Imperial {

  void writingImperialLengths() {
    @in double bumper = 3.5;
    @ft double field = 54;
    @yd double run = 10;
  }

  void inchesAreNotMeters() {
    @in double bumper = 3.5;
    assertError("explicit conversion required between in and m");
    @m double wrong = bumper;
  }

  void feetAreNotInches() {
    @ft double field = 54;
    assertError("explicit conversion required between ft and in");
    @in double wrong = field;
  }

  void addingFeetAndInches() {
    @ft double feet = 5;
    @in double inches = 8;
    assertError("explicit conversion required between ft and in");
    double height = feet + inches;
  }

  void inchesAreStillALength() {
    @in double bumper = 3.5;
    assertError("cannot assign in to s");
    @s double wrong = bumper;
  }
}
