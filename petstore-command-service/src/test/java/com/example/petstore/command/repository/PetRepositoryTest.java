package com.example.petstore.command.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.entity.TagEntity;
import com.example.petstore.common.model.Status;

@DataJpaTest
@ActiveProfiles("test") // Uses application-test.properties for H2 setup
class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    /**
     * Helper method to create and persist a Pet with category + tags.
     */
    private PetWriteEntity createAndSavePet(String petName, String categoryName) {
        // Category
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryName);

        // Tags
        TagEntity tag1 = new TagEntity();
        tag1.setName("Friendly");

        TagEntity tag2 = new TagEntity();
        tag2.setName("Small");

        // Pet
        PetWriteEntity pet = new PetWriteEntity();
        pet.setName(petName);
        pet.setDescription("Sample description for " + petName);
        pet.setPhotoUrl("http://example.com/" + petName.toLowerCase() + ".jpg");
        pet.setStatus(Status.AVAILABLE);
        pet.setCategory(category);
        pet.setTags(List.of(tag1, tag2));

        return petRepository.save(pet);
    }

    @Test
    void testSaveAndFindPet() {
        // given
        PetWriteEntity savedPet = createAndSavePet("Buddy", "Dogs");

        // then
        assertThat(savedPet.getPetId()).isNotNull();
        assertThat(savedPet.getCategory().getId()).isNotNull();
        assertThat(savedPet.getTags()).hasSize(2);
        assertThat(savedPet.getTags().get(0).getId()).isNotNull();

        // fetch back
        PetWriteEntity foundPet = petRepository.findById(savedPet.getPetId()).orElseThrow();
        assertThat(foundPet.getName()).isEqualTo("Buddy");
        assertThat(foundPet.getCategory().getName()).isEqualTo("Dogs");
    }

    @Test
    void testDeletePet() {
        // given
        PetWriteEntity savedPet = createAndSavePet("Kitty", "Cats");

        // when
        petRepository.deleteById(savedPet.getPetId());

        // then
        assertFalse(petRepository.findById(savedPet.getPetId()).isPresent());
    }
}
