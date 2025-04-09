package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.core.base.BaseEntity;
import com.boreksolutions.hiresenseapi.core.country.Country;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

//    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CityCompany> cityCompanies = new ArrayList<>();
}