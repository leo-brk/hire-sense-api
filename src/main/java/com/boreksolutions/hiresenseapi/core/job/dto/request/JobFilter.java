package com.boreksolutions.hiresenseapi.core.job.dto.request;

import com.boreksolutions.hiresenseapi.core.job.Type;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class JobFilter {

    private String jobTitle;
    private String title;
    private Type jobType;
    private String postedDate;
    private String companySize;
@Pattern(regexp = "^(1-50|50-100|100-200|200\\+)$", message = "Invalid company size range")
public String getCompanySize() {
    return companySize;
}
    private Integer openJobsNumber;
    private String positionName;
    private List<Long> industryIds;
    private List<Long> cityIds;
    private List<Long> companyIds;
    private List<Long> countryIds;

    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDateTime getStartDateTime() {
        return startDate != null ? startDate.atStartOfDay() : null;
    }

    public LocalDateTime getEndDateTime() {
        return endDate != null ? endDate.atTime(LocalTime.MAX) : null;
    }

}