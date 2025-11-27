// package com.jobtracker.repository;

// import com.jobtracker.entity.JobApplication;
// import com.jobtracker.entity.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;
// import java.util.List;
// import java.util.Map;

// @Repository
// public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
//     List<JobApplication> findByUser(User user);
//     List<JobApplication> findByUserAndStatus(User user, JobApplication.ApplicationStatus status);
//     Long countByUserAndStatus(User user, JobApplication.ApplicationStatus status);
//     long countByUser(User user);
    
//     @Query("SELECT j.status as status, COUNT(j) as count FROM JobApplication j WHERE j.user = :user GROUP BY j.status")
//     List<Map<String, Object>> findApplicationStatsByUser(User user);
// }
// package com.jobtracker.repository;

// import com.jobtracker.entity.JobApplication;
// import com.jobtracker.entity.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
//     List<JobApplication> findByUser(User user);
//     List<JobApplication> findByUserAndStatus(User user, JobApplication.ApplicationStatus status);
//     Long countByUserAndStatus(User user, JobApplication.ApplicationStatus status);
//     long countByUser(User user);
    
//     // Fixed query - use string function to convert enum to string
//     @Query("SELECT FUNCTION('STRING', j.status) as status, COUNT(j) as count FROM JobApplication j WHERE j.user = :user GROUP BY j.status")
//     List<Object[]> findApplicationStatsByUser(User user);
// }
package com.jobtracker.repository;

import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUser(User user);
    List<JobApplication> findByUserAndStatus(User user, JobApplication.ApplicationStatus status);
    Long countByUserAndStatus(User user, JobApplication.ApplicationStatus status);
    long countByUser(User user);
    
    // Alternative approach - use native query
    @Query(value = "SELECT status as status, COUNT(*) as count FROM job_applications WHERE user_id = :userId GROUP BY status", nativeQuery = true)
    List<Object[]> findApplicationStatsByUser(Long userId);
}