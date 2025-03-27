package com.boreksolutions.hiresenseapi.core.company.dto.request;

import lombok.Data;

@Data
public class CompanyFilter {

    private String name;

    private int numberOfEmployees;

    private int openJobsNumber;

    private Long industryId;
}