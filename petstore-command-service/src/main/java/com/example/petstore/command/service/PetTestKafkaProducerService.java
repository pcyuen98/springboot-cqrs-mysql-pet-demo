package com.example.petstore.command.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.common.events.PetCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Refactored Service focusing on high-throughput event streaming.
 * Removed PetRepository to eliminate database I/O bottlenecks during stress tests.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PetTestKafkaProducerService {

	private final KafkaTemplate<String, PetCreatedEvent> kafkaTemplate;
	
	/**
	 * Publishes a Pet creation event to Kafka.
	 * * @param pet The entity containing pet details
	 * @return The original pet entity
	 */
	public PetWriteEntity createPet(PetWriteEntity pet) {
		PetCreatedEvent event = new PetCreatedEvent(
				pet.getPetId(), 
				pet.getName(), 
				pet.getCategory().getName(),
				pet.getStatus().name(), 
				pet.getPhotoUrl(), 
				pet.getDescription()
		);

		// KafkaTemplate.send is asynchronous by default, allowing for high-speed loops
		kafkaTemplate.send("pets", event);
		
		return pet;
	}

}