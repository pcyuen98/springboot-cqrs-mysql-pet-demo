package com.example.petstore.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.petstore.query.model.PetReadEntity;

public interface PetReadRepository extends MongoRepository<PetReadEntity, Long> {
}