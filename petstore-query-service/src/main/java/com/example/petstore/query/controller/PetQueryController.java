package com.example.petstore.query.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.service.PetQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets/read/v1")
@Slf4j
public class PetQueryController {

    private final PetQueryService petQueryService;

    @GetMapping
    public List<PetReadEntity> getAllPets() {
        log.info("Fetching all pets");
        return petQueryService.findAllPets();
    }
}
