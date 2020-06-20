package com.codegym.education.service;

import java.util.Optional;

public interface GeneralService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    void save(T model);

    void remove(Long id);
}
