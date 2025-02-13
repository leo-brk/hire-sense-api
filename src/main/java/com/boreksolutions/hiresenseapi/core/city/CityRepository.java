package com.boreksolutions.hiresenseapi.core.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
            FROM City c WHERE c.name = :name AND c.deletedAt is null
            """)
    boolean existsByName(@Param("name") String name);
}