package com.codegym.education.repository;

import com.codegym.education.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Page <Lesson> findByNameLessonContaining(Pageable pageable, Optional<String> name);
    Page <Lesson> findAllByOrderByDateDesc(Pageable pageable);
    List <Lesson> findByTypeLesson(String type);
}
