package com.boreksolutions.hiresenseapi.core.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.id = ?1 AND c.deletedAt IS NULL")
    Optional<Company> findById(Long id);

    @Query("SELECT new Company(c.id, c.name) from Company c")
    List<Company> findAllWithPartialData();

    long count();

    boolean existsByName(String name);
}