package com.example.petstore.command.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.repository.PetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetInsertService {

    private final PetRepository petRepository;

    /**
     * Insert a single Pet entity inside its own transaction.
     * This method is thread-safe when called from multiple threads
     * because each call gets its own transactional context.
     */
    @Transactional
    public void insertPet(PetWriteEntity pet) {
        petRepository.save(pet);
    }
}