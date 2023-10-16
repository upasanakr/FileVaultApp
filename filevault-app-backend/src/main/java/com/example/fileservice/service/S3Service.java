package com.example.fileservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3Service {

    @Autowired
    private final AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    private String s3bucket;

    @Value("${aws.cloudFrontDomain}")
    private String cloudFrontDomain;

    public String putObject(MultipartFile file, Long userId) throws IOException {
        String fileName = file.getOriginalFilename();
        try {
            String key = userId + "/" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(new PutObjectRequest(s3bucket, key, file.getInputStream(), metadata));
            return fileName;
        } catch (AmazonS3Exception e)  {
            log.error("File upload to S3 failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: " + e.getMessage());
            throw e;
        }
    }

    public void deleteObject(String fileName, Long userId) {
        try {
            String key = userId + "/" + fileName;
            amazonS3.deleteObject(new DeleteObjectRequest(s3bucket, key));
        } catch (AmazonS3Exception e) {
            log.error("Delete file from S3 failed: " + e.getMessage());
            throw e;
        }
        catch (Exception e) {
            log.error("An unexpected error occurred: " + e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<String> downloadFileFromCloudFront(String fileName, Long userId) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            String s3ObjectKey = userId + "/" + encodedFileName;
            String cloudFrontUrl = cloudFrontDomain + s3ObjectKey;
            return ResponseEntity.ok(cloudFrontUrl);
        } catch (Exception e) {
            log.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
