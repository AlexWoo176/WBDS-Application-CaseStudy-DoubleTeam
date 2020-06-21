package com.codegym.education.service.document;

import com.codegym.education.model.AppDoc;
import com.codegym.education.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DocumentService implements IDocumentService{
    @Autowired
    private DocumentRepository documentRepository;


    @Override
    public Page<AppDoc> findAll(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    @Override
    public Optional<AppDoc> findById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public void save(AppDoc model) {
        documentRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public Page<AppDoc> findByNameDocument(Pageable pageable, Optional<String> name){
        return documentRepository.findByNameDocument(pageable,name);
    }
}
