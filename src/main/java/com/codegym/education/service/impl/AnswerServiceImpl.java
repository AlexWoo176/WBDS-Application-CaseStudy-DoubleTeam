package com.codegym.education.service.impl;


import com.codegym.education.model.Answer;
import com.codegym.education.repository.AnswerRepository;
import com.codegym.education.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl implements IAnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Page<Answer> findAllPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 1);
        return answerRepository.findAll(pageable);
    }

    @Override
    public Iterable<Answer> findAll() {
        Iterable<Answer> answers = answerRepository.findAll();
        return answers;
    }

    @Override
    public Answer findById(Long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        Answer answer;
        if (optionalAnswer.isPresent()) {
            answer = optionalAnswer.get();
        } else {
            answer = null;
        }
        return answer;
    }
}
