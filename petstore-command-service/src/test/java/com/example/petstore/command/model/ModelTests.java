package com.example.petstore.command.model;

import com.example.petstore.common.exception.DemoAppException;
import com.example.petstore.common.model.Status;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelTests {

	private static Validator validator;

	@BeforeAll
	static void setupValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testValidPetWrite() {
		PetWrite pet = new PetWrite();
		Category category = new Category();
		category.setId(1L);
		category.setName("Dogs");

		Tag tag = new Tag();
		tag.setId(10L);
		tag.setName("Friendly");

		pet.setPetId(100L);
		pet.setCategory(category);
		pet.setDescription("A friendly dog");
		pet.setName("Buddy");
		pet.setPhotoUrl("https://short.url");
		pet.setTags(List.of(tag));
		pet.setStatus(Status.AVAILABLE);

		Set<ConstraintViolation<PetWrite>> violations = validator.validate(pet);

		assertThat(violations).isEmpty(); // ✅ should pass validation
	}

	@Test
	void testInvalidPetWriteMissingFields() {
	    PetWrite pet = new PetWrite(); // empty object
	    pet.setPhotoUrl("invalid-url"); // force validation on photo URL

	    ValidationException ex = assertThrows(ValidationException.class, () -> {
	        validator.validate(pet);
	    });

	    assertThat(ex).hasCauseInstanceOf(DemoAppException.class);
	    assertThat(ex.getCause().getMessage())
	                 .contains("Invalid photo URL");
	}

	@Test
	void testInvalidPhotoUrl() {
	    PetWrite pet = new PetWrite();
	    pet.setCategory(new Category());
	    pet.setDescription("Desc");
	    pet.setName("Doggo");
	    pet.setStatus(Status.AVAILABLE);
	    pet.setPhotoUrl("invalid-url"); // ❌ not https://

	    Exception ex = assertThrows(ValidationException.class, () -> {
	        validator.validate(pet);
	    });

	    // 🔎 check the cause is your DemoAppException
	    assertThat(ex).hasCauseInstanceOf(DemoAppException.class);
	    assertThat(ex.getCause().getMessage())
	                 .contains("Invalid photo URL");
	}
}
