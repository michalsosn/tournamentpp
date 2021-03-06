package pl.lodz.p.ftims.tournamentpp.rules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

/**
 * @author Michał Sośnicki
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
public @interface Seed {
    long seed();
    long epochSecond();
    String zoneId();
}
