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
        List<AppDoc> documents = listDocuments.getContent();
        List<AppDoc> topSixDoc = new ArrayList<>();
        topSixDoc.add(documents.get(0));
        topSixDoc.add(documents.get(1));
//        topSixDoc.add(documents.get(2));
//        topSixDoc.add(documents.get(3));
//        topSixDoc.add(documents.get(4));
//        topSixDoc.add(documents.get(5));

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("firstLessons", firstLesson);
        modelAndView.addObject("participant", userService.getCurrentUser());
        modelAndView.addObject("topSixLession", topSixLession);
        modelAndView.addObject("topSixDocs", topSixDoc);
        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }


    @GetMapping("/findAll")
    public ModelAndView findAll(@PageableDefault(size = 9) Pageable pageable,
                                @RequestParam("keyword") Optional<String> keyword){
        Page<Lesson> listLessons = lessonService.findByNameLesson(pageable,keyword);
        Page<AppDoc> listDocuments = documentService.findByNameDocument(pageable,keyword);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listLessons",listLessons.getContent());
        modelAndView.addObject("listDocuments",listDocuments.getContent());
        return modelAndView;
    }
}

