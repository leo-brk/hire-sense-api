package com.boreksolutions.hiresenseapi.core.cityCompany;

import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city_company")
public class CityCompany {

    @EmbeddedId
    private CityCompanyId id = new CityCompanyId();

    @ManyToOne
    @MapsId("cityId")
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;
}