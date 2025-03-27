package com.boreksolutions.hiresenseapi.core.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.id = ?1 AND c.deletedAt IS NULL")
    Optional<Company> findById(Long id);

    boolean existsByName(String name);
}