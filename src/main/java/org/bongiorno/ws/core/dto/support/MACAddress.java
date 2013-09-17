package org.bongiorno.ws.core.dto.support;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author cbongiorno
 *         Date: 6/7/12
 *         Time: 5:25 PM
 */
@Target(value = {ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@Pattern(regexp = "\\b(([0-9a-f]){2}([:-])){5}[0-9a-f]{2}\\b", message = "Bad MAC address. Example: \"aa:aa:aa:aa:aa:aa\"")
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface MACAddress {
    String message() default "not a base16 encoded MAC address";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}
