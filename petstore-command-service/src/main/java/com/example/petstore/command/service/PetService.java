package com.example.petstore.command.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.repository.PetRepository;
import com.example.petstore.common.events.PetCreatedEvent;
import com.example.petstore.common.exception.DemoAppException;
import com.example.petstore.common.model.Status;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PetService {
	private final PetRepository repository;
	private final KafkaTemplate<String, PetCreatedEvent> kafkaTemplate;

	public PetService(PetRepository repository, KafkaTemplate<String, PetCreatedEvent> kafkaTemplate) {
		this.repository = repository;
		this.kafkaTemplate = kafkaTemplate;
	}

	@Transactional
	public PetWriteEntity createPet(PetWriteEntity pet) {
		PetWriteEntity saved = repository.save(pet);
		PetCreatedEvent event = new PetCreatedEvent(saved.getPetId(), saved.getName(), saved.getCategory().getName(),

				saved.getStatus().name(), saved.getPhotoUrl(), saved.getDescription());
		kafkaTemplate.send("pets", event);
		return saved;
	}

	@Transactional
	public void deletePet(Long id) {
		PetWriteEntity found = repository.findById(id)
				.orElseThrow(() -> new DemoAppException("Pet not found with id: " + id));

		PetCreatedEvent event = new PetCreatedEvent(found.getPetId(), found.getName(), found.getCategory().getName(),
				Status.DELETE.name(), found.getPhotoUrl(), found.getDescription());
		kafkaTemplate.send("pets", event);
		repository.deleteById(id);
		log.info("id deleted successfully #{}", id);
	}
}
