package com.example.petstore.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.command.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
