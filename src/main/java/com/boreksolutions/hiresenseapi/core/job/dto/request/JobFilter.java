package com.boreksolutions.hiresenseapi.core.job.dto.request;

import lombok.Data;

@Data
public class JobFilter {

    private String title;

    private String industry;

    private String city;

    private String company;
}
