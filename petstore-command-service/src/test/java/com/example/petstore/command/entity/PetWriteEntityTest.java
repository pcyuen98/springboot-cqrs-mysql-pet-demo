package com.example.petstore.command.entity;

import com.example.petstore.command.repository.PetRepository;
import com.example.petstore.common.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PetWriteEntityTest {

    @Autowired
    private PetRepository petRepository;

    @Test
    void testSavePetWithCategoryAndTags() {
        // Category
        CategoryEntity category = new CategoryEntity();
        category.setName("Dogs");

        // Tags
        TagEntity tag1 = new TagEntity();
        tag1.setName("Playful");

        TagEntity tag2 = new TagEntity();
        tag2.setName("Small");

        // Pet
        PetWriteEntity pet = new PetWriteEntity();
        pet.setName("Buddy");
        pet.setPhotoUrl("http://example.com/dog.jpg");
        pet.setDescription("Friendly dog");
        pet.setStatus(Status.AVAILABLE);
        pet.setCategory(category);
        pet.setTags(List.of(tag1, tag2));

        PetWriteEntity saved = petRepository.save(pet);

        assertThat(saved.getPetId()).isNotNull();
        assertThat(saved.getCategory().getId()).isNotNull();
        assertThat(saved.getTags()).hasSize(2);
        assertThat(saved.getTags().get(0).getId()).isNotNull();
    }
}
