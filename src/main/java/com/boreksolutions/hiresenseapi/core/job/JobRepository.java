package com.boreksolutions.hiresenseapi.core.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("Select j from Job j where j.id = :id and j.deletedAt is null ")
    Optional<Job> findById(@Param("id") Long id);
}
