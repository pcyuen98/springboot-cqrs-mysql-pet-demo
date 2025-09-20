package com.example.petstore.command.repository;

import com.example.petstore.command.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {}
