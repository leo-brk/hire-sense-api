package com.boreksolutions.hiresenseapi.core.job.dto.request;

import com.boreksolutions.hiresenseapi.core.job.Type;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class JobFilter {

    private String jobTitle;//
    private String title;
    private Type jobType;//
    private String postedDate;//
    private String companySize;//
    private Integer openJobsNumber;//
    private String positionName;
    private Long industryId;//
    private Long cityId;//
    private Long companyId;
    private Long countryId;//

    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDateTime getStartDateTime() {
        return startDate != null ? startDate.atStartOfDay() : null;
    }

    public LocalDateTime getEndDateTime() {
        return endDate != null ? endDate.atTime(LocalTime.MAX) : null;
    }
}