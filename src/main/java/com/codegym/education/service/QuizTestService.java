package com.codegym.education.service;

import org.springframework.data.domain.Page;

public interface QuizTestService<T> {

    Iterable<T> findAll();

    Page<T> findAllPage(int pageNumber);

    T findById(Long id);
}
