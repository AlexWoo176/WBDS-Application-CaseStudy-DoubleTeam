package com.codegym.education.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nameLesson;
    @Column(nullable = false)
    private String titleLess;
    @Column(nullable = false)
    private String contentLesson;
    @Column(nullable = false)
    private String imgLesson;
    @Column(nullable = false)
    private String videoLesson;
    @Column(nullable = false)
    private String typeLesson;
    @Column(nullable = false)
    private Date date;

    public Lesson() {
    }

}
