package com.example.petstore.command.service;

import com.example.petstore.command.model.Pet;
import com.example.petstore.command.repository.PetRepository;
import com.example.petstore.common.events.PetCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService {
    private final PetRepository repository;
    private final KafkaTemplate<String, PetCreatedEvent> kafkaTemplate;

    public PetService(PetRepository repository, KafkaTemplate<String, PetCreatedEvent> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Pet createPet(Pet pet) {
        Pet saved = repository.save(pet);
        PetCreatedEvent event = new PetCreatedEvent(saved.getId(), saved.getName(), saved.getType(), saved.getStatus());
        kafkaTemplate.send("pets", event);
        return saved;
    }
}
