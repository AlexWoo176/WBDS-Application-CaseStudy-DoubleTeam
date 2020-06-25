package com.codegym.education.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


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
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;


    public AppDoc() {
    }
}
