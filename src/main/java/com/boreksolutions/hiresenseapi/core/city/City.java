package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.core.base.BaseEntity;
import com.boreksolutions.hiresenseapi.core.cityCompany.CityCompany;
import com.boreksolutions.hiresenseapi.core.country.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CityCompany> cityCompanies = new ArrayList<>();
}