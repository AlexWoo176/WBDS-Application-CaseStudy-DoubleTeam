package com.codegym.education.controller;


import com.codegym.education.model.Answer;
import com.codegym.education.model.Question;
import com.codegym.education.service.IAnswerService;
import com.codegym.education.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@SessionAttributes("totalChecked")
public class QuestionController {

    @ModelAttribute("totalChecked")
    public static List<String> getTotalQuestion() {
        radioCheckedList = new ArrayList<>();
        return radioCheckedList;
    }

    static Map<Long, Long> resultMap = new HashMap<Long, Long>();

    private static List<String> radioCheckedList;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerService answerService;

    @GetMapping(value="/quiz-page")
    public ModelAndView quizz(@ModelAttribute("totalChecked") List<Long> totalChecked, HttpServletRequest request) {
        radioCheckedList = new ArrayList<>();
        HttpSession session = request.getSession();
        session.setAttribute("radioCheckedList", radioCheckedList);
        return listByPage(totalChecked, 1, request);
    }

    @PostMapping("/page/{pageNumber}")
    public ModelAndView listByPage(@ModelAttribute("totalChecked") List<Long> totalChecked, @PathVariable("pageNumber") int currentPage, HttpServletRequest request) {
        Long questionId = null, answerId = null;
        if (currentPage != 1) {
            questionId = new Long(currentPage - 1);
            String answerIdStr = request.getParameter("question_" + questionId);
            if (answerIdStr != null) {
                answerId = new Long(Integer.parseInt(answerIdStr));
                resultMap.put(questionId, answerId);
                String questionIdStr = Long.toString(questionId);
                radioCheckedList.add(questionIdStr);
            }
        }

        Page<Question> pageQuestion = questionService.findAllPage(currentPage);
        int totalPages = pageQuestion.getTotalPages();
        List<Question> questionList = pageQuestion.getContent();
        ModelAndView modelAndView = new ModelAndView("quiz-page");

        modelAndView.addObject("questionList", questionList);
        modelAndView.addObject("totalPages", totalPages);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("radioCheckedList", radioCheckedList);

        if (currentPage > totalPages + 1) {
            return new ModelAndView("redirect:/gradequizz");
        }
        return modelAndView;
    }
}
