package com.example.dms.model;

import jakarta.persistence.*;

@Entity
public class FileChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private Integer chunkNumber;
    private String path; // path where chunk is stored

    // Getters and Setters
}
