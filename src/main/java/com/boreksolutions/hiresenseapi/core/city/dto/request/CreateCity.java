package com.boreksolutions.hiresenseapi.core.city.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCity {

    @NotBlank(message = "City name cannot be blank")
    private String name;

    @NotNull(message = "Country ID cannot be null")
    private Long countryId;
}