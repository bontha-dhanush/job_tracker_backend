// package com.jobtracker.repository;

// import com.jobtracker.entity.JobPosting;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
//     long countByIsActiveTrue();
// }
package com.jobtracker.repository;

import com.jobtracker.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    
    // Make sure this method exists and works correctly
    long countByIsActiveTrue();
    
    // Alternative approach if the above doesn't work
    @Query("SELECT COUNT(j) FROM JobPosting j WHERE j.isActive = true")
    long countActiveJobPostings();
}