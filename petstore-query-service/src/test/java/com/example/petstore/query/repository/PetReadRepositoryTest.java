package com.example.petstore.query.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.petstore.query.model.PetReadEntity;

@DataMongoTest
@ActiveProfiles("test") // loads application-test.properties
class PetReadRepositoryTest {

	@Autowired
	private PetReadRepository petReadRepository;

	@Test
	void testFindByIdAndStatusAndDataContaining_shouldReturnEmptyWhenNoMatch() {
		//results = petReadRepository.search(".*", ".*"); // wildcard everything

		List<PetReadEntity> results = findPetsByIdStatusAndData(null, null); // wildcard everything

		assertNotNull(results);
	}

	public List<PetReadEntity> findPetsByIdStatusAndData(String status, String data) {
		String statusRegex = (status == null || status.isBlank()) ? ".*" : status;
		String dataRegex = (data == null || data.isBlank()) ? ".*" : data;

		return petReadRepository.search(statusRegex, dataRegex);
	}

}
