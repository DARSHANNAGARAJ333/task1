package com.example.dms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;

@Service
public class FileService {

    private final String uploadDir = "uploads/";

    public FileService() throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
    }

    // Save a chunk
    public void saveChunk(MultipartFile file, String fileName, int chunkNumber) throws IOException {
        Path chunkPath = Paths.get(uploadDir + fileName + "_chunk_" + chunkNumber);
        Files.write(chunkPath, file.getBytes());
    }

    // Finalize upload
    public void assembleFile(String fileName, int totalChunks) throws IOException {
        Path finalPath = Paths.get(uploadDir + fileName);
        try (OutputStream os = Files.newOutputStream(finalPath, StandardOpenOption.CREATE)) {
            for (int i = 1; i <= totalChunks; i++) {
                Path chunkPath = Paths.get(uploadDir + fileName + "_chunk_" + i);
                Files.copy(chunkPath, os);
                Files.delete(chunkPath);
            }
        }
    }

    // Download chunk
    public byte[] getChunk(String fileName, int chunkNumber, int chunkSize) throws IOException {
        Path chunkPath = Paths.get(uploadDir + fileName);
        try (RandomAccessFile raf = new RandomAccessFile(chunkPath.toFile(), "r")) {
            raf.seek((long) (chunkNumber - 1) * chunkSize);
            int size = (int) Math.min(chunkSize, raf.length() - raf.getFilePointer());
            byte[] buffer = new byte[size];
            raf.readFully(buffer);
            return buffer;
        }
    }
}
