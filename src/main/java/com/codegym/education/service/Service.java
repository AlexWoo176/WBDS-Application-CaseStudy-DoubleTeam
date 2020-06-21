package com.codegym.education.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Service <T>{
    Page<T> findAll(Pageable pageable);
    Optional <T> findById(Long id);
    void save(T model);
    void delete(Long id);
}
