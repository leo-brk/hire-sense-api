package com.boreksolutions.hiresenseapi.core.industry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
    boolean existsByName(String name);

    @Query("Select i from Industry i where i.id = :id and i.deletedAt is null")
    Optional<Industry> findById(@Param("id") Long id);

    @Query("Select i from Industry i where i.deletedAt is null")
    List<Industry> findAll();
}