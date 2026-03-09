package com.example.petstore.command.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.entity.TagEntity;
import com.example.petstore.command.repository.CategoryRepository;
import com.example.petstore.command.repository.PetRepository;
import com.example.petstore.command.repository.TagRepository;
import com.example.petstore.common.model.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetInsertService {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    /**
     * Data carrier for shared entities.
     */
    public record SharedData(CategoryEntity category, TagEntity tag) {}

    /**
     * Prepares and saves the shared Category and Tag data.
     * Returns a Record instead of a List for type safety.
     */
    @Transactional
    public SharedData prepareSharedData() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Dogs");
        CategoryEntity savedCategory = categoryRepository.saveAndFlush(category);

        TagEntity tag = new TagEntity();
        tag.setName("Friendly");
        TagEntity savedTag = tagRepository.saveAndFlush(tag);

        return new SharedData(savedCategory, savedTag);
    }

    /**
     * Insert a single Pet entity inside its own transaction.
     * @return 
     */
    @Transactional
    public PetWriteEntity insertPet(PetWriteEntity pet) {
        return petRepository.save(pet);
    }
    
    /**
     * Helper to build the entity using the specific entities from the Record.
     */
    public PetWriteEntity createPetEntity(int i, CategoryEntity savedCategory, TagEntity savedTag) {
        PetWriteEntity pet = new PetWriteEntity();
        pet.setName("Pet " + i);
        pet.setPhotoUrl("http://example.com/" + i + ".jpg");
        pet.setCategory(savedCategory);
        pet.setTags(List.of(savedTag));
        pet.setStatus(Status.AVAILABLE);
        pet.setDescription("Description " + i);
        
        return pet;
    }
}