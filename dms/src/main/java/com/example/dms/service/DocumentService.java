package com.example.dms.service;

import com.example.dms.model.Document;
import com.example.dms.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public List<Document> searchByTitle(String title) {
        return documentRepository.findByTitleContainingIgnoreCase(title);
    }
}

