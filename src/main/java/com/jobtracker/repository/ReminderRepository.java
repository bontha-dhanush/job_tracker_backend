package com.jobtracker.repository;

import com.jobtracker.entity.Reminder;
import com.jobtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserAndReminderDate(User user, LocalDate date);
    List<Reminder> findByUserAndIsCompletedFalse(User user);
}