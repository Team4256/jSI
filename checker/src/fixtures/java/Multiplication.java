import static org.jsi.Expect.assertError;

import org.jsi.annotations.Units.*;

class Multiplication {
    void distancePerTime() {
        @m double distance = 10;
        @s double time = 1;
        @m_s double speed = distance / time;
    }

    void distancePerTimePerTime() {
        @m double distance = 10;
        @s double time = 2;
        @m_s2 double accleration = distance / (time * time);
    }

    void newtons() {
        @m_s2 double accleration = 10;
        @kg double mass = 1;
        @N double force = mass * accleration;
    }

    void torqueAndWork() {
        @N double force = 10;
        @m double distance = 10;
        @Nm double torque = force * distance;
        @J double work = force * distance;
    }
}
