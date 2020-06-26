package com.codegym.education.controller;

import com.codegym.education.model.Lesson;
import com.codegym.education.model.Participant;
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

import javax.servlet.http.HttpSession;
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
                                      @RequestParam("keyword") Optional<String> keyword, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("lesson");
        List<Lesson> listJavaLessons = lessonService.findByTypeLesson("java");
        List<Lesson> listPhpLessons = lessonService.findByTypeLesson("php");

        Page<Lesson> listLesson;
        if (keyword.isPresent()) {
            listLesson = lessonService.findByNameLesson(pageable, keyword);
            modelAndView.addObject("keyword", keyword.map(Object::toString).orElse(null));
        } else {
            listLesson = lessonService.sortByDate(pageable);
            modelAndView.addObject("keyword", "");
        }
        Participant participant = (Participant)session.getAttribute("participant");
        modelAndView.addObject("participant", participant);
        modelAndView.addObject("listJavaLessons",listJavaLessons);
        modelAndView.addObject("listPhpLessons",listPhpLessons);
        modelAndView.addObject("listLessons", listLesson);
        return modelAndView;
    }

    //xem chi tiet 1 phan
    @GetMapping("/showlesson/{id}")
    public ModelAndView showLesson(@PathVariable("id") Long id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("detailLesson");
        Optional<Lesson> lessonOptional = lessonService.findById(id);
        Lesson lesion = lessonOptional.get();
        Participant participant = (Participant)session.getAttribute("participant");
        modelAndView.addObject("participant", participant);
        modelAndView.addObject("lesson", lesion);
        return modelAndView;
    }
}
