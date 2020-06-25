package com.codegym.education.controller;

import com.codegym.education.model.AppDoc;
import com.codegym.education.model.Lesson;
import com.codegym.education.service.document.DocumentService;
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

import java.util.Optional;
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/showDocument/{id}")
    public ModelAndView showDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detailDocument");
        Optional<AppDoc> document = documentService.findById(id);
        modelAndView.addObject("documents", document.get());
        return modelAndView;
    }

    @GetMapping("/showAllDocument")
    public ModelAndView showAllDocument(@PageableDefault(size = 6) Pageable pageable,
                                        @RequestParam("keyword") Optional<String> keyword) {
        Page<AppDoc> listDocuments;
        ModelAndView modelAndView = new ModelAndView("document");

        modelAndView.addObject("listJavaDocument",documentService.findByTypeDocument("java"));
        modelAndView.addObject("listPhpDocument",documentService.findByTypeDocument("php"));
        if (keyword.isPresent()) {
            listDocuments = documentService.findByNameDocument(pageable, keyword);
            modelAndView.addObject("keyword", keyword.map(Object::toString).orElse(null));
        } else {
            listDocuments = documentService.sortByDate(pageable);
            modelAndView.addObject("keyword", "");
        }

        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }

}
