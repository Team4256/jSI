import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

class Calls {

  void move(@m double by) {}

  @m double lastMove() {
    return 10;
  }

  void passingALength() {
    @m double distance = 10;
    move(distance);
  }

  void passingATime() {
    @s double time = 2;
    assertError("cannot pass s as m");
    move(time);
  }

  void returnedLengthIntoTime() {
    assertError("cannot assign m to s");
    @s double wrong = lastMove();
  }
}
