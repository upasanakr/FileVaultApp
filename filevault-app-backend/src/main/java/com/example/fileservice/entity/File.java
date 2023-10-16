package com.example.fileservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @Column(name="file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "TIMESTAMP", name = "last_modified_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Los_Angeles")
    private Date lastModifiedTime;

    @Column(columnDefinition = "TIMESTAMP", name = "last_updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Los_Angeles")
    private Date lastUpdatedTime;

    @PrePersist
    protected void onCreate() {
        lastModifiedTime = new Date();
        lastUpdatedTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedTime = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public File(String filename, String description, User user) {
        this.description = description;
        this.filename = filename;
        this.user = user;
    }
}
