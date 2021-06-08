package om.opejr.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = NotEqualFieldsValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEqualFields {
    String message() default "Fields are not equal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String originalField();
    String fieldToCompare();
}

