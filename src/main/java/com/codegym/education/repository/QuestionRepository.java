package com.codegym.education.repository;


import com.codegym.education.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

    Page<Question> findAll(Pageable pageable);
}
