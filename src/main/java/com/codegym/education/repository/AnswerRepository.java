package com.codegym.education.repository;

import com.codegym.education.model.Answer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion_id(Long question_id);
}
