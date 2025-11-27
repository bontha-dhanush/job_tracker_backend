// package com.jobtracker.service;

// import com.jobtracker.entity.JobApplication;
// import com.jobtracker.entity.User;
// import com.jobtracker.repository.JobApplicationRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @Service
// @RequiredArgsConstructor
// public class JobApplicationService {

//     private final JobApplicationRepository applicationRepository;
//     private final AuthService authService;

//     public List<JobApplication> getUserApplications() {
//         User currentUser = authService.getCurrentUser();
//         return applicationRepository.findByUser(currentUser);
//     }

//     public JobApplication createApplication(JobApplication application) {
//         User currentUser = authService.getCurrentUser();
//         application.setUser(currentUser);
//         return applicationRepository.save(application);
//     }

//     public JobApplication updateApplication(Long id, JobApplication application) {
//         User currentUser = authService.getCurrentUser();
//         JobApplication existingApplication = applicationRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Application not found"));

//         // Verify ownership
//         if (!existingApplication.getUser().getId().equals(currentUser.getId())) {
//             throw new RuntimeException("Not authorized to update this application");
//         }

//         existingApplication.setJobTitle(application.getJobTitle());
//         existingApplication.setCompany(application.getCompany());
//         existingApplication.setLocation(application.getLocation());
//         existingApplication.setDescription(application.getDescription());
//         existingApplication.setNotes(application.getNotes());
//         existingApplication.setStatus(application.getStatus());
//         existingApplication.setNextFollowUp(application.getNextFollowUp());

//         return applicationRepository.save(existingApplication);
//     }

//     public void deleteApplication(Long id) {
//         User currentUser = authService.getCurrentUser();
//         JobApplication application = applicationRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Application not found"));

//         // Verify ownership
//         if (!application.getUser().getId().equals(currentUser.getId())) {
//             throw new RuntimeException("Not authorized to delete this application");
//         }

//         applicationRepository.delete(application);
//     }

//     public Map<String, Long> getApplicationStats() {
//         User currentUser = authService.getCurrentUser();
//         List<Map<String, Object>> stats = applicationRepository.findApplicationStatsByUser(currentUser);
        
//         Map<String, Long> result = new HashMap<>();
//         for (JobApplication.ApplicationStatus status : JobApplication.ApplicationStatus.values()) {
//             result.put(status.name(), 0L);
//         }
        
//         for (Map<String, Object> stat : stats) {
//             String status = (String) stat.get("status");
//             Long count = (Long) stat.get("count");
//             result.put(status, count);
//         }
        
//         return result;
//     }
// }
package com.jobtracker.service;

import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.User;
import com.jobtracker.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final AuthService authService;

    public List<JobApplication> getUserApplications() {
        User currentUser = authService.getCurrentUser();
        return applicationRepository.findByUser(currentUser);
    }

    public JobApplication createApplication(JobApplication application) {
        User currentUser = authService.getCurrentUser();
        application.setUser(currentUser);
        return applicationRepository.save(application);
    }

    public JobApplication updateApplication(Long id, JobApplication application) {
        User currentUser = authService.getCurrentUser();
        JobApplication existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!existingApplication.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to update this application");
        }

        existingApplication.setJobTitle(application.getJobTitle());
        existingApplication.setCompany(application.getCompany());
        existingApplication.setLocation(application.getLocation());
        existingApplication.setDescription(application.getDescription());
        existingApplication.setNotes(application.getNotes());
        existingApplication.setStatus(application.getStatus());
        existingApplication.setNextFollowUp(application.getNextFollowUp());

        return applicationRepository.save(existingApplication);
    }

    public void deleteApplication(Long id) {
        User currentUser = authService.getCurrentUser();
        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to delete this application");
        }

        applicationRepository.delete(application);
    }

    // public Map<String, Long> getApplicationStats() {
    //     try {
    //         User currentUser = authService.getCurrentUser();
    //         List<Object[]> statsData = applicationRepository.findApplicationStatsByUser(currentUser);
            
    //         Map<String, Long> result = new HashMap<>();
            
    //         // Initialize with all possible statuses as 0
    //         for (JobApplication.ApplicationStatus status : JobApplication.ApplicationStatus.values()) {
    //             result.put(status.name(), 0L);
    //         }
            
    //         // Fill with actual counts from database
    //         for (Object[] stat : statsData) {
    //             String status = (String) stat[0]; // status as string
    //             Long count = (Long) stat[1];      // count as long
    //             result.put(status, count);
    //         }
            
    //         log.info("Application stats for user {}: {}", currentUser.getUsername(), result);
    //         return result;
            
    //     } catch (Exception e) {
    //         log.error("Error getting application stats: {}", e.getMessage(), e);
    //         throw new RuntimeException("Failed to get application statistics: " + e.getMessage());
    //     }
    //}
    public Map<String, Long> getApplicationStats() {
    try {
        User currentUser = authService.getCurrentUser();
        List<Object[]> statsData = applicationRepository.findApplicationStatsByUser(currentUser.getId());
        
        Map<String, Long> result = new HashMap<>();
        
        // Initialize with all possible statuses as 0
        for (JobApplication.ApplicationStatus status : JobApplication.ApplicationStatus.values()) {
            result.put(status.name(), 0L);
        }
        
        // Fill with actual counts from database
        for (Object[] stat : statsData) {
            String status = (String) stat[0]; // status as string from native query
            Long count = ((Number) stat[1]).longValue(); // count might be different type in native query
            result.put(status, count);
        }
        
        log.info("Application stats for user {}: {}", currentUser.getUsername(), result);
        return result;
        
    } catch (Exception e) {
        log.error("Error getting application stats: {}", e.getMessage(), e);
        throw new RuntimeException("Failed to get application statistics: " + e.getMessage());
    }
}
}