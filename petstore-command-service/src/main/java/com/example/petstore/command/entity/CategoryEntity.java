package com.example.petstore.command.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

// Table not used at the moment, dummy value only
@Entity
@Data
@Table(name = "categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
	private String name;
}
