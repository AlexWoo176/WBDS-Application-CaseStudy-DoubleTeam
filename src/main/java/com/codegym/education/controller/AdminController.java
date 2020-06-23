package com.codegym.education.controller;

import com.codegym.education.model.AppDoc;
import com.codegym.education.model.Lesson;
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


    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("admin","participant", userService.getCurrentUser());
    }

    // vao trang tong bai viet + tim kiem
    //lesson
    @GetMapping("/showAllLesson")
    public ModelAndView showAllLesson(@PageableDefault(size = 10) Pageable pageable,
                                      @RequestParam("keyword") Optional<String> keyword) {
        Page<Lesson> listLesson;
        if (keyword.isPresent()) {
            listLesson = lessonService.findByNameLesson(pageable, keyword);
        } else {
            listLesson = lessonService.sortByDate(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("alllesson");
        modelAndView.addObject("listLessons", listLesson);
        return modelAndView;
    }

    @GetMapping("/creatlesson")
    public ModelAndView showFormCreatLesson() {
        ModelAndView modelAndView = new ModelAndView();
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
        ModelAndView modelAndView = new ModelAndView();
        Optional<Lesson> lesson = lessonService.findById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }

    @GetMapping("/deletelesson/{id}")
    public ModelAndView showFormDeletelesson(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Lesson> lesson = lessonService.findById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }

    @PostMapping("/deletelesson")
    public ModelAndView deleteLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.delete(lesson.getId());
        return new ModelAndView("redirect:/admin/showAllLesson");
    }

    //Document
    @GetMapping("/showAllDocument")
    public ModelAndView showAllDocument(@PageableDefault(size = 10) Pageable pageable,
                                        @RequestParam("keyword") Optional<String> keyword) {
        Page<AppDoc> listDocuments;
        if (keyword.isPresent()) {
            listDocuments = documentService.findByNameDocument(pageable, keyword);
        } else {
            listDocuments = documentService.sortByDate(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("allDocument");
        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }


    @GetMapping("/creatdocument")
    public ModelAndView showFormCreatDocument() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("document", new AppDoc());
        return modelAndView;
    }

    @PostMapping("/savedocument")
    public ModelAndView saveDocument(@ModelAttribute("document") AppDoc appDoc) {
        documentService.save(appDoc);
        return new ModelAndView("redirect:/admin/showAllDocument");
    }

    @GetMapping("/editdocument/{id}")
    public ModelAndView showFormEditDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<AppDoc> appDoc = documentService.findById(id);
        modelAndView.addObject("appDoc", appDoc);
        return modelAndView;
    }

    @GetMapping("/deletedocument/{id}")
    public ModelAndView showFormDeleteDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<AppDoc> appDoc = documentService.findById(id);
        modelAndView.addObject("appDoc", appDoc);
        return modelAndView;
    }

    @PostMapping("/deletedocument")
    public ModelAndView deleteDocument(@ModelAttribute("appDoc") AppDoc appDoc) {
        documentService.delete(appDoc.getId());
        return new ModelAndView("redirect:/admin/showAllDocument");
    }


}
