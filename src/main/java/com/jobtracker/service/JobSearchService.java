package com.jobtracker.service;

import com.jobtracker.dto.JobListing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobSearchService {

    private final RestTemplate restTemplate;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    public List<JobListing> searchJobs(String query, String location, int page) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", rapidApiKey);
            headers.set("X-RapidAPI-Host", rapidApiHost);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = String.format("https://%s/search?query=%s&location=%s&page=%d", 
                    rapidApiHost, query, location, page);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("data")) {
                List<Map<String, Object>> jobsData = (List<Map<String, Object>>) body.get("data");
                return convertToJobListings(jobsData);
            }
        } catch (Exception e) {
            // Fallback to mock data if API fails
            return getMockJobs();
        }
        
        return getMockJobs();
    }

    private List<JobListing> convertToJobListings(List<Map<String, Object>> jobsData) {
        List<JobListing> jobListings = new ArrayList<>();
        for (Map<String, Object> jobData : jobsData) {
            JobListing listing = new JobListing();
            listing.setTitle((String) jobData.get("job_title"));
            listing.setCompany((String) jobData.get("employer_name"));
            listing.setLocation((String) jobData.get("job_country"));
            listing.setDescription((String) jobData.get("job_description"));
            listing.setApplyUrl((String) jobData.get("job_apply_link"));
            listing.setPostedDate((String) jobData.get("job_posted_at_datetime_utc"));
            listing.setEmploymentType((String) jobData.get("job_employment_type"));
            listing.setRemote(jobData.get("job_is_remote") != null && (Boolean) jobData.get("job_is_remote"));
            listing.setJobId((String) jobData.get("job_id"));
            
            jobListings.add(listing);
        }
        return jobListings;
    }

    private List<JobListing> getMockJobs() {
        List<JobListing> mockJobs = new ArrayList<>();
        
        // Add some mock job listings
        mockJobs.add(new JobListing(
            "Software Engineer",
            "Tech Company Inc",
            "New York, NY",
            "We are looking for a skilled software engineer...",
            "https://company.com/apply",
            "2024-01-15",
            "$100,000 - $150,000",
            "Full-time",
            true,
            "job1"
        ));
        
        mockJobs.add(new JobListing(
            "Frontend Developer",
            "Web Solutions LLC",
            "Remote",
            "Join our frontend team to build amazing user experiences...",
            "https://websolutions.com/careers",
            "2024-01-10",
            "$90,000 - $130,000",
            "Full-time",
            true,
            "job2"
        ));
        
        return mockJobs;
    }
}