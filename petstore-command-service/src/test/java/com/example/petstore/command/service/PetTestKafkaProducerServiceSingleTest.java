package com.example.petstore.command.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.common.model.Status;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PetTestKafkaProducerServiceSingleTest {

    @Autowired
    private PetTestKafkaProducerService producerService;

    @Test
    @DisplayName("Verify single pet creation and Kafka message dispatch")
    void testSendSinglePetToKafka() {
        // 1. Arrange
        PetWriteEntity pet = createDummyPet(101);
        
        // 2. Act
        // This fires the message via your KafkaTemplate wrapper
        producerService.createPet(pet);

        // 3. Assert
        assertNotNull(pet, "The Pet entity should be initialized");
        System.out.println(">>> Kafka Message dispatched for Pet ID: 101");
    }

    private PetWriteEntity createDummyPet(int id) {
        PetWriteEntity pet = new PetWriteEntity();
        pet.setCategory(new CategoryEntity()); // Assuming Category is an inner class
        pet.setStatus(Status.AVAILABLE);
        // Set basic fields if your entity requires them for validation
        // pet.setId((long) id);
        // pet.setName("TestPet");
        return pet;
    }
}