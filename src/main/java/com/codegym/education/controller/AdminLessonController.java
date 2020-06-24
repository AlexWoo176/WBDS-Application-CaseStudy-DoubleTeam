package com.codegym.education.controller;

import com.codegym.education.model.Lesson;
import com.codegym.education.service.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin/lesson")
public class AdminLessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/creatlesson")
    public ModelAndView showFormCreatLesson() {
        ModelAndView modelAndView = new ModelAndView("dashboard/createLessonDashboard");
        modelAndView.addObject("lesson", new Lesson());
        return modelAndView;
    }

    @PostMapping("/savelesson")
    public ModelAndView saveLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.save(lesson);
        return new ModelAndView("redirect:/admin/showAllLesson");
    }

    @GetMapping("/editlesson/{id}")
    public ModelAndView showFormEditLesson(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("dashboard/editLessonDashboard");
        Optional<Lesson> lesson = lessonService.findById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }


    @GetMapping("/deletelesson/{id}")
    public ModelAndView showFormDeletelesson(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("dashboard/deleteLessonDashboard");
        Optional<Lesson> lesson = lessonService.findById(id);

        modelAndView.addObject("lesson", lesson.get());
        return modelAndView;
    }

    @PostMapping("/deletelesson")
    public ModelAndView deleteLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.delete(lesson.getId());
        return new ModelAndView("redirect:/admin/showAllLesson");
    }

}
