package com.demo.keycloak.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.keycloak.model.bean.FeedbackDTO;
import com.demo.keycloak.model.entity.FeedbackEntity;
import com.demo.keycloak.model.entity.mapper.FeedbackSMapper;
import com.demo.keycloak.model.repository.IFeedbackRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedbackService implements IService<FeedbackDTO, FeedbackEntity> {

	private final IFeedbackRepository repository;
	
	private final FeedbackSMapper feedbackSMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<FeedbackEntity> toList() {

		return repository.findAll();
	}

	@Override
	@Transactional
	public FeedbackDTO save(FeedbackDTO feedbackDTO) {
		return feedbackSMapper.toDto(repository.save(feedbackSMapper.toEntity(feedbackDTO)));
	}

	@Override
	@Transactional
	public void delete(FeedbackEntity entity) {
		repository.delete(entity);
	}
}
