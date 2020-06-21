package com.codegym.education.repository;

import com.codegym.education.model.AppDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<AppDoc,Long> {
    Page <AppDoc> findByNameDocument(Pageable pageable, Optional<String> name);
}
