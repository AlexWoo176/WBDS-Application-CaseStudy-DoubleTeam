package com.codegym.education.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Table;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "long")
    private String nameLesson;
    @Column(nullable = false, columnDefinition = "long")
    private String titleLess;
    @Column(nullable = false, columnDefinition = "long")
    private String contentLesson;
    @Column(nullable = false, columnDefinition = "long")
    private String imgLesson;
    @Column(nullable = false, columnDefinition = "long")
    private String videoLesson;
    @Column(nullable = false, columnDefinition = "long")
    private String typeLesson;
    @DateTimeFormat
    private LocalDateTime date;

    public Lesson() {
    }

}
