package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.core.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName(String name);


    @Query("Select c from City c where c.deletedAt is null")
    List<City> findAll();

    @Query("Select c from City c where c.id = :id and c.deletedAt is null")
    Optional<City> findById(@Param("id") Long id);
}