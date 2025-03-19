package com.boreksolutions.hiresenseapi.core.company.dto.request;

import com.boreksolutions.hiresenseapi.config.validation.uniqueCompanyName.UniqueCompanyName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompany {

    @NotBlank(message = "Company name cannot be blank")
    @UniqueCompanyName(message = "Company name already exists")
    private String name;

    private String url;

    @PositiveOrZero(message = "Number of employees must be a positive number or zero")
    private Integer numberOfEmployees;

    @PositiveOrZero(message = "Open jobs number must be a positive number or zero")
    private Integer openJobsNumber;

    @NotNull(message = "Industry ID cannot be null")
    private Long industryId;

    @NotNull(message = "Updated by user ID cannot be null")
    private Long updatedById;
}