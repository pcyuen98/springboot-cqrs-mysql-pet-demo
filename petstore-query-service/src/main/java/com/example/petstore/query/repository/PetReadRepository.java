package com.example.petstore.query.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petstore.query.model.PetReadEntity;

public interface PetReadRepository extends MongoRepository<PetReadEntity, Long> {
	List<PetReadEntity> findByStatus(String status);
    
    @Query("{ " +
            "'status': { $regex: ?0, $options: 'i' }, " +
            "'data':   { $regex: ?1, $options: 'i' } " +
           "}")
    List<PetReadEntity> search(String statusRegex, String dataRegex);
    

}