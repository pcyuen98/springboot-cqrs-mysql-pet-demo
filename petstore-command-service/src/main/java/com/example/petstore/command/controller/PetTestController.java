package com.example.petstore.command.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetInsertService;

import jakarta.validation.Valid;

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

    private final PetInsertService petService;

    /**
     * Creates a new pet entry.
     * * @param pet The pet entity containing provided Category and Tag IDs.
     * @return The fully resolved and persisted PetWriteEntity.
     */
    @PostMapping
    public ResponseEntity<PetWriteEntity> createPet() {
        log.info("Received request to create pet: {}", pet);

        // All the logic for finding Categories, Tags, and setting 
        // defaults is now encapsulated within the Service.
        PetWriteEntity savedPet = petService.createPetEntity();

        log.info("Pet created successfully with ID: {}", savedPet.getId());
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPet);
    }
}