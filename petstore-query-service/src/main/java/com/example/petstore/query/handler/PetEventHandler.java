package com.example.petstore.query.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.petstore.common.events.PetCreatedEvent;
import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.repository.PetReadRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PetEventHandler {
  
	private final PetReadRepository petReadRepository;
	
    @KafkaListener(topics = "pets", groupId = "query-service")
    public void handlePetCreated(PetCreatedEvent event) {
    	log.info("Incoming Kafka Data = {}" , event.getPetId());
    	PetReadEntity pet = new PetReadEntity();
    	pet.setId(event.getPetId());
    	pet.setData(event.getName());
    	petReadRepository.save(pet);
    }
}
