package gyber.org.hakaton.page.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegistrationFieldsValidator implements ConstraintValidator<ValidateRegistrationFields, String> {
    @Override
    public void initialize(ValidateRegistrationFields constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
