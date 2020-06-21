package com.codegym.education.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class AppDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nameDocument;
    @Column(nullable = false)
    private String titleDocument;
    @Column(nullable = false)
    private String imgDocument;
    @Column(nullable = false)
    private String contentDocument;
    @Column(nullable = false)
    private String typeDocument;
    @Column(nullable = false)
    private Date date;

    public AppDoc() {
    }
}
