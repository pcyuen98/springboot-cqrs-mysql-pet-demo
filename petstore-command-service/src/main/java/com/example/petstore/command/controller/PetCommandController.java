package com.example.petstore.command.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.service.PetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pet")
@Slf4j
public class PetCommandController {
	private final PetService service;

	@PostMapping
	public ResponseEntity<PetWriteEntity> createPet(@RequestBody PetWriteEntity pet) {
		PetWriteEntity created = service.createPet(pet);
		return ResponseEntity.ok(created);
	}
	
	@PutMapping
	public ResponseEntity<PetWriteEntity> updatePet(@RequestBody PetWriteEntity pet) {
		PetWriteEntity updated = service.createPet(pet);
		return ResponseEntity.ok(updated);
	}
}
