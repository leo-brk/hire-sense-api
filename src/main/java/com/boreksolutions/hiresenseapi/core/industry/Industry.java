package com.boreksolutions.hiresenseapi.core.industry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "industry")
public class Industry  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "industry_seq")
    @SequenceGenerator(name = "industry_seq", sequenceName = "industry_seq", allocationSize = 50)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

//    @OneToMany(mappedBy = "industry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Job> jobs;
//
//    @OneToMany(mappedBy = "industry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Company> companies;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Industry(Long id, String name) {
        this.setId(id);
        this.name = name;
    }
}