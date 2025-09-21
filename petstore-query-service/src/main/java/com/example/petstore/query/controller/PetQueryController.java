package com.example.petstore.query.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.service.PetQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pet/all")
@Slf4j
public class PetQueryController {

    private final PetQueryService petQueryService;

    @GetMapping
    public List<PetReadEntity> getAllPets() {
        log.info("Fetching all pets");
        return petQueryService.findAllPets();
    }
    
    // ✅ Get pet by ID
    @GetMapping("/{id}")
    public Optional<PetReadEntity> getPetById(@PathVariable Long id) {
        log.info("Fetching pet by id: {}", id);
        return petQueryService.findPetById(id);
    }

    // ✅ Get pets by status
    @GetMapping("/findByStatus/{status}")
    public List<PetReadEntity> getPetsByStatus(@PathVariable String status) {
        log.info("Fetching pets by status: {}", status);
        return petQueryService.findPetsByStatus(status.toUpperCase()); // normalize input
    }
}
