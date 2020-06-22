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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView home(@PageableDefault(size = 6) Pageable pageable) {
        Page<Lesson> listLessons = lessonService.sortByDate(pageable);
        List<Lesson> lessions = listLessons.getContent();
        Lesson firstLesson = lessions.get(0);
        List<Lesson> topSixLession = new ArrayList<>();
        topSixLession.add(lessions.get(0));
        topSixLession.add(lessions.get(1));
//        topSixLession.add(lessions.get(2));
//        topSixLession.add(lessions.get(3));
//        topSixLession.add(lessions.get(4));
//        topSixLession.add(lessions.get(5));
        Page<AppDoc> listDocuments = documentService.sortByDate(pageable);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("firstLessons", firstLesson);
        modelAndView.addObject("participant", userService.getCurrentUser());
        modelAndView.addObject("topSixLession", topSixLession);
        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }

    @GetMapping("/lesson")
    public ModelAndView lesson (){
        return new ModelAndView("lesson");
    }

    @GetMapping("/about")
    public ModelAndView about (){
        return new ModelAndView("about");
    }

    //xem chi tiet 1 phan
    @GetMapping("/showlesson/{id}")
    public ModelAndView showLesson(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("showlesson");
        Optional<Lesson> lesson = lessonService.findById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }

    @GetMapping("/showDocument/{id}")
    public ModelAndView showDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("showDocument");
        Optional<AppDoc> document = documentService.findById(id);
        return modelAndView;
    }
    // vao trang tong bai viet + tim kiem

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

    @GetMapping("/showAllDocument")
    public ModelAndView showAllDocument(@PageableDefault(size = 10) Pageable pageable,
                                        @RequestParam("keyword") Optional<String> keyword) {
        Page <AppDoc> listDocuments;
        if(keyword.isPresent()){
            listDocuments = documentService.findByNameDocument(pageable,keyword);
        }else {
            listDocuments = documentService.sortByDate(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("allDocument");
        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }

}

