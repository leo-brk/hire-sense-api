package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobCriteriaBuilder {

    private final EntityManager entityManager;

    public Page<JobEntity> filterJobs(JobFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JobEntity> criteriaQuery = criteriaBuilder.createQuery(JobEntity.class);
        Root<JobEntity> root = criteriaQuery.from(JobEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filter by title
        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                    "%" + filter.getTitle().toLowerCase() + "%"));
        }

        // Filter by job type
//        if (filter.getJobType() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("jobType"), filter.getJobType()));
//        }

        // Filter by posted date
//        if (filter.getPostedDate() != null && !filter.getPostedDate().isEmpty()) {
//            predicates.add(criteriaBuilder.equal(root.get("postedDate"), filter.getPostedDate()));
//        }

        // Filter by company ID
        if (filter.getCompanyId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("company").get("id"), filter.getCompanyId()));
        }

        // Filter by company size
        if (filter.getCompanySize() != null) {
            predicates.add(criteriaBuilder.equal(root.get("company").get("companySize"), filter.getCompanySize()));
        }

        // Filter by open jobs count
        if (filter.getOpenJobsNumber() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("company").get("openJobsNumber"), filter.getOpenJobsNumber()));
        }

        // Filter by position name
        if (filter.getPositionName() != null && !filter.getPositionName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("positionName")),
                    "%" + filter.getPositionName().toLowerCase() + "%"));
        }

        // Filter by industry ID
        if (filter.getIndustryId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("industry").get("id"), filter.getIndustryId()));
        }

        // Filter by city ID
        if (filter.getCityId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("city").get("id"), filter.getCityId()));
        }

        // Filter by country ID
        if (filter.getCountryId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("country").get("id"), filter.getCountryId()));
        }

        // Apply predicates to the query
        predicates.add(criteriaBuilder.isNull(root.get("deletedAt"))); // Filter for non-deleted records
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Execute the query for the paginated results
        TypedQuery<JobEntity> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<JobEntity> jobs = query.getResultList();

        // Create a query to count the total number of matching records
        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(j) FROM JobEntity j", Long.class);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(jobs, pageable, total);
    }
}
