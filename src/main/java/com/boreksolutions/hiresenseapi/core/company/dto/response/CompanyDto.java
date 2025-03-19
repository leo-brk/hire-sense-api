package com.boreksolutions.hiresenseapi.core.company.dto.response;

import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto; //needs to be added later
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    @Positive
    private Long id;

    private String name;

    private String url;

    private Integer numberOfEmployees;

    private Integer openJobsNumber;

    private IndustryDto industry; //needs to be added later

    private UserDto updatedBy;
}