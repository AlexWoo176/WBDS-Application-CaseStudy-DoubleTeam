package com.codegym.education.controller;

import com.codegym.education.model.AppDoc;
import com.codegym.education.model.Lesson;
import com.codegym.education.model.Participant;
import com.codegym.education.service.UserService;
import com.codegym.education.service.document.DocumentService;
import com.codegym.education.service.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;


    //lesson
    @GetMapping("/showAllLesson")
    public ModelAndView showAllLesson(@PageableDefault(size = 10) Pageable pageable,
                                      @RequestParam("keyword") Optional<String> keyword, HttpSession session) {
        Page<Lesson> listLesson;
        ModelAndView modelAndView = new ModelAndView("dashboard/LessonDashboard");
        if (keyword.isPresent()) {
            listLesson = lessonService.findByNameLesson(pageable, keyword);
            modelAndView.addObject("keyword", keyword.map(Object::toString).orElse(null));
        } else {
            listLesson = lessonService.sortByDate(pageable);
            modelAndView.addObject("keyword", "");
        }
        Participant participant = (Participant)session.getAttribute("participant");
        modelAndView.addObject("participant", participant);
        modelAndView.addObject("listLessons", listLesson);
        return modelAndView;
    }


    //Document
    @GetMapping("/showAllDocument")
    public ModelAndView showAllDocument(@PageableDefault(size = 10) Pageable pageable,
                                        @RequestParam("keyword") Optional<String> keyword, HttpSession session) {
        Page<AppDoc> listDocuments;
        ModelAndView modelAndView = new ModelAndView("dashboard/documentDashboard");
        if (keyword.isPresent()) {
            listDocuments = documentService.findByNameDocument(pageable, keyword);
            modelAndView.addObject("keyword", keyword.map(Object::toString).orElse(null));
        } else {
            listDocuments = documentService.sortByDate(pageable);
            modelAndView.addObject("keyword", "");

        }
        Participant participant = (Participant)session.getAttribute("participant");
        modelAndView.addObject("participant", participant);
        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }




}
