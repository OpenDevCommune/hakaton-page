package gyber.org.hakaton.page.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class RegistrationFieldsValidatorTest {
    private RegistrationFieldsValidator registrationFieldsValidator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    public void setup(){
        registrationFieldsValidator = new RegistrationFieldsValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }


    public void checkIsValidTrue() {
        String registrationField = "Hello";
        assertTrue(registrationFieldsValidator.isValid(registrationField, constraintValidatorContext));
    }

    public void checkIsValidFalse() {
        String registrationField = "Hello&*^";
        assertFalse(registrationFieldsValidator.isValid(registrationField, constraintValidatorContext));
    }


}