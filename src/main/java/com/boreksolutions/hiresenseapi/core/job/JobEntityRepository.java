package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.company.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    @Query("Select j from JobEntity j where j.id = :id and j.deletedAt is null ")
    Optional<JobEntity> findById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM job_entity; DELETE FROM company; DELETE FROM industry; DELETE FROM city; DELETE FROM country;", nativeQuery = true)
    void clearData();

    @Query("SELECT COUNT(j) FROM JobEntity j WHERE LOWER(j.crawledJobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "AND (:from IS NULL OR j.createdAt >= :from) " + "AND (:to IS NULL OR j.createdAt <= :to)")
    Long getJobsWithCrawledJobTitleName(@Param("keyword") String keyword, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    Long countByCrawledJobTitleContainingIgnoreCase(String keyword);


    @Query("SELECT j.city.name, COUNT(j) AS jobCount FROM JobEntity j GROUP BY j.city ORDER BY jobCount DESC")
    List<Object[]> findTop3CitiesWithMostJobs(Pageable pageable);

    @Query("SELECT j.company.name, COUNT(j) AS jobCount FROM JobEntity j GROUP BY j.company ORDER BY jobCount DESC")
    List<Object[]> findCompaniesWithMostOpenJobs(Pageable pageable);

    @Query("SELECT j.title, COUNT(j) AS positionCount FROM JobEntity j GROUP BY j.title ORDER BY positionCount DESC")
    List<Object[]> findTopPositions(Pageable pageable);

    @Query("SELECT j.industry.name, COUNT(j) AS jobCount FROM JobEntity j GROUP BY j.industry ORDER BY jobCount DESC ")
    List<Object[]> findIndustriesWithMostOpenJobs(Pageable pageable);

    long count();

}