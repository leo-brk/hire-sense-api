package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.core.base.BaseEntity;
import com.boreksolutions.hiresenseapi.core.cityCompany.CityCompany;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "min_positions")
    private Integer minPositions;

    @Column(name = "max_positions")
    private Integer maxPositions;

    @Column(name = "open_jobs_number")
    private String openJobsNumber;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CityCompany> cityCompanies = new ArrayList<>();

    public Company(Long id, String name) {
        this.setId(id);
        this.name = name;
    }
}