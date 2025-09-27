package com.demo.keycloak.repo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.UserHistoryEntity;
import com.demo.keycloak.model.repository.IUserHistoryRepository;
import com.demo.keycloak.model.repository.IUserRepository;
@SpringBootTest
@ActiveProfiles("test") // Uses application-test.properties for H2 setup

class UserHistoryRepositoryTest {

    @Autowired
    private IUserHistoryRepository iUserHistoryRepository;

    @Autowired
    private IUserRepository iUserRepository;
    
    UserHistoryEntity saveUserHistory() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setName("last name");
        userEntity.setSurname("surname");
        userEntity.setUsername("username");
        userEntity = iUserRepository.save(userEntity);
        // Act
        UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
        userHistoryEntity.setUsersEntity(userEntity);
        userHistoryEntity = iUserHistoryRepository.save(userHistoryEntity);

        // Assert
        assertNotNull(userHistoryEntity);
        return userHistoryEntity;
    }

    @Test
    void cascadeMergeTest() {
    	
    	UserHistoryEntity userHistoryEntity = saveUserHistory();
    	userHistoryEntity.getUsersEntity().setDescription("Updated Description");
    	userHistoryEntity.getUsersEntity().setLastUpdatedDate(userHistoryEntity.getLoginDate());
    	userHistoryEntity = this.iUserHistoryRepository.save(userHistoryEntity);
    	
    	Optional<UserHistoryEntity> userHistoryEntityOp =  iUserHistoryRepository.findById(userHistoryEntity.getId());
    	userHistoryEntity = userHistoryEntityOp.get();
    	assertNotNull(userHistoryEntity.getUsersEntity().getDescription());
    	assertNotNull(userHistoryEntity.getUsersEntity().getLastUpdatedDate());
        
    }
}
