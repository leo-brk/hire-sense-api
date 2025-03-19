package com.boreksolutions.hiresenseapi.core.industry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
    boolean existsByName(String name);
}