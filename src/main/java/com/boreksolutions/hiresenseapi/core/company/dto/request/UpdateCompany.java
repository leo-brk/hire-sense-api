package com.boreksolutions.hiresenseapi.core.company.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCompany {

    @Positive
    private Long id;

    private String name;

    private String url;

    private Integer numberOfEmployees;

    private Integer openJobsNumber;

    private Long industryId;
}