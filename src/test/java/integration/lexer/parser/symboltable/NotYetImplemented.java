package integration.lexer.parser.symboltable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Disabled;

/**
 * Annotation to mark test methods that are not yet implemented.
 * <p>
 * Methods annotated with {@code @NotYetImplemented} are automatically disabled
 * via JUnit's {@link Disabled} meta-annotation, preventing them from running
 * while still keeping them in the test suite as reminders.
 * </p>
 *
 * @author Matias Ortiz
 * @author Victoriano Etcheverría
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Disabled("Not Yet Implemented")
public @interface NotYetImplemented {
}