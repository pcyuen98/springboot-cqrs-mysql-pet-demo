package com.demo.keycloak.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.keycloak.exceptions.DemoAppException;
import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.bean.UserHistoryDTO;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.mapper.UserSMapper;
import com.demo.keycloak.model.repository.IUserRepository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements IService<UserDTO, UserEntity> {

	private final IUserRepository repository;
	private final UserSMapper userSMapper;
	private final UserHistoryService userHistoryService;

	@Override
	@Transactional(readOnly = true)
	public List<UserEntity> toList() {

		return repository.findAll();
	}

	@Override
	public UserDTO save(UserDTO user) {

		return userSMapper.toDto(repository.save(userSMapper.toEntity(user)));
	}

	public UserDTO findByUsername(String username) {
		Optional<UserEntity> userApp = repository.findByUsername(username);
		if (userApp.isPresent()) {
			return userSMapper.toDto(userApp.get());

		} else
			return null;
	}

	@Override
	@Transactional
	public void delete(UserEntity entity) {
		repository.delete(entity);
	}

	@Transactional(rollbackFor = PersistenceException.class, isolation = Isolation.SERIALIZABLE)
	public UserDTO updateUser(UserDTO user) {
		if (user == null || user.getUsername() == null) {
			throw new DemoAppException("Invalid user object");
		}

		UserDTO existingUser = findByUsername(user.getUsername());
		return updateUserLogin(user, existingUser);
	}
	
	public UserDTO updateUserLogin(UserDTO user, UserDTO existingUser) {

		// Save new user if not found
		if (existingUser == null) {
			existingUser = save(user);
		}

		// Log user update in history
		UserHistoryDTO historyDTO = new UserHistoryDTO();
		historyDTO.setUserDTO(existingUser);

		historyDTO = userHistoryService.save(historyDTO);
		existingUser = historyDTO.getUserDTO();

		return existingUser;
	}

}
