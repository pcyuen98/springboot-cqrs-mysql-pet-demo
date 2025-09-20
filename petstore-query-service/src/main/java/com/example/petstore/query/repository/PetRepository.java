package com.example.petstore.query.repository;

import com.example.petstore.query.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {}
