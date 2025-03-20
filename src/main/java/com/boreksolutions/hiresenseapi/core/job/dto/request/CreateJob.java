package com.boreksolutions.hiresenseapi.core.job.dto.request;

import com.boreksolutions.hiresenseapi.core.job.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateJob {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String crawledJobTitle;

    private String url;

    private String description;

    @NotNull(message = "Number of same positions cannot be null")
    private Integer numberOfSamePositions;

    @NotNull(message = "Job type cannot be null")
    private Type jobType;

    private Boolean archived;

    @NotNull(message = "Updated by user ID cannot be null")
    private Long updatedById;

    @NotNull(message = "Industry ID cannot be null")
    private Long industryId;

    @NotNull(message = "Company ID cannot be null")
    private Long companyId;

    @NotNull(message = "City ID cannot be null")
    private Long cityId;
}