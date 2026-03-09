package com.example.petstore.command.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.petstore.common.model.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pets")
public class PetWriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pet_category"))
    private CategoryEntity category;

    @Column(nullable = false)
    private String name;

    @Column(name = "photo_url", nullable = false, length = 500)
    private String photoUrl;

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

    @Column(
    	    name = "created_at", 
    	    nullable = false, 
    	    updatable = false, 
    	    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    	)
    	private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}