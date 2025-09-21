package com.demo.keycloak.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IService<T,E> {

    List<E> toList();

    T save(T dto);
    
    void delete(E entity);

}
