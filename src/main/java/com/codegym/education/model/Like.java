package com.codegym.education.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Long userId;
    @ManyToOne
    private AppDoc appDoc;
    @ManyToOne
    private Lesson lesson;
}
