package com.example.petstore.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.command.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
