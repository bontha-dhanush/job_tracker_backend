package com.jobtracker.controller;

import com.jobtracker.dto.ApiResponse;
import com.jobtracker.dto.JobListing;
import com.jobtracker.service.JobSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JobSearchController {
    
    private final JobSearchService jobSearchService;
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchJobs(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "") String location,
            @RequestParam(defaultValue = "1") int page) {
        
        try {
            List<JobListing> jobs = jobSearchService.searchJobs(query, location, page);
            return ResponseEntity.ok(ApiResponse.success("Jobs retrieved successfully", jobs));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse> getFeaturedJobs() {
        try {
            List<JobListing> jobs = jobSearchService.searchJobs("software engineer", "United States", 1);
            return ResponseEntity.ok(ApiResponse.success("Featured jobs retrieved successfully", jobs));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}