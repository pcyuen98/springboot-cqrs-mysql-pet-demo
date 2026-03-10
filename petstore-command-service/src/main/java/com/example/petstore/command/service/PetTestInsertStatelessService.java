package com.example.petstore.command.service;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.entity.TagEntity;
import com.example.petstore.command.repository.CategoryRepository;
import com.example.petstore.command.repository.TagRepository;
import com.example.petstore.common.model.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetTestInsertStatelessService {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public record SharedData(CategoryEntity category, TagEntity tag) {}

    /**
     * Prepares lookup data using standard JPA.
     */
    @Transactional
    public SharedData prepareSharedData() {
        CategoryEntity category = categoryRepository.findByName("Dogs")
                .orElseGet(() -> {
                    CategoryEntity newCat = new CategoryEntity();
                    newCat.setName("Dogs");
                    return categoryRepository.saveAndFlush(newCat);
                });

        TagEntity tag = tagRepository.findByName("Friendly")
                .orElseGet(() -> {
                    TagEntity newTag = new TagEntity();
                    newTag.setName("Friendly");
                    return tagRepository.saveAndFlush(newTag);
                });

        return new SharedData(category, tag);
    }

    /**
     * HIGH PERFORMANCE: Bypasses 1st-level cache and dirty checking.
     */
    @Transactional
    public void insertPetsStateless(List<PetWriteEntity> pets) {
        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        
        try (StatelessSession session = sessionFactory.openStatelessSession()) {
            session.beginTransaction();
            for (PetWriteEntity pet : pets) {
                session.insert(pet);
            }
            session.getTransaction().commit();
        }
    }

    public PetWriteEntity createPetEntity(int i, CategoryEntity savedCategory, TagEntity savedTag) {
        PetWriteEntity pet = new PetWriteEntity();
        pet.setName("Pet " + i);
        pet.setPhotoUrl("http://example.com/" + i + ".jpg");
        pet.setCategory(savedCategory);
        pet.setTags(List.of(savedTag));
        pet.setStatus(Status.AVAILABLE);
        pet.setDescription("Description " + i);
        pet.setCreatedAt(LocalDateTime.now());
        return pet;
    }
}