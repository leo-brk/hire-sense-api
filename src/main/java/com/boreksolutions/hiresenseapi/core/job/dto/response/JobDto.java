package com.boreksolutions.hiresenseapi.core.job.dto.response;

import lombok.Data;
import com.boreksolutions.hiresenseapi.core.job.Type;

@Data
public class JobDto {
    private Long id;
    private String title;
    private String crawledJobTitle;
    private String url;
    private String description;
    private Integer numberOfSamePositions;
    private Type jobType;
    private Boolean archived;
    private Long updatedById;
    private Long industryId;
    private Long companyId;
    private Long cityId;
}