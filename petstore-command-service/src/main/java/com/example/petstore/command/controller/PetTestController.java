package com.example.petstore.command.controller;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetInsertService;
import com.example.petstore.command.service.PetInsertService.SharedData;
import com.example.petstore.command.service.PetMetricsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for managing Pet lifecycle operations and performance testing.
 * This version tracks insertion metrics including throughput and latency.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/test/pets")	
@RequiredArgsConstructor
public class PetTestController {

    private final PetInsertService petInsertService;
    private final PetMetricsService metricsService;

    /**
     * Creates a new pet entry and records execution time.
     * * @return The fully resolved and persisted PetWriteEntity.
     */
    @GetMapping
    public ResponseEntity<PetWriteEntity> createPet() {
        long startTime = System.currentTimeMillis();
        
        try {
            SharedData sharedData = petInsertService.prepareSharedData();
            int randomId = ThreadLocalRandom.current().nextInt(1, 1000000);
            
            PetWriteEntity savedPet = petInsertService.insertPet(
                petInsertService.createPetEntity(randomId, sharedData.category(), sharedData.tag())
            );

            // Calculate and record duration
            long duration = System.currentTimeMillis() - startTime;
            metricsService.recordInsert(duration);

            log.info("Pet created successfully with ID: {} in {}ms", savedPet.getPetId(), duration);
            
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedPet);
        } catch (Exception e) {
            log.error("Failed to create pet: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Returns real-time performance metrics for the insertion process.
     * Includes: Total count, Inserts Per Minute (IPM), Min, Max, and Avg latency.
     * * @return A map of performance statistics.
     */
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getInsertionMetrics() {
        log.debug("Fetching pet insertion metrics");
        return ResponseEntity.ok(metricsService.getMetrics());
    }
}