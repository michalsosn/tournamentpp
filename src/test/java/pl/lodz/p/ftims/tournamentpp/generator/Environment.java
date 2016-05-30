package pl.lodz.p.ftims.tournamentpp.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Random;

/**
 * @author Michał Sośnicki
 */
public class Environment {
    private final Random random;
    private final Clock clock;
    private PasswordEncoder passwordEncoder;
    private long uniqueCounter = 0;

    public Environment(Random random, Clock clock, PasswordEncoder passwordEncoder) {
        this.random = random;
        this.clock = clock;
        this.passwordEncoder = passwordEncoder;
    }

    public static Environment makeDefault() {
        return new Environment(
                new Random(),
                Clock.fixed(Instant.now(), ZoneId.systemDefault()),
                new BCryptPasswordEncoder()
        );
    }

    public Random getRandom() {
        return random;
    }

    public Clock getClock() {
        return clock;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public long nextUnique() {
        return uniqueCounter++;
    }

    @Override
    public String toString() {
        return "Environment{"
             + "random=" + random
             + ", clock=" + clock
             + ", passwordEncoder=" + passwordEncoder
             + ", uniqueCounter=" + uniqueCounter
             + '}';
    }
}

