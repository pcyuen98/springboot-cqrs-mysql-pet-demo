package com.example.petstore.command.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.command.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	
	/**
     * Finds a category by its name.
     * @param name The name of the category to search for.
     * @return An Optional containing the category if found, or empty if not.
     */
    Optional<CategoryEntity> findByName(String name);
}
