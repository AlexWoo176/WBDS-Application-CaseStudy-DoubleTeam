package com.codegym.education.controller;

import com.codegym.education.model.AppDoc;
import com.codegym.education.service.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/showDocument/{id}")
    public ModelAndView showDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("showDocument");
        Optional<AppDoc> document = documentService.findById(id);
        return modelAndView;
    }

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

}
