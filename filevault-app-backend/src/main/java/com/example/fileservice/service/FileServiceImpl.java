package com.example.fileservice.service;

import com.example.fileservice.entity.File;
import com.example.fileservice.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private final FileRepository fileRepository;

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    @Transactional
    public void deleteFileById(Long id) {
        fileRepository.deleteFileById(id);
    }

    @Override
    @Transactional
    public void updateFile(Long fileId, String newFileName, String description) {
        // Retrieve the existing File entity by its ID
        File existingFile = fileRepository.findById(fileId).orElse(null);

        if (existingFile != null) {
            // Make changes to the entity
            existingFile.setFilename(newFileName);
            existingFile.setDescription(description);

            // Save the updated entity back to the database
            fileRepository.save(existingFile);
        }
    }

    @Override
    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public Optional<File> getFileByNameAndUserId(String filename, Long userId) {
        return fileRepository.findByFilenameAndUserId(filename, userId);
    }

    @Transactional
    public void updateFileByNameAndUserId(String filename, Long userId, String newDescription) {
        // Find the file by name and user ID
        Optional<File> optionalFile = fileRepository.findByFilenameAndUserId(filename, userId);

        if (optionalFile.isPresent()) {
            File file = optionalFile.get();

            // Update the file with the new description
            file.setDescription(newDescription);
            file.setFilename(filename);

            // Save the updated file
            fileRepository.save(file);
        }
    }

    @Override
    public List<File> getFilesByUserId(Long userId) {
        return fileRepository.findByUserId(userId);
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
}
