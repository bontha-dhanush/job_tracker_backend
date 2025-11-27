// package com.jobtracker.controller;

// import com.jobtracker.dto.ApiResponse;
// import com.jobtracker.entity.JobPosting;
// import com.jobtracker.entity.User;
// import com.jobtracker.repository.JobPostingRepository;
// import com.jobtracker.repository.UserRepository;
// import com.jobtracker.service.AuthService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/admin")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:3000")
// public class AdminController {

//     private final JobPostingRepository jobPostingRepository;
//     private final UserRepository userRepository;
//     private final AuthService authService;

//     @GetMapping("/job-postings")
//     public ResponseEntity<ApiResponse> getAllJobPostings() {
//         try {
//             List<JobPosting> jobPostings = jobPostingRepository.findAll();
//             return ResponseEntity.ok(ApiResponse.success("Job postings retrieved successfully", jobPostings));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }

//     @PostMapping("/job-postings")
//     public ResponseEntity<ApiResponse> createJobPosting(@RequestBody JobPosting jobPosting) {
//         try {
//             User currentUser = authService.getCurrentUser();
//             jobPosting.setCreatedBy(currentUser);
//             jobPosting.setPostedDate(LocalDateTime.now());
//             JobPosting savedJob = jobPostingRepository.save(jobPosting);
//             return ResponseEntity.ok(ApiResponse.success("Job posting created successfully", savedJob));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }

//     @PutMapping("/job-postings/{id}")
//     public ResponseEntity<ApiResponse> updateJobPosting(@PathVariable Long id, @RequestBody JobPosting jobPosting) {
//         try {
//             JobPosting existingJob = jobPostingRepository.findById(id)
//                     .orElseThrow(() -> new RuntimeException("Job posting not found"));

//             existingJob.setTitle(jobPosting.getTitle());
//             existingJob.setCompany(jobPosting.getCompany());
//             existingJob.setLocation(jobPosting.getLocation());
//             existingJob.setDescription(jobPosting.getDescription());
//             existingJob.setSalaryRange(jobPosting.getSalaryRange());
//             existingJob.setEmploymentType(jobPosting.getEmploymentType());
//             existingJob.setApplicationUrl(jobPosting.getApplicationUrl());
//             existingJob.setIsActive(jobPosting.getIsActive());

//             JobPosting updatedJob = jobPostingRepository.save(existingJob);
//             return ResponseEntity.ok(ApiResponse.success("Job posting updated successfully", updatedJob));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }

//     @DeleteMapping("/job-postings/{id}")
//     public ResponseEntity<ApiResponse> deleteJobPosting(@PathVariable Long id) {
//         try {
//             jobPostingRepository.deleteById(id);
//             return ResponseEntity.ok(ApiResponse.success("Job posting deleted successfully", null));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }

//     @GetMapping("/stats")
//     public ResponseEntity<ApiResponse> getAdminStats() {
//         try {
//             long totalUsers = userRepository.count();
//             long totalJobPostings = jobPostingRepository.count();
//             long activeJobPostings = jobPostingRepository.countByIsActiveTrue();

//             Map<String, Long> stats = Map.of(
//                     "totalUsers", totalUsers,
//                     "totalJobPostings", totalJobPostings,
//                     "activeJobPostings", activeJobPostings
//             );

//             return ResponseEntity.ok(ApiResponse.success("Admin stats retrieved successfully", stats));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }
// }
package com.jobtracker.controller;

import com.jobtracker.dto.ApiResponse;
import com.jobtracker.entity.JobPosting;
import com.jobtracker.entity.User;
import com.jobtracker.repository.JobPostingRepository;
import com.jobtracker.repository.UserRepository;
import com.jobtracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @GetMapping("/job-postings")
    public ResponseEntity<ApiResponse> getAllJobPostings() {
        try {
            List<JobPosting> jobPostings = jobPostingRepository.findAll();
            return ResponseEntity.ok(ApiResponse.success("Job postings retrieved successfully", jobPostings));
        } catch (Exception e) {
            log.error("Error getting job postings: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/job-postings")
    public ResponseEntity<ApiResponse> createJobPosting(@RequestBody JobPosting jobPosting) {
        try {
            User currentUser = authService.getCurrentUser();
            jobPosting.setCreatedBy(currentUser);
            jobPosting.setPostedDate(LocalDateTime.now());
            JobPosting savedJob = jobPostingRepository.save(jobPosting);
            return ResponseEntity.ok(ApiResponse.success("Job posting created successfully", savedJob));
        } catch (Exception e) {
            log.error("Error creating job posting: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/job-postings/{id}")
    public ResponseEntity<ApiResponse> updateJobPosting(@PathVariable Long id, @RequestBody JobPosting jobPosting) {
        try {
            JobPosting existingJob = jobPostingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Job posting not found"));

            existingJob.setTitle(jobPosting.getTitle());
            existingJob.setCompany(jobPosting.getCompany());
            existingJob.setLocation(jobPosting.getLocation());
            existingJob.setDescription(jobPosting.getDescription());
            existingJob.setSalaryRange(jobPosting.getSalaryRange());
            existingJob.setEmploymentType(jobPosting.getEmploymentType());
            existingJob.setApplicationUrl(jobPosting.getApplicationUrl());
            existingJob.setIsActive(jobPosting.getIsActive());

            JobPosting updatedJob = jobPostingRepository.save(existingJob);
            return ResponseEntity.ok(ApiResponse.success("Job posting updated successfully", updatedJob));
        } catch (Exception e) {
            log.error("Error updating job posting: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/job-postings/{id}")
    public ResponseEntity<ApiResponse> deleteJobPosting(@PathVariable Long id) {
        try {
            jobPostingRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Job posting deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting job posting: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getAdminStats() {
        try {
            log.info("Fetching admin statistics");
            
            long totalUsers = userRepository.count();
            long totalJobPostings = jobPostingRepository.count();
            
            // Use try-catch for active job postings count
            long activeJobPostings;
            try {
                activeJobPostings = jobPostingRepository.countByIsActiveTrue();
            } catch (Exception e) {
                log.warn("countByIsActiveTrue failed, using alternative method: {}", e.getMessage());
                // Alternative approach
                activeJobPostings = jobPostingRepository.findAll().stream()
                        .filter(job -> Boolean.TRUE.equals(job.getIsActive()))
                        .count();
            }

            Map<String, Long> stats = new HashMap<>();
            stats.put("totalUsers", totalUsers);
            stats.put("totalJobPostings", totalJobPostings);
            stats.put("activeJobPostings", activeJobPostings);

            log.info("Admin stats: totalUsers={}, totalJobPostings={}, activeJobPostings={}", 
                    totalUsers, totalJobPostings, activeJobPostings);

            return ResponseEntity.ok(ApiResponse.success("Admin stats retrieved successfully", stats));
        } catch (Exception e) {
            log.error("Error getting admin stats: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve admin statistics: " + e.getMessage()));
        }
    }
}