package com.boreksolutions.hiresenseapi.core.cityCompany;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityCompanyId implements Serializable {
    private Long cityId;
    private Long companyId;
}