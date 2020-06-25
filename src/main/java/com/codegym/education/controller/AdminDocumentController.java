package com.codegym.education.controller;

import com.codegym.education.model.AppDoc;
import com.codegym.education.service.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin/document")
public class AdminDocumentController {

    @Autowired
    private DocumentService documentService;



    @GetMapping("/createdocument")
    public ModelAndView showFormCreatDocument() {
        ModelAndView modelAndView = new ModelAndView("dashboard/createdocumentDashboard");
        modelAndView.addObject("document", new AppDoc());
        return modelAndView;
    }

    @PostMapping("/savedocument")
    public ModelAndView saveDocument(@ModelAttribute("document") AppDoc appDoc) {
        appDoc.setDate(LocalDateTime.now());
        documentService.save(appDoc);
        return new ModelAndView("redirect:/admin/showAllDocument");
    }

    @GetMapping("/editdocument/{id}")
    public ModelAndView showFormEditDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("dashboard/editdocumentDashboard");
        Optional<AppDoc> appDoc = documentService.findById(id);
        modelAndView.addObject("appDoc", appDoc);
        return modelAndView;
    }

    @GetMapping("/deletedocument/{id}")
    public ModelAndView showFormDeleteDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("dashboard/deleteDocumentDashboard");
        Optional<AppDoc> appDoc = documentService.findById(id);
        modelAndView.addObject("appDoc", appDoc.get());
        return modelAndView;
    }

    @PostMapping("/deletedocument")
    public ModelAndView deleteDocument(@ModelAttribute("appDoc") AppDoc appDoc) {
        documentService.delete(appDoc.getId());
        return new ModelAndView("redirect:/admin/showAllDocument");
    }

}
