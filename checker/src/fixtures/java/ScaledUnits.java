import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

/** Units of one dimension at different scales never mix without a conversion. */
class ScaledUnits {

  void sameScaleIsFine() {
    @km double a = 1, b = 2;
    @km double total = a + b;
  }

  void kilometersAreNotMeters() {
    @km double trip = 5;
    assertError("explicit conversion required between km and m");
    @m double wrong = trip;
  }

  void addingAcrossScales() {
    @km double trip = 5;
    @m double extra = 300;
    assertError("explicit conversion required between km and m");
    double total = trip + extra;
  }

  void comparingAcrossScales() {
    @mm double gap = 5;
    @cm double limit = 1;
    assertError("explicit conversion required between mm and cm");
    boolean tooBig = gap > limit;
  }

  void gramsAreNotKilograms() {
    @g double grams = 500;
    assertError("explicit conversion required between g and kg");
    @kg double wrong = grams;
  }

  void scalingKeepsTheScale() {
    @ms double period = 20;
    assertError("explicit conversion required between ms and s");
    @s double wrong = 2 * period;
  }

  void scaleAndDimensionAreSeparateMistakes() {
    @mg double mass = 5;
    assertError("cannot assign mg to us");
    @us double wrong = mass;
  }

  void computedScaledUnitsShowAsFormulas() {
    @km double far = 5;
    @s double time = 2;
    assertError("explicit conversion required between 1000·m·s⁻¹ and m_s");
    @m_s double wrong = far / time;
  }

  void tinyScalesShowAsPowersOfTen() {
    @mm double side = 2;
    assertError("cannot assign 10⁻⁶·m² to m");
    @m double wrong = side * side;
  }
}
