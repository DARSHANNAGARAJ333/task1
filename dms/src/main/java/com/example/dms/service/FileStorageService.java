package com.example.dms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${file.storage.path}")
    private String storagePath;

    // Save file chunks
    public void saveChunk(String fileId, int chunkNumber, MultipartFile chunk) throws IOException {
        Path dir = Paths.get(storagePath, fileId);
        Files.createDirectories(dir);

        Path chunkFile = dir.resolve("chunk_" + chunkNumber);
        Files.write(chunkFile, chunk.getBytes(), StandardOpenOption.CREATE);
    }

    // Merge chunks into one complete file
    public void mergeChunks(String fileId, String fileName, int totalChunks) throws IOException {
        Path dir = Paths.get(storagePath, fileId);
        Path finalFile = Paths.get(storagePath, fileName);

        try (OutputStream out = Files.newOutputStream(finalFile, StandardOpenOption.CREATE)) {
            for (int i = 1; i <= totalChunks; i++) {
                Path chunkFile = dir.resolve("chunk_" + i);
                Files.copy(chunkFile, out);
            }
        }

        // Optionally delete chunk files
        for (int i = 1; i <= totalChunks; i++) {
            Files.deleteIfExists(dir.resolve("chunk_" + i));
        }
        Files.deleteIfExists(dir);
    }

    // Download chunk
    public byte[] getChunk(String fileName, long start, long end) throws IOException {
        Path filePath = Paths.get(storagePath, fileName);
        try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "r")) {
            raf.seek(start);
            int length = (int) (end - start + 1);
            byte[] buffer = new byte[length];
            raf.readFully(buffer);
            return buffer;
        }
    }
}
