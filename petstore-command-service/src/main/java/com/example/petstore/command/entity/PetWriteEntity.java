package com.example.petstore.command.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.example.petstore.common.model.Status;

@Entity
@Data
@Table(name = "pets")
public class PetWriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(nullable = false)
    private String name;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @ManyToMany
    @JoinTable(
        name = "pet_tags",
        joinColumns = @JoinColumn(name = "pet_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags;  

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;   
}
