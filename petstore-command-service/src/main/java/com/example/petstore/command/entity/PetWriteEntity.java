package com.example.petstore.command.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.example.petstore.common.model.Status;

@Data
@Entity
@Table(name = "pets")
public class PetWriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    // FIX: Removed CascadeType.PERSIST. When saving a new Pet, 
    // if the Category has an ID, we only want to MERGE/link it, not PERSIST it as new.
    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pet_category"))
    private CategoryEntity category;

    @Column(nullable = false)
    private String name;

    @Column(name = "photo_url", nullable = false, length = 500)
    private String photoUrl;

    // FIX: Removed CascadeType.PERSIST for the same reason as Category.
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "pet_tags",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(length = 2000, nullable = false)
    private String description;
}
