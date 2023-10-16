package com.example.fileservice.repository;

import com.example.fileservice.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findById(Long id);

    Optional<File> findByFilenameAndUserId(String name, Long userId);

    void deleteFileById(Long id);

    List<File> findByUserId(Long userId);

}
