package com.codegym.education.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class AppDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "long")
    private String nameDocument;
    @Column(nullable = false, columnDefinition = "long")
    private String titleDocument;
    @Column(nullable = false, columnDefinition = "long")
    private String imgDocument;
    @Column(nullable = false, columnDefinition = "long")
    private String contentDocument;
    @Column(nullable = false, columnDefinition = "long")
    private String typeDocument;
    @DateTimeFormat
    private LocalDateTime date;


    public AppDoc() {
    }
}
