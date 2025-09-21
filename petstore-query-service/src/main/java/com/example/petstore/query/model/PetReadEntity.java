package com.example.petstore.query.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "pet_data")
public class PetReadEntity {

    @Id
    private Long id;
    
	private String data;
}
