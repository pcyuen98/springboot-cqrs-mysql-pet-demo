package com.example.petstore.command.mapper;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.entity.TagEntity;
import com.example.petstore.command.model.Category;
import com.example.petstore.command.model.PetWrite;
import com.example.petstore.command.model.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-20T15:56:16+0800",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.15 (Eclipse Adoptium)"
)
*/
@Component
public class PetWriteMapperImpl implements PetWriteMapper {

    @Override
    public PetWrite toDto(PetWriteEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PetWrite petWrite = new PetWrite();

        petWrite.setStatus( entity.getStatus() );
        List<String> list = entity.getPhotoUrls();
        if ( list != null ) {
            petWrite.setPhotoUrls( new ArrayList<String>( list ) );
        }
        petWrite.setCategory( categoryEntityToCategory( entity.getCategory() ) );
        petWrite.setId( entity.getId() );
        petWrite.setName( entity.getName() );
        petWrite.setTags( tagEntityListToTagList( entity.getTags() ) );

        return petWrite;
    }

    @Override
    public PetWriteEntity toEntity(PetWrite dto) {
        if ( dto == null ) {
            return null;
        }

        PetWriteEntity petWriteEntity = new PetWriteEntity();

        petWriteEntity.setStatus( dto.getStatus() );
        List<String> list = dto.getPhotoUrls();
        if ( list != null ) {
            petWriteEntity.setPhotoUrls( new ArrayList<String>( list ) );
        }
        petWriteEntity.setCategory( categoryToCategoryEntity( dto.getCategory() ) );
        petWriteEntity.setId( dto.getId() );
        petWriteEntity.setName( dto.getName() );
        petWriteEntity.setTags( tagListToTagEntityList( dto.getTags() ) );

        return petWriteEntity;
    }

    @Override
    public List<PetWrite> toDtoList(List<PetWriteEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PetWrite> list = new ArrayList<PetWrite>( entityList.size() );
        for ( PetWriteEntity petWriteEntity : entityList ) {
            list.add( toDto( petWriteEntity ) );
        }

        return list;
    }

    @Override
    public List<PetWriteEntity> toEntityList(List<PetWrite> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PetWriteEntity> list = new ArrayList<PetWriteEntity>( dtoList.size() );
        for ( PetWrite petWrite : dtoList ) {
            list.add( toEntity( petWrite ) );
        }

        return list;
    }

    protected Category categoryEntityToCategory(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryEntity.getId() );
        category.setName( categoryEntity.getName() );

        return category;
    }

    protected Tag tagEntityToTag(TagEntity tagEntity) {
        if ( tagEntity == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setId( tagEntity.getId() );
        tag.setName( tagEntity.getName() );

        return tag;
    }

    protected List<Tag> tagEntityListToTagList(List<TagEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<Tag> list1 = new ArrayList<Tag>( list.size() );
        for ( TagEntity tagEntity : list ) {
            list1.add( tagEntityToTag( tagEntity ) );
        }

        return list1;
    }

    protected CategoryEntity categoryToCategoryEntity(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId( category.getId() );
        categoryEntity.setName( category.getName() );

        return categoryEntity;
    }

    protected TagEntity tagToTagEntity(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagEntity tagEntity = new TagEntity();

        tagEntity.setId( tag.getId() );
        tagEntity.setName( tag.getName() );

        return tagEntity;
    }

    protected List<TagEntity> tagListToTagEntityList(List<Tag> list) {
        if ( list == null ) {
            return null;
        }

        List<TagEntity> list1 = new ArrayList<TagEntity>( list.size() );
        for ( Tag tag : list ) {
            list1.add( tagToTagEntity( tag ) );
        }

        return list1;
    }
}
