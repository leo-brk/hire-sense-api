package com.boreksolutions.hiresenseapi.core.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompanyId(Long companyId);
    List<Job> findByCityId(Long cityId);
    List<Job> findByIndustryId(Long industryId);
    List<Job> findByTitleContainingIgnoreCase(String title);
}
