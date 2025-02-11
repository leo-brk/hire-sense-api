package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.base.BaseEntity;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "crawled_job_title")
    private String crawledJobTitle;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_same_positions")
    private Integer numberOfSamePositions;

    @Column(name = "job_type")
    private Type jobType;

    @Column(name = "archived")
    private Boolean archived;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}