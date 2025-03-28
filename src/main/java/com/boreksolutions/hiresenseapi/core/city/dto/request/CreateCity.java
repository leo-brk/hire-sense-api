package com.boreksolutions.hiresenseapi.core.city.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCity {

    @NotBlank(message = "City name cannot be blank")
    private String name;

    private Long countryId;
}