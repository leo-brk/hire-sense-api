package com.boreksolutions.hiresenseapi.core.country.dto.request;

import com.boreksolutions.hiresenseapi.config.validation.uniqueCountry.UniqueCountryName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCountry {

    @NotBlank(message = "Country name cannot be blank")
    @UniqueCountryName(message = "Country name already exists")
    private String name;
}