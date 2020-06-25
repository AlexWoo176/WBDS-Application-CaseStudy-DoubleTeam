package com.codegym.education.service.impl;


import com.codegym.education.model.Question;
import com.codegym.education.repository.ResultRepository;
import com.codegym.education.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements IResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public Page<Question> findAllPage(int pageNumber) {
        return null;
    }

    @Override
    public Iterable<Question> findAll() {
        Iterable<Question> questions = resultRepository.findAll();
        return questions;
    }

    @Override
    public Question findById(Long id) {
        return null;
    }
}
