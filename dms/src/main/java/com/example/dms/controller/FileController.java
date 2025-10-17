package com.example.dms.controller;

import com.example.dms.service.FileService;
import com.example.dms.service.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final FileStorageService storageService;

    // ✅ Constructor-based dependency injection
    public FileController(FileService fileService, FileStorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }

    // ✅ Upload one chunk (using FileStorageService)
    @PostMapping("/upload-chunk-storage")
    public ResponseEntity<String> uploadChunkStorage(
            @RequestParam String fileId,
            @RequestParam int chunkNumber,
            @RequestParam MultipartFile chunk) {
        try {
            storageService.saveChunk(fileId, chunkNumber, chunk);
            return ResponseEntity.ok("Chunk " + chunkNumber + " uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while uploading chunk: " + e.getMessage());
        }
    }

    // ✅ Merge chunks (using FileStorageService)
    @PostMapping("/finalize-upload-storage")
    public ResponseEntity<String> finalizeUploadStorage(
            @RequestParam String fileId,
            @RequestParam String fileName,
            @RequestParam int totalChunks) {
        try {
            storageService.mergeChunks(fileId, fileName, totalChunks);
            return ResponseEntity.ok("File merged successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while merging chunks: " + e.getMessage());
        }
    }

    // ✅ Download specific byte range (using FileStorageService)
    @GetMapping("/download-chunk-storage")
    public ResponseEntity<byte[]> downloadChunkStorage(
            @RequestParam String fileName,
            @RequestParam long start,
            @RequestParam long end) {
        try {
            byte[] chunk = storageService.getChunk(fileName, start, end);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(chunk);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ✅ Upload chunk (using FileService)
    @PostMapping("/upload-chunk")
    public ResponseEntity<String> uploadChunk(
            @RequestParam MultipartFile file,
            @RequestParam String fileName,
            @RequestParam int chunkNumber) throws IOException {
        fileService.saveChunk(file, fileName, chunkNumber);
        return ResponseEntity.ok("Chunk uploaded successfully: " + chunkNumber);
    }

    // ✅ Finalize file (using FileService)
    @PostMapping("/finalize-upload")
    public ResponseEntity<String> finalizeUpload(
            @RequestParam String fileName,
            @RequestParam int totalChunks) throws IOException {
        fileService.assembleFile(fileName, totalChunks);
        return ResponseEntity.ok("File assembled successfully");
    }

    // ✅ Download file chunk (using FileService)
    @GetMapping("/download-chunk/{fileName}/{chunkNumber}")
    public ResponseEntity<byte[]> downloadChunk(
            @PathVariable String fileName,
            @PathVariable int chunkNumber,
            @RequestParam int chunkSize) throws IOException {
        byte[] chunk = fileService.getChunk(fileName, chunkNumber, chunkSize);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(chunk);
    }
}
