package gyber.org.hakaton.page.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegistrationFieldsValidator implements ConstraintValidator<ValidateRegistrationFields, String> {
    @Override
    public void initialize(ValidateRegistrationFields validateRegistrationFields) {
    }

    @Override
    public boolean isValid(String registrationField, ConstraintValidatorContext constraintValidatorContext) {
        return registrationField.matches("^[a-zA-Z]+$");
    }
}
