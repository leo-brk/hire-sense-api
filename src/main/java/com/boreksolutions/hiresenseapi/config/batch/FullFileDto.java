package com.boreksolutions.hiresenseapi.config.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullFileDto {
    private String uniqId;
    private String crawlTimestamp;
    private String pageUrl;
    private String postDate;
    private String inputJobTitle;
    private String crawledJobTitle;
    private String jobType;
    private String jobDescription;
    private String companyName;
    private String location;
    private String city;
    private String country;
    private String industry;
    private String companySize;
    private String openJobsCount;
    private String openJobsCountUrl;

}

