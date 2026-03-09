package com.example.petstore.command.repository;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.entity.TagEntity;
import com.example.petstore.command.service.PetTestInsertService;
import com.example.petstore.common.model.Status;

@SpringBootTest
class PetRepositoryStressTest {

    @Autowired
    private PetTestInsertService petInsertService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testConcurrentInsertPerformance() throws Exception {

        // --- Fix: Use separate variables so the 'saved' versions are effectively final ---
        CategoryEntity categoryToSave = new CategoryEntity();
        categoryToSave.setName("Dogs");
        final CategoryEntity savedCategory = categoryRepository.saveAndFlush(categoryToSave);

        TagEntity tagToSave = new TagEntity();
        tagToSave.setName("Friendly");
        final TagEntity savedTag = tagRepository.saveAndFlush(tagToSave);
        // --------------------------------------------------------------------------------

        int totalRecords = 1000;
        int threads = 20;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(totalRecords);

        long start = System.currentTimeMillis();

        for (int i = 0; i < totalRecords; i++) {

            final int index = i; // local primitive must also be final/effectively final

            executor.submit(() -> {
                try {
                    PetWriteEntity pet = new PetWriteEntity();
                    pet.setName("Pet " + index);
                    pet.setPhotoUrl("http://example.com/" + index + ".jpg");
                    
                    // Using the final variables here
                    pet.setCategory(savedCategory);
                    pet.setTags(List.of(savedTag));
                    
                    pet.setStatus(Status.AVAILABLE);
                    pet.setDescription("Description " + index);

                    petInsertService.insertPet(pet);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        long duration = System.currentTimeMillis() - start;
        double rate = totalRecords / (duration / 1000.0);

        System.out.println("---- CONCURRENT INSERT REPORT ----");
        System.out.println("Threads: " + threads);
        System.out.println("Total Inserts: " + totalRecords);
        System.out.println("Time(ms): " + duration);
        System.out.println("Rate: " + String.format("%.2f", rate) + " inserts/sec");
        System.out.println("----------------------------------");
    }
}