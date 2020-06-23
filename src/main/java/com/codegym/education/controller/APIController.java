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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class APIController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;


    @GetMapping("/findDocument")
    public ResponseEntity<Page<AppDoc>> findDocumentsByType(@PageableDefault(9) Pageable pageable, @PathVariable("find") String type) {
        Page<AppDoc> listDocuments = documentService.findByTypeDocument(pageable, type);
        return new ResponseEntity<Page<AppDoc>>(listDocuments, HttpStatus.OK);
    }

    @GetMapping("/findLesson/{number}")
    public ResponseEntity<Page<Lesson>> findLessonsByType(@PageableDefault(9) Pageable pageable,
                                                          @PathVariable("number") String number) {
        int numbers = Integer.parseInt(number);
        Page<Lesson> listLessons;
        switch (numbers) {
            case 1:
                listLessons = lessonService.findByTypeLesson(pageable, "java");
                break;
            case 2:
                listLessons= lessonService.findByTypeLesson(pageable, "php");
                break;
            case 3:
                listLessons= lessonService.findByTypeLesson(pageable, "javascript");
                break;

            default:
                return null;
        }

        return new ResponseEntity<Page<Lesson>>(listLessons, HttpStatus.OK);
    }


}
