package pl.lodz.p.ftims.tournamentpp.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.lodz.p.ftims.tournamentpp.generator.Environment;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Random;

/**
 * @author Michał Sośnicki
 */
public class EnvironmentRule implements TestRule {

    private static class EnvironmentStatement extends Statement {

        private final Statement statement;
        private final long seed;
        private final long epochSecond;
        private final String zoneId;

        public EnvironmentStatement(
                Statement statement, long seed, long epochSecond, String zoneId
        ) {
            this.statement = statement;
            this.seed = seed;
            this.epochSecond = epochSecond;
            this.zoneId = zoneId;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                statement.evaluate();
            } catch (Throwable t) {
                System.err.printf("Test failed with environment:\nseed: %d\n" +
                        "epochSecond: %d\nzoneId: %s\n", seed, epochSecond, zoneId);
                throw t;
            }
        }

    }

    private Environment environment;

    @Override
    public Statement apply(Statement statement, Description description) {
        Seed seedAnnotation = description.getAnnotation(Seed.class);

        long seed;
        long epochSecond;
        String zoneId;
        if (seedAnnotation != null) {
            seed = seedAnnotation.seed();
            epochSecond = seedAnnotation.epochSecond();
            zoneId = seedAnnotation.zoneId();
        } else {
            seed = new Random().nextLong();
            epochSecond = Instant.now().getEpochSecond();
            zoneId = ZoneId.systemDefault().getId();
        }

        environment = new Environment(new Random(seed),
                Clock.fixed(Instant.ofEpochSecond(epochSecond), ZoneId.of(zoneId)),
                new BCryptPasswordEncoder());
        return new EnvironmentStatement(statement, seed, epochSecond, zoneId);
    }

    public Environment getEnvironment() {
        return environment;
    }

}
