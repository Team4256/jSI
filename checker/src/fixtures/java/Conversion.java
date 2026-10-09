import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;
import org.jsi.conversions.Conversions;

class Conversion {
    void mToKM() {
        @m double meters = 1;
        @km double kilometers = Conversions.metersToKilometers(meters);
    }
}
