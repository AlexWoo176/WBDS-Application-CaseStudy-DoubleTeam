package com.codegym.education.controller;

import com.codegym.education.model.Lesson;
import com.codegym.education.service.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    // vao trang tong bai viet + tim kiem

    @GetMapping("/showAllLesson")
    public ModelAndView showAllLesson(@PageableDefault(size = 9) Pageable pageable,
                                      @RequestParam("keyword") Optional<String> keyword) {

        Page<Lesson> listLesson;
        if (keyword.isPresent()) {
            listLesson = lessonService.findByNameLesson(pageable, keyword);
        } else {
            listLesson = lessonService.sortByDate(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("lesson");
        modelAndView.addObject("listLessons", listLesson);
        return modelAndView;
    }

    //xem chi tiet 1 phan
    @GetMapping("/showlesson/{id}")
    public ModelAndView showLesson(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("showlesson");
        Optional<Lesson> lesson = lessonService.findById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }
}
