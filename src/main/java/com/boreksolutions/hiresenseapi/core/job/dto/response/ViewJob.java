package com.boreksolutions.hiresenseapi.core.job.dto.response;

import com.boreksolutions.hiresenseapi.core.job.JobEntity;
import com.boreksolutions.hiresenseapi.core.job.Type;
import lombok.Data;

@Data
public class ViewJob {
    private Long id;
    private String title;
    private String crawledJobTitle;
    private String url;
    private String description;
    private Integer numberOfSamePositions;
    private Type jobType;
    private Boolean archived;
    private Long updatedById;
    private String industry;
    private String company;
    private String city;

    public static Object fromEntity(JobEntity jobEntity) {
        return null;
    }
}
