import static org.jsi.Expect.assertError;

import java.util.function.DoubleSupplier;

import org.jsi.annotations.Units.*;

/** Arguments, returns, varargs, constructors, lambdas and method references. */
class Methods {

  @m double distance = 10;
  @s double time = 2;

  static class Wheel {
    Wheel(@m double radius) {}
  }

  @m double total(@m double... parts) {
    return 0;
  }

  @m_s double speed(@m double d, @s double t) {
    return d / t;
  }

  @m double returnsALength() {
    return distance;
  }

  @m double returnsANumber() {
    return 5;
  }

  @m double returnsATime() {
    assertError("cannot return s from a method returning m");
    return time;
  }

  double unannotatedReturnIsNotChecked() {
    return time;
  }

  void argumentsInOrder() {
    @m_s double v = speed(distance, time);
  }

  void argumentsSwapped() {
    assertError("cannot pass s as m");
    assertError("cannot pass m as s");
    speed(time, distance);
  }

  void resultIsTheReturnUnit() {
    assertError("cannot assign m_s to s");
    @s double wrong = speed(distance, time);
  }

  void resultOfAMemberCall() {
    assertError("cannot assign m to s");
    @s double wrong = this.returnsALength();
  }

  void varargsSpread() {
    @m double sum = total(distance, distance, 3);
    @m double none = total();
  }

  void varargsSpreadWrongUnit() {
    assertError("cannot pass s as m");
    total(distance, time);
  }

  void varargsArrayPassedThrough() {
    @m double[] parts = {1, 2};
    @m double sum = total(parts);
  }

  void varargsWrongArrayPassedThrough() {
    @s double[] times = {1, 2};
    assertError("cannot pass s as m");
    total(times);
  }

  void constructorArguments() {
    new Wheel(distance);
  }

  void constructorWrongArgument() {
    assertError("cannot pass s as m");
    new Wheel(time);
  }

  void lambdaBodiesAreChecked() {
    Runnable r = () -> {
      assertError("cannot assign m to s");
      @s double wrong = distance;
    };
  }

  void lambdaResultsAreNotChecked() {
    DoubleSupplier anyTime = () -> time;
    DoubleSupplier alsoAnyTime = () -> { return time; };
  }

  void methodReferencesHaveNoUnit() {
    DoubleSupplier length = this::returnsALength;
  }
}
