package com.example.petstore.command.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.entity.TagEntity;
import com.example.petstore.command.service.PetInsertService;
import com.example.petstore.common.model.Status;

@SpringBootTest
class PetRepositoryBatchTest {

    @Autowired
    private PetInsertService petInsertService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testSequentialInsertPerformance() {

        // Prepare shared data
        CategoryEntity category = new CategoryEntity();
        category.setName("Dogs");
        CategoryEntity savedCategory = categoryRepository.saveAndFlush(category);

        TagEntity tag = new TagEntity();
        tag.setName("Friendly");
        TagEntity savedTag = tagRepository.saveAndFlush(tag);

        int totalRecords = 1000;
        long start = System.currentTimeMillis();

        // Standard sequential loop
        for (int i = 0; i < totalRecords; i++) {
            try {
                PetWriteEntity pet = new PetWriteEntity();
                pet.setName("Pet " + i);
                pet.setPhotoUrl("http://example.com/" + i + ".jpg");
                pet.setCategory(savedCategory);
                pet.setTags(List.of(savedTag));
                pet.setStatus(Status.AVAILABLE);
                pet.setDescription("Description " + i);

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