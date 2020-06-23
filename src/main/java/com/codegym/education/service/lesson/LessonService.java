package com.codegym.education.service.lesson;

import com.codegym.education.model.Lesson;
import com.codegym.education.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LessonService implements ILessonService{
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public Page<Lesson> findAll(Pageable pageable) {
        return lessonRepository.findAll(pageable) ;
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public void save(Lesson model) {
        lessonRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    public Page<Lesson> findByNameLesson(Pageable pageable, Optional<String> name){
        return lessonRepository.findByNameLessonContaining(pageable,name);
    }
    public Page<Lesson> sortByDate(Pageable pageable){
        return lessonRepository.findAllByOrderByDateDesc(pageable);
    }
    public Page<Lesson> findByTypeLesson(Pageable pageable,String type){
        return lessonRepository.findByTypeLesson(pageable,type);
    }
}
