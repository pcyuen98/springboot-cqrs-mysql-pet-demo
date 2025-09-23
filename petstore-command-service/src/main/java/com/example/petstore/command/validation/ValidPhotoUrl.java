package com.example.petstore.command.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhotoUrlValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhotoUrl {
	String message() default "Invalid photo URL. Must start with https and be less than 50 characters";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
