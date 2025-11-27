package com.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private JobApplication application;

    private String title;
    private String description;
    private LocalDate reminderDate;
    private String reminderType; // FOLLOW_UP, INTERVIEW, DEADLINE
    private Boolean isCompleted = false;
    private LocalDate createdAt = LocalDate.now();
}