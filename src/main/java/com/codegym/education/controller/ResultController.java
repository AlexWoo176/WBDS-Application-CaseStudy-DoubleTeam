package com.codegym.education.controller;

import com.codegym.education.model.Answer;
import com.codegym.education.model.CollectAnswer;
import com.codegym.education.model.Question;
import com.codegym.education.service.IQuestionService;
import com.codegym.education.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ResultController {

    private static Map<Long, CollectAnswer> analizeMap = new HashMap<>();

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IResultService resultService;


    @GetMapping("/gradequizz")
    public String gradeQuiz(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("welcome");

        Iterable<Question> questionIterable = questionService.findAll();
        Iterator<Question> questionIterator = questionIterable.iterator();
        int totalQuestion= 0;
        while (questionIterator.hasNext()) {
            totalQuestion++;
            questionIterator.next();
        }
        int score = calculateScore();
        float average = (float)score / totalQuestion;
        System.out.println("So diem ban dat duoc la:" + average * 100 + "%");
        if ((average * 100) >= 50) {
            System.out.println("Ban da do");
        } else {
            System.out.println("Ban da truot");
        }
        return "redirect:/analize";
    }

    private Long findAnswerIdCorrect(Long questionId) {
        Question question = questionService.findById(questionId);
        for (Answer answer : question.getAnswers()) {
            if (answer.isCorrect()) {
                return answer.getId();
            }
        }
        return -1L;
    }

    private int calculateScore() {
        int score = 0;

        Long questionId = 0L, answerCorrectId = 0L, answerId = 0L;
        for (Map.Entry<Long, Long> entry : QuestionController.resultMap.entrySet()) {
            questionId = entry.getKey();
            answerCorrectId = findAnswerIdCorrect(questionId);
            answerId = entry.getValue();

            if (answerId.equals(answerCorrectId)) {
                score++;
            }
        }
        return score;
    }

    @GetMapping("/analize")
    private ModelAndView analizeResult() {

        Map<Long, CollectAnswer> finalMap = new HashMap<>();
        Long questionId = 0L, answerCorrectId = 0L, answerId = 0L;
        ModelAndView modelAndView = new ModelAndView("result");

        Iterable<Question> iterable = questionService.findAll();
        Iterator<Question> iterator = iterable.iterator();
        List<Question> questionList = new ArrayList<>();
        iterator.forEachRemaining(questionList::add);

    process:    for (Question question : questionList) {
            for (Map.Entry<Long, Long> entry : QuestionController.resultMap.entrySet()) {
                questionId = entry.getKey();
                answerCorrectId = findAnswerIdCorrect(questionId);
                answerId = entry.getValue();

                Long mark = 1L;
                Long sub = 1L;
                if (answerCorrectId >= answerId) {
                    mark = answerCorrectId;
                    sub = answerCorrectId - answerId;
                } else {
                    mark = answerId;
                    sub = answerId - answerCorrectId;
                }

                int top = 1;
                for (int i = 1; i <= 4; i++) {
                    top = i * 4;
                    if (top >= mark) {
                        break;
                    }
                }

                mark = top - mark;
                if (answerCorrectId >= answerId) {
                    answerCorrectId = mark;
                    answerId = answerCorrectId - sub;
                } else {
                    answerId = mark;
                    answerCorrectId = answerId - sub;
                }

                if (question.getId().equals(questionId)) {
                    CollectAnswer collectAnswer = new CollectAnswer();
                    collectAnswer.setAnswerCorrectId(answerCorrectId);
                    collectAnswer.setAnswerId(answerId);

                    finalMap.put(questionId, collectAnswer);
                    continue process;
                }

                /*CollectAnswer collectAnswer = new CollectAnswer();
                collectAnswer.setAnswerCorrectId(answerCorrectId);
                collectAnswer.setAnswerId(answerId);
                analizeMap.put(questionId, collectAnswer);*/
            }

            finalMap.put(question.getId(), null);
        }

        /*Set<Long> keySet = analizeMap.keySet();
        List<Long> keyList = new ArrayList<Long>(keySet);*/
        /*modelAndView.addObject("analizeMap", analizeMap);*/
        modelAndView.addObject("finalMap", finalMap);
        modelAndView.addObject("questionList", questionList);

        return modelAndView;
    }
}
