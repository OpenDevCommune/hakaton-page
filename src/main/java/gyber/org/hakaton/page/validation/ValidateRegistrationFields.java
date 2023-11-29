package gyber.org.hakaton.page.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegistrationFieldsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateRegistrationFields {
    String message() default "Invalid registration fields";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
