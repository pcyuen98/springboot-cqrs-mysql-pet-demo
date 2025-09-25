package com.example.petstore.command.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.petstore.common.exception.DemoAppException;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

class PhotoUrlValidatorTest {

    private PhotoUrlValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PhotoUrlValidator();

        // Provide a fake ValidPhotoUrl annotation so validator.initialize() works
        ValidPhotoUrl annotation = new ValidPhotoUrl() {
            @Override
            public String message() {
                return "Invalid photo URL";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            @SuppressWarnings("unchecked")
            public Class<? extends Payload>[] payload() {
                return (Class<? extends Payload>[]) new Class[0];
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return ValidPhotoUrl.class;
            }
        };

        validator.initialize(annotation);
    }

    @Test
    void whenValidUrl_thenReturnsTrue() {
        String url = "https://example.com/image.jpg";
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        boolean result = validator.isValid(url, context);

        assertTrue(result, "Expected valid URL to pass validation");
    }

    @Test
    void whenInvalidUrl_thenThrowsDemoAppException() {
        String url = "ftp://example.com/image.jpg"; // ❌ invalid
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        DemoAppException ex = assertThrows(DemoAppException.class,
                () -> validator.isValid(url, context));

        assertEquals("Invalid photo URL", ex.getMessage());
    }

    @Test
    void whenNullOrBlank_thenThrowsDemoAppException() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertThrows(DemoAppException.class,
                () -> validator.isValid(null, context));

        assertThrows(DemoAppException.class,
                () -> validator.isValid("", context));
    }

    @Test
    void whenTooLongUrl_thenThrowsDemoAppException() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        String tooLongUrl = "https://example.com/" + "a".repeat(100); // >50 chars
        assertThrows(DemoAppException.class,
                () -> validator.isValid(tooLongUrl, context));
    }
}
