import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

class Assignment {

  void lengthIntoLength() {
    @m double value = 10;
    @m double another = value;
  }

  void numberIntoLength() {
    @m double value = 10;
    value = 20;
  }

  void lengthIntoTime() {
    @m double value = 10;
    assertError("cannot assign m to s");
    @s double another = value;
  }
}
