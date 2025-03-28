package com.boreksolutions.hiresenseapi.core.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByName(String name);

    @Query("Select c from Country c where c.deletedAt is null")
    List<Country> findAll();

    @Query("Select c from Country c where c.id = :id and c.deletedAt is null")
    Optional<Country> findById(@Param("id") Long id);
}
