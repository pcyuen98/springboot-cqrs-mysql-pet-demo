package com.example.petstore.command.validation;

import com.example.petstore.common.exception.DemoAppException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhotoUrlValidator implements ConstraintValidator<ValidPhotoUrl, String> {

	private static final String URL_REGEX = "^http[s]?://.*$";

    private String message;

    @Override
    public void initialize(ValidPhotoUrl constraintAnnotation) {
        // Capture the message defined in the annotation
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = value != null
                && !value.isBlank()
                && value.length() <= 500
                && value.matches(URL_REGEX);

        if (!isValid) {
            // Throw your custom exception with the annotation message
            throw new DemoAppException(message);
        }

        return true;
    }
}
