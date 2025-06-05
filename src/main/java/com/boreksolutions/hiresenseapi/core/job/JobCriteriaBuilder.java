package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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

    public long count() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<JobEntity> root = countQuery.from(JobEntity.class);

        countQuery.select(criteriaBuilder.count(root));
        countQuery.where(criteriaBuilder.isNull(root.get("deletedAt")));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public Page<JobEntity> filterJobs(JobFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JobEntity> criteriaQuery = criteriaBuilder.createQuery(JobEntity.class);
        Root<JobEntity> root = criteriaQuery.from(JobEntity.class);

        List<Predicate> predicates = new ArrayList<>();

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

//        if (filter.getCompanyId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("company").get("id"), filter.getCompanyId()));
//        }

        if (filter.getCompanySize() != null) {
            Path<Integer> minPositions = root.get("company").get("minPositions");
            Path<Integer> maxPositions = root.get("company").get("maxPositions");

            switch (filter.getCompanySize()) {
                case "1-50":
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.lessThanOrEqualTo(minPositions, 50),
                            criteriaBuilder.greaterThanOrEqualTo(maxPositions, 1)
                    ));
                    break;
                case "51-100":
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.lessThanOrEqualTo(minPositions, 100),
                            criteriaBuilder.greaterThanOrEqualTo(maxPositions, 51)
                    ));
                    break;
                case "101-200":
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.lessThanOrEqualTo(minPositions, 200),
                            criteriaBuilder.greaterThanOrEqualTo(maxPositions, 101)
                    ));
                    break;
                case "201+":
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(minPositions, 201));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid company size range");
            }
        }

//        if (filter.getOpenJobsNumber() != null) {
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("company").get("openJobsNumber"), filter.getOpenJobsNumber()));
//        }
//
//        if (filter.getPositionName() != null && !filter.getPositionName().isEmpty()) {
//            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("positionName")),
//                    "%" + filter.getPositionName().toLowerCase() + "%"));
//        }

//        if (filter.getIndustryId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("industry").get("id"), filter.getIndustryId()));
//        }
//
//        if (filter.getCityId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("city").get("id"), filter.getCityId()));
//        }

//         For Industry (name filter)
        if (filter.getIndustryIds() != null && !filter.getIndustryIds().isEmpty()) {
            predicates.add(root.get("industry").get("id").in(filter.getIndustryIds()));
        }

//      For Company (name filter)
        if (filter.getCompanyIds() != null && !filter.getCompanyIds().isEmpty()) {
            predicates.add(root.get("company").get("id").in(filter.getCompanyIds()));
        }
//      For City (name filter)
        if (filter.getCityIds() != null && !filter.getCityIds().isEmpty()) {
            predicates.add(root.get("city").get("id").in(filter.getCityIds()));
        }

        // For Country (name filter)
        if (filter.getCountryIds() != null && !filter.getCountryIds().isEmpty()) {
            predicates.add(root.get("country").get("id").in(filter.getCountryIds()));
        }

// Keep existing ID-based filters (for backward compatibility)
//        if (filter.getIndustryId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("industry").get("id"), filter.getIndustryId()));
//        }
//        if (filter.getCompanyId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("company").get("id"), filter.getCompanyId()));
//        }
//        if (filter.getCityId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("city").get("id"), filter.getCityId()));
//        }
//
//        if (filter.getCountryId() != null) {
//            predicates.add(criteriaBuilder.equal(root.get("country").get("id"), filter.getCountryId()));
//        }

        predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        TypedQuery<JobEntity> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<JobEntity> jobs = query.getResultList();

        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(j) FROM JobEntity j", Long.class);
        Long total = countQuery.getSingleResult();



        return new PageImpl<>(jobs, pageable, total);
    }
}