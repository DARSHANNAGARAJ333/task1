package com.example.dms.controller;

import com.example.dms.model.Document;
import com.example.dms.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    // You can also move this path to application.properties if you prefer
    private static final String STORAGE_DIR = "C:/Users/darsh/angular/datastore/";

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // 🔹 Upload file + metadata
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("uploadedBy") String uploadedBy) {

        try {
            // Ensure directory exists
            File dir = new File(STORAGE_DIR);
            if (!dir.exists()) dir.mkdirs();

            // Save file to disk
            Path filePath = Paths.get(STORAGE_DIR + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            // Save metadata
            Document doc = new Document();
            doc.setTitle(title);
            doc.setDescription(description);
            doc.setFileName(file.getOriginalFilename());
            doc.setFileSize(file.getSize());
            doc.setUploadDate(LocalDateTime.now());
            doc.setUploadedBy(uploadedBy);

            Document savedDoc = documentService.save(doc);
            return ResponseEntity.ok(savedDoc);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔹 Download file
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        Optional<Document> optionalDoc = documentService.findById(id);
        if (optionalDoc.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Document doc = optionalDoc.get();
        Path filePath = Paths.get(STORAGE_DIR + doc.getFileName());
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileBytes = Files.readAllBytes(filePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }

    // 🔹 Get all or search by title
    @GetMapping
    public List<Document> getAll(@RequestParam(required = false) String title) {
        if (title != null) return documentService.searchByTitle(title);
        return documentService.findAll();
    }

    // 🔹 Get document metadata by ID
    @GetMapping("/{id}")
    public ResponseEntity<Document> getById(@PathVariable Long id) {
        return documentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Update metadata (not file)
    @PutMapping("/{id}")
    public ResponseEntity<Document> update(@PathVariable Long id, @RequestBody Document doc) {
        return documentService.findById(id).map(existing -> {
            existing.setTitle(doc.getTitle());
            existing.setDescription(doc.getDescription());
            existing.setUploadedBy(doc.getUploadedBy());
            existing.setFileName(doc.getFileName());
            return ResponseEntity.ok(documentService.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Delete file + metadata
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Document> optionalDoc = documentService.findById(id);
        if (optionalDoc.isPresent()) {
            Document doc = optionalDoc.get();
            Path filePath = Paths.get(STORAGE_DIR + doc.getFileName());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) {}
            documentService.delete(id);
        }
        return ResponseEntity.ok().build();
    }
}
