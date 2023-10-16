package com.example.fileservice.service;


import com.example.fileservice.entity.File;

import java.util.List;
import java.util.Optional;

public interface FileService {
    File saveFile(File file);

    void updateFile(Long fileId, String newFileName, String description);

    void updateFileByNameAndUserId(String filename, Long userId, String newDescription);

    void deleteFileById(Long id);

    Optional<File> getFileById(Long id);

    Optional<File> getFileByNameAndUserId(String filename, Long userId);

    List<File> getFilesByUserId(Long userId);

    List<File> getAllFiles();
}
