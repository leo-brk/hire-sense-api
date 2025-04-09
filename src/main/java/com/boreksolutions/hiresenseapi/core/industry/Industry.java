package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "industry")
public class Industry extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

//    @OneToMany(mappedBy = "industry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Job> jobs;
//
//    @OneToMany(mappedBy = "industry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Company> companies;
}