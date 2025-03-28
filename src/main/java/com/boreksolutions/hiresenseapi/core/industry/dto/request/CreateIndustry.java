package com.boreksolutions.hiresenseapi.core.industry.dto.request;

import com.boreksolutions.hiresenseapi.config.validation.uniqueIndustryName.UniqueIndustryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateIndustry {

    @NotBlank(message = "Industry name cannot be blank")
    @Size(min = 2, max = 100, message = "Industry name must be between 2 and 100 characters long")
    @UniqueIndustryName(message = "Industry name already exists")
    private String name;

}