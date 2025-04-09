package com.boreksolutions.hiresenseapi.core.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    @Query("Select j from JobEntity j where j.id = :id and j.deletedAt is null ")
    Optional<JobEntity> findById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM job_entity; DELETE FROM company; DELETE FROM industry; DELETE FROM city; DELETE FROM country;", nativeQuery = true)
    void clearData();

}
