package com.demo.keycloak.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.keycloak.model.entity.UserHistoryEntity;

@Repository
public interface IUserHistoryRepository extends JpaRepository<UserHistoryEntity, Long> {
}
