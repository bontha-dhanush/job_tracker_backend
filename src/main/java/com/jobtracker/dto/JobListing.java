package com.jobtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListing {
    private String title;
    private String company;
    private String location;
    private String description;
    private String applyUrl;
    private String postedDate;
    private String salary;
    private String employmentType;
    private Boolean remote;
    private String jobId;
}
