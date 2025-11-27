// package com.jobtracker.controller;

// import com.jobtracker.dto.ApiResponse;
// import com.jobtracker.entity.JobApplication;
// import com.jobtracker.service.JobApplicationService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/applications")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:3000")
// public class JobApplicationController {
    
//     private final JobApplicationService applicationService;
    
//     @GetMapping
//     public ResponseEntity<ApiResponse> getUserApplications() {
//         try {
//             List<JobApplication> applications = applicationService.getUserApplications();
//             return ResponseEntity.ok(ApiResponse.success("Applications retrieved successfully", applications));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }
    
//     @PostMapping
//     public ResponseEntity<ApiResponse> createApplication(@RequestBody JobApplication application) {
//         try {
//             JobApplication savedApplication = applicationService.createApplication(application);
//             return ResponseEntity.ok(ApiResponse.success("Application created successfully", savedApplication));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }
    
//     @PutMapping("/{id}")
//     public ResponseEntity<ApiResponse> updateApplication(@PathVariable Long id, @RequestBody JobApplication application) {
//         try {
//             JobApplication updatedApplication = applicationService.updateApplication(id, application);
//             return ResponseEntity.ok(ApiResponse.success("Application updated successfully", updatedApplication));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }
    
//     @DeleteMapping("/{id}")
//     public ResponseEntity<ApiResponse> deleteApplication(@PathVariable Long id) {
//         try {
//             applicationService.deleteApplication(id);
//             return ResponseEntity.ok(ApiResponse.success("Application deleted successfully", null));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }
    
//     @GetMapping("/stats")
//     public ResponseEntity<ApiResponse> getApplicationStats() {
//         try {
//             Map<String, Long> stats = applicationService.getApplicationStats();
//             return ResponseEntity.ok(ApiResponse.success("Stats retrieved successfully", stats));
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//         }
//     }
// }
package com.jobtracker.controller;

import com.jobtracker.dto.ApiResponse;
import com.jobtracker.entity.JobApplication;
import com.jobtracker.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserApplications() {
        try {
            List<JobApplication> applications = applicationService.getUserApplications();
            return ResponseEntity.ok(ApiResponse.success("Applications retrieved successfully", applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createApplication(@RequestBody JobApplication application) {
        try {
            JobApplication savedApplication = applicationService.createApplication(application);
            return ResponseEntity.ok(ApiResponse.success("Application created successfully", savedApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateApplication(@PathVariable Long id, @RequestBody JobApplication application) {
        try {
            JobApplication updatedApplication = applicationService.updateApplication(id, application);
            return ResponseEntity.ok(ApiResponse.success("Application updated successfully", updatedApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok(ApiResponse.success("Application deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getApplicationStats() {
        try {
            Map<String, Long> stats = applicationService.getApplicationStats();
            return ResponseEntity.ok(ApiResponse.success("Stats retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
