package com.codegym.education.repository;


import com.codegym.education.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Question, Long> {
}
