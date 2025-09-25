package com.example.petstore.query.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.petstore.common.events.PetCreatedEvent;
import com.example.petstore.common.exception.DemoAppException;
import com.example.petstore.common.model.Status;
import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.repository.PetReadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PetEventHandler {
  
	private final PetReadRepository petReadRepository;
	
	private final ObjectMapper objectMapper;
	
	@KafkaListener(topics = "pets", groupId = "query-service")
	public void handlePetCreated(String message) {
	    try {
	        
	        log.info("Incoming Kafka Event JSON = {}", message);
	        PetCreatedEvent event = objectMapper.readValue(message, PetCreatedEvent.class);
	        PetReadEntity pet = new PetReadEntity();
	        pet.setId(event.getPetId());
	        pet.setStatus(event.getStatus());
	        pet.setData(message);
	        
	        if (event.getStatus().equals(Status.DELETE.name()) ) {
	        	petReadRepository.delete(pet);
	        }
	        else {
	        	petReadRepository.save(pet);
	        }
	        

	    } catch (Exception e) {
	    	throw new DemoAppException("Error processing Kafka event", e);
	    }
	}
}
