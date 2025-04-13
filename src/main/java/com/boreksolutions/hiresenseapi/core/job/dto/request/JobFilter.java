package com.boreksolutions.hiresenseapi.core.job.dto.request;

import com.boreksolutions.hiresenseapi.core.job.Type;
import lombok.Data;

@Data
public class JobFilter {

    private String jobTitle;

    private String title;

    private Type jobType;

    private String postedDate;

    private String companySize;

    private Integer openJobsNumber;

    private String positionName;

    private Long industryId;

    private Long cityId;

    private Long companyId;

    private Long countryId;
}
