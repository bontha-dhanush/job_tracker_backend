package com.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "job_postings")
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String company;
    private String location;
    
    @Column(length = 2000)
    private String description;
    
    private String salaryRange;
    private String employmentType;
    private String applicationUrl;
    
    private Boolean isActive = true;
    private LocalDateTime postedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    private LocalDateTime createdAt = LocalDateTime.now();
}