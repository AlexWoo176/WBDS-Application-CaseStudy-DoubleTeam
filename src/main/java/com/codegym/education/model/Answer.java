package com.codegym.education.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="answers")
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, columnDefinition="TEXT")
    private String content;

    @ManyToOne
    private Question question;

    @Column(nullable=false)
    private Long question_id;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean correct;

    public Answer(){}

    public Answer(String content, Question question, Long question_id, boolean correct) {
        this.content = content;
        this.question = question;
        this.question_id = question_id;
        this.correct = correct;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public Question getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(Question question) {
//        this.question = question;
//    }
//
//    public Long getQuestion_id() {
//        return question_id;
//    }
//
//    public void setQuestion_id(Long question_id) {
//        this.question_id = question_id;
//    }
//
//    public boolean isCorrect() {
//        return correct;
//    }
//
//    public void setCorrect(boolean correct) {
//        this.correct = correct;
//    }
}
