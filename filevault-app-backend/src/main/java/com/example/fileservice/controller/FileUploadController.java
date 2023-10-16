package com.example.fileservice.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.fileservice.entity.File;
import com.example.fileservice.entity.User;
import com.example.fileservice.service.FileService;
import com.example.fileservice.service.S3Service;
import com.example.fileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
@CrossOrigin
@Slf4j
public class FileUploadController {

    @Autowired
    private final S3Service s3Service;

    @Autowired
    private final UserService userService;

    @Autowired
    private final FileService fileService;

    @PostMapping("upload/{userId}")
    public ResponseEntity<String> uploadFile(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file,
                             @RequestParam("description") String description) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found with ID: " + userId);
            }
            String fileName = file.getOriginalFilename();
            Optional<File> fileOptional = fileService.getFileByNameAndUserId(fileName, userId);
            User user = userOptional.get();
            fileName = s3Service.putObject(file, userId);
            if(fileOptional.isEmpty()) {
                File s3file = new File(fileName, description, user);
                fileService.saveFile(s3file);
            } else{
                fileService.updateFileByNameAndUserId(fileName, userId, description);
            }
            return ResponseEntity.ok("File uploaded to S3 successfully!");
        } catch (AmazonS3Exception e) {
            return ResponseEntity.badRequest().body("S3 Error: " + e.getErrorMessage());
        } catch (Exception e) {
            log.error("File upload to S3 failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping("update/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable("fileId") Long fileId, @RequestParam("file") MultipartFile file,
                             @RequestParam("description") String description) {
        try {
            Optional<File> fileOptional = fileService.getFileById(fileId);
            if (fileOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("File not found with ID: " + fileId);
            }
            File s3file = fileOptional.get();
            s3Service.deleteObject(s3file.getFilename(), s3file.getUser().getId());
            String fileName = s3Service.putObject(file, s3file.getUser().getId());
            fileService.updateFile(fileId, fileName, description);
            return ResponseEntity.ok("File updated in S3 successfully!");
        } catch (AmazonS3Exception e) {
            return ResponseEntity.badRequest().body("S3 Error: " + e.getErrorMessage());
        } catch (Exception e) {
            log.error("File update to S3 failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @DeleteMapping("delete/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long fileId) {
        try {
            Optional<File> fileOptional = fileService.getFileById(fileId);
            if (fileOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("File not found with ID: " + fileId);
            }
            File s3file = fileOptional.get();
            s3Service.deleteObject(s3file.getFilename(), s3file.getUser().getId());
            fileService.deleteFileById(s3file.getId());
            return ResponseEntity.ok("File Deleted from S3 successfully!");
        } catch(AmazonS3Exception e){
            return ResponseEntity.badRequest().body("S3 Error: " + e.getErrorMessage());
        } catch (Exception e) {
            log.error("File deletion from S3 failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<File>> getFilesByUserId(@PathVariable("userId") Long userId) {
        List<File> userFiles = fileService.getFilesByUserId(userId);
        return ResponseEntity.ok(userFiles);
    }

    @GetMapping("/all")
    public ResponseEntity<List<File>> getAllFiles() {
        List<File> userFiles = fileService.getAllFiles();
        return ResponseEntity.ok(userFiles);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<String> downloadFile(@PathVariable("fileId") Long fileId) {
        try {
            Optional<File> fileOptional = fileService.getFileById(fileId);
            if (fileOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            File s3file = fileOptional.get();
            return s3Service.downloadFileFromCloudFront(s3file.getFilename(), s3file.getUser().getId());
        } catch (Exception e) {
            log.error("File download from CloudFront failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
