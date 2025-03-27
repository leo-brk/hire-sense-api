package com.boreksolutions.hiresenseapi.core.job.dto.request;

import lombok.Data;

@Data
public class JobFilter {

    private String title;

    private Long industryId;

    private Long cityId;

    private Long companyId;
}
