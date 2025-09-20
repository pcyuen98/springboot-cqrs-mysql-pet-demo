package com.example.petstore.query.handler;

import com.example.petstore.common.events.PetCreatedEvent;
import com.example.petstore.query.model.Pet;
import com.example.petstore.query.repository.PetRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PetEventHandler {
    private final PetRepository repository;

    public PetEventHandler(PetRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "pets", groupId = "query-service")
    public void handlePetCreated(PetCreatedEvent event) {
        Pet pet = new Pet();
        pet.setId(event.getPetId());
        pet.setName(event.getName());
        pet.setType(event.getType());
        pet.setStatus(event.getStatus());
        repository.save(pet);
    }
}
