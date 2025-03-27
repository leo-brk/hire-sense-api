package com.boreksolutions.hiresenseapi.core.job;

import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.company.Company;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.job.dto.request.JobFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JobCriteriaBuilder {

    private final EntityManager entityManager;

    public Page<Job> filterJobs(JobFilter filter, Pageable pageable) {
        Objects.requireNonNull(filter, "JobFilter cannot be null");
        Objects.requireNonNull(pageable, "Pageable cannot be null");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> root = cq.from(Job.class);

        List<Predicate> predicates = buildPredicates(filter, cb, root);
        cq.where(predicates.toArray(new Predicate[0]));


        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> {
                Path<Object> path = root.get(order.getProperty());
                cq.orderBy(order.isAscending() ? cb.asc(path) : cb.desc(path));
            });
        }

        TypedQuery<Job> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Job> jobs = query.getResultList();
        Long total = getTotalCount(filter);

        return new PageImpl<>(jobs, pageable, total);
    }

    private List<Predicate> buildPredicates(JobFilter filter, CriteriaBuilder cb, Root<Job> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(filter.getTitle())) {
            predicates.add(cb.like(
                    cb.lower(root.get("title")),
                    "%" + filter.getTitle().trim().toLowerCase() + "%"
            ));
        }

        if (StringUtils.isNotBlank(filter.getIndustry())) {
            Join<Job, Industry> industryJoin = root.join("industry", JoinType.INNER);
            predicates.add(cb.like(
                    cb.lower(industryJoin.get("name")),
                    "%" + filter.getIndustry().trim().toLowerCase() + "%"
            ));
        }

        if (StringUtils.isNotBlank(filter.getCity())) {
            Join<Job, City> cityJoin = root.join("city", JoinType.INNER);
            predicates.add(cb.like(
                    cb.lower(cityJoin.get("name")),
                    "%" + filter.getCity().trim().toLowerCase() + "%"
            ));
        }

        if (StringUtils.isNotBlank(filter.getCompany())) {
            Join<Job, Company> companyJoin = root.join("company", JoinType.INNER);
            predicates.add(cb.like(
                    cb.lower(companyJoin.get("name")),
                    "%" + filter.getCompany().trim().toLowerCase() + "%"
            ));
        }

        return predicates;
    }

    private Long getTotalCount(JobFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Job> root = countQuery.from(Job.class);

        List<Predicate> predicates = buildPredicates(filter, cb, root);
        countQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}