package com.boreksolutions.hiresenseapi.core.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    @Query("Select j from JobEntity j where j.id = :id and j.deletedAt is null ")
    Optional<JobEntity> findById(@Param("id") Long id);
}
