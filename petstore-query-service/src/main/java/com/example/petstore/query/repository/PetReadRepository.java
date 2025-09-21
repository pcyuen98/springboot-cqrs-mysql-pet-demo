package com.example.petstore.query.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.petstore.query.model.PetReadEntity;

public interface PetReadRepository extends MongoRepository<PetReadEntity, Long> {
	List<PetReadEntity> findByStatus(String status);
}