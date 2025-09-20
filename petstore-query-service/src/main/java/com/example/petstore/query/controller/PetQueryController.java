package com.example.petstore.query.controller;

import com.example.petstore.query.model.Pet;
import com.example.petstore.query.repository.PetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetQueryController {
    private final PetRepository repository;

    public PetQueryController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pet> listPets() {
        return repository.findAll();
    }
}
