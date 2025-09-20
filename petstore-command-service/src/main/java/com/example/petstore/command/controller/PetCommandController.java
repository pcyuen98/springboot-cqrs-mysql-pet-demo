package com.example.petstore.command.controller;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pets/v1")
@Slf4j
public class PetCommandController {
	private final PetService service;

	@PostMapping
	public ResponseEntity<PetWriteEntity> createPet(@RequestBody PetWriteEntity pet) {
		PetWriteEntity created = service.createPet(pet);
		return ResponseEntity.ok(created);
	}
}
