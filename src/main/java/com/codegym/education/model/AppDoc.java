package com.codegym.education.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AppDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    String nameDocument;
    @Column(nullable = false)
    String titleDocument;
    @Column(nullable = false)
    String imgDocument;
    @Column(nullable = false)
    String contentDocument;

    public AppDoc() {
    }
}
