package com.example.petstore.command.service;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.common.model.Status;

@SpringBootTest

class PetTestKafkaProducerServicePerformanceTest {



    @Autowired
    private PetTestKafkaProducerService producerService;

    @Test
    void stressTestWithRealKafka() throws InterruptedException {
        int totalRecords = 10_000;
        AtomicInteger counter = new AtomicInteger(0);
        
        // Use Virtual Threads to handle 200 concurrent tasks efficiently
        long startTime = System.currentTimeMillis();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalRecords; i++) {
                executor.submit(() -> {
                    PetWriteEntity pet = createDummyPet(counter.incrementAndGet());
                    producerService.createPet(pet);
                });
            }
            // The try-with-resources block implicitly calls executor.close(), 
            // waiting for all 10k virtual threads to complete their submissions.
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double rate = (double) totalRecords / (duration / 1000.0);

        System.out.println("---- SEQUENTIAL INSERT REPORT ----");
        System.out.println("Total Inserts: " + totalRecords);
        System.out.println("Total Time(ms): " + duration);
        System.out.println("Average Rate: " + String.format("%.2f", rate) + " inserts/sec");
        System.out.println("----------------------------------");
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