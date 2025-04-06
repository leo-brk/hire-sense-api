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

        // Filter by Title
        if (filter.getTitle() != null && !filter.getTitle().isEmpty())
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                    "%%" + filter.getTitle().toLowerCase() + "%%"));

        // Filter by company ID
        Long companyId = filter.getCompanyId();
        if (companyId != null)
            predicates.add(criteriaBuilder.equal(root.get("company").get("id"), companyId));

        // Filter by city ID
        Long cityId = filter.getCityId();
        if (cityId != null)
            predicates.add(criteriaBuilder.equal(root.get("city").get("id"), cityId));

        // Filter by industry ID
        Long industryId = filter.getIndustryId();
        if (industryId != null)
            predicates.add(criteriaBuilder.equal(root.get("industry").get("id"), industryId));


        predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

        // Apply predicates to the query
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Execute the query for the paginated results
        TypedQuery<JobEntity> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<JobEntity> jobs = query.getResultList();

        // Create a query to count the total number of matching records
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<JobEntity> countRoot = countQuery.from(JobEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Create a query to count the total number of matching records
        TypedQuery<JobEntity> sizeQuery = entityManager.createQuery(criteriaQuery);
        int total = sizeQuery.getResultList().size();


        return new PageImpl<>(jobs, pageable, total);
    }
}
