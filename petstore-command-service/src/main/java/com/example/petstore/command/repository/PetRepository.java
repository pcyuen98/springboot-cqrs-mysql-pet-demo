package com.example.petstore.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.command.entity.PetWriteEntity;

public interface PetRepository extends JpaRepository<PetWriteEntity, Long> {}
