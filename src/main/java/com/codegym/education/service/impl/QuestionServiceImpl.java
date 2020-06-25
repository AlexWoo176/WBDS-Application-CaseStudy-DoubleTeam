package com.codegym.education.service.impl;


import com.codegym.education.model.Question;
import com.codegym.education.repository.QuestionRepository;
import com.codegym.education.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Page<Question> findAllPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 1);
        return questionRepository.findAll(pageable);
    }

    @Override
    public Iterable<Question> findAll() {
        Iterable<Question> questions = questionRepository.findAll();
        return questions;
    }

    @Override
    public Question findById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        Question question;
        if (optionalQuestion.isPresent()) {
            question = optionalQuestion.get();
        } else {
            question = null;
        }
        return question;
    }
}
