package com.example.petstore.command.service;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetTestInsertStatelessService.SharedData;

@SpringBootTest
class PetRepositoryBatchTest {

    @Autowired
    private PetTestInsertStatelessService statelessService;

    @Test
    void testStatelessBatchInsertPerformance() {
        SharedData sharedData = statelessService.prepareSharedData();
        int totalRecords = 1000;
        
        List<PetWriteEntity> pets = IntStream.range(0, totalRecords)
                .mapToObj(i -> statelessService.createPetEntity(i, sharedData.category(), sharedData.tag()))
                .toList();

        long start = System.currentTimeMillis();

        try {
            statelessService.insertPetsStateless(pets);
        } catch (Exception e) {
        	e.getStackTrace();
            System.err.println("Batch insert failed: " + e.getMessage());
        }

        long duration = System.currentTimeMillis() - start;
        double rate = totalRecords / (duration / 1000.0);

        System.out.println("---- STATELESS BATCH INSERT REPORT ----");
        System.out.println("Total Inserts: " + totalRecords);
        System.out.println("Total Time(ms): " + duration);
        System.out.println("Average Rate: " + String.format("%.2f", rate) + " inserts/sec");
        System.out.println("---------------------------------------");
    }
}