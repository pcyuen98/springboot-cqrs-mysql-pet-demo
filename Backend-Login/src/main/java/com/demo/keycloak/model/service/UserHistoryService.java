package com.demo.keycloak.model.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.keycloak.model.bean.UserHistoryDTO;
import com.demo.keycloak.model.entity.UserHistoryEntity;
import com.demo.keycloak.model.entity.mapper.UserHistorySMapper;
import com.demo.keycloak.model.repository.IUserHistoryRepository;
import com.demo.keycloak.model.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserHistoryService implements IService<UserHistoryDTO, UserHistoryEntity> {

	private final IUserHistoryRepository repository;
	private final IUserRepository iUserRepository;
	private final UserHistorySMapper userHistorySMapper;

	@Override
	@Transactional(readOnly = true)
	public List<UserHistoryEntity> toList() {

		return repository.findAll();
	}

	@Override
	@Transactional
	public UserHistoryDTO save(UserHistoryDTO userHistoryDTO) {
		UserHistoryEntity userHistoryEntity = userHistorySMapper.toEntity(userHistoryDTO);
		userHistoryEntity.setLoginDate(LocalDateTime.now());
		userHistoryEntity.getUsersEntity().setLastUpdatedDate(userHistoryEntity.getLoginDate());
		userHistoryEntity = repository.save(userHistoryEntity);
		iUserRepository.save(userHistoryEntity.getUsersEntity());
		return userHistorySMapper.toDto(userHistoryEntity);
	}


	@Override
	@Transactional
	public void delete(UserHistoryEntity entity) {
		repository.delete(entity);
	}

}
