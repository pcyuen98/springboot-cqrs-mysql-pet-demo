package com.example.petstore.command.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetInsertService;
import com.example.petstore.command.service.PetInsertService.SharedData;

@SpringBootTest
class PetRepositoryBatchTest {

    @Autowired
    private PetInsertService petInsertService;

    @Test
    void testSequentialInsertPerformance() {
        // 1. Prepare shared data using the new Record-based method
        SharedData sharedData = petInsertService.prepareSharedData();

        int totalRecords = 1000;
        long start = System.currentTimeMillis();

        // 2. Standard sequential loop
        for (int i = 0; i < totalRecords; i++) {
            try {
                // 3. Use the helper method and pass entities from the record
                PetWriteEntity pet = petInsertService.createPetEntity(
                    i, 
                    sharedData.category(), 
                    sharedData.tag()
                );

                petInsertService.insertPet(pet);
            } catch (Exception e) {
                System.err.println("Failed to insert record " + i + ": " + e.getMessage());
            }
        }

        long duration = System.currentTimeMillis() - start;
        double rate = totalRecords / (duration / 1000.0);

        System.out.println("---- SEQUENTIAL INSERT REPORT ----");
        System.out.println("Total Inserts: " + totalRecords);
        System.out.println("Total Time(ms): " + duration);
        System.out.println("Average Rate: " + String.format("%.2f", rate) + " inserts/sec");
        System.out.println("----------------------------------");
    }
}