package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country extends BaseEntity {

    @Column(name = "name")
    private String name;
}