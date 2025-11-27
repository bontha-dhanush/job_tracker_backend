package com.jobtracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", nullable = true)
    private JobPosting jobPosting;

    @Column(nullable = false)
    private String jobTitle;

    private String company;
    private String location;
    private String description;
    private String notes;

    @Column(nullable = false)
    private LocalDate applicationDate = LocalDate.now();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDate nextFollowUp;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Prevents circular reference
    private User user;

    public enum ApplicationStatus {
        APPLIED, INTERVIEW, OFFER, ACCEPTED, REJECTED
    }
}