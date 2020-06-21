package com.codegym.education.repository;

import com.codegym.education.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Page <Lesson> findByNameLesson(Pageable pageable, Optional<String> name);
}
