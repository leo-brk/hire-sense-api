package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.core.cityCompany.CityCompany;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 50)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "open_jobs_number")
    private Integer openJobsNumber;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CityCompany> cityCompanies = new ArrayList<>();

    public Company(Long id, String name) {
        this.setId(id);
        this.name = name;
    }
}