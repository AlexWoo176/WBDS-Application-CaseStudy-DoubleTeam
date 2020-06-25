package com.codegym.education.repository;

import com.codegym.education.model.AppDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<AppDoc,Long> {
    Page <AppDoc> findByNameDocumentContaining(Pageable pageable, Optional<String> name);
    Page <AppDoc> findAllByOrderByDateDesc(Pageable pageable);
    List <AppDoc> findByTypeDocument(String type);
}
