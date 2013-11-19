package org.bongiorno.ws.core.dto.support;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {ElementType.LOCAL_VARIABLE, ElementType.FIELD})
@Pattern(regexp = "^(((25[0-5])|(2[0-4][0-9])|([01]?[0-9]{1,2}))\\.){3}((25[0-5])|(2[0-4][0-9])|([01]?[0-9]{1,2}))$", message = "Invalid IPv4 address.  Example: \"123.45.67.89\"")
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface IPv4Address {
    String message() default "not a 10 base ip address";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

}
