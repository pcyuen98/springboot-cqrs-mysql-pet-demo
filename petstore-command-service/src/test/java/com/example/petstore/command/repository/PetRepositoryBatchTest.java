package com.example.petstore.command.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.service.PetTestInsertService;
import com.example.petstore.command.service.PetTestInsertService.SharedData;

@SpringBootTest
class PetRepositoryBatchTest {

    @Autowired
    private PetTestInsertService petInsertService;

    @Test
    void testSequentialInsertPerformance() {
        // 1. Prepare shared data using the new Record-based method
        SharedData sharedData = petInsertService.prepareSharedData();

        int totalRecords = 1000;
        long start = System.currentTimeMillis();

        IntStream.range(0, totalRecords).forEach(i -> {
            try {
                petInsertService.insertPet(petInsertService.createPetEntity(i, sharedData.category(), sharedData.tag()));
            } catch (Exception e) {
                System.err.println("Failed to insert record " + i + ": " + e.getMessage());
            }
        });

        long duration = System.currentTimeMillis() - start;
        double rate = totalRecords / (duration / 1000.0);

        System.out.println("---- SEQUENTIAL INSERT REPORT ----");
        System.out.println("Total Inserts: " + totalRecords);
        System.out.println("Total Time(ms): " + duration);
        System.out.println("Average Rate: " + String.format("%.2f", rate) + " inserts/sec");
        System.out.println("----------------------------------");
    }
}