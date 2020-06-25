package com.codegym.education.model;

import lombok.Data;

@Data
public class AnswerForm {
    private int[] choosen;

    public AnswerForm() {

    }

    public AnswerForm(int[] choosen) {
        this.choosen = choosen;
    }

//    public int[] getChoosen() {
//        return choosen;
//    }
//
//    public void setChoosen(int[] choosen) {
//        this.choosen = choosen;
//    }
}
