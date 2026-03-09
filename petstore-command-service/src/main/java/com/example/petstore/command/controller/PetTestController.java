package com.example.petstore.command.controller;


import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetInsertService;
import com.example.petstore.command.service.PetInsertService.SharedData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for managing Pet lifecycle operations.
 * This version follows the "Thin Controller" pattern by delegating 
 * entity resolution and business logic to the Service layer.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetTestController {

    private final PetInsertService petInsertService;

    /**
     * Creates a new pet entry.
     * * @param pet The pet entity containing provided Category and Tag IDs.
     * @return The fully resolved and persisted PetWriteEntity.
     */
    @PostMapping
    public ResponseEntity<PetWriteEntity> createPet() {
    	SharedData sharedData = petInsertService.prepareSharedData();

    	// Inside your createPet method:
    	int randomId = ThreadLocalRandom.current().nextInt(1, 1000000); // Range 1 to 999,999
    	PetWriteEntity savedPet = petInsertService.insertPet(
    	    petInsertService.createPetEntity(randomId, sharedData.category(), sharedData.tag())
    	);
        log.info("Pet created successfully with ID: {}", savedPet.getPetId());
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPet);
    }
}