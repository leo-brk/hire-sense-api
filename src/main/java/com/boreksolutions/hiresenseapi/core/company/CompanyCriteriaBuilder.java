package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.core.company.dto.request.CompanyFilter;
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
public class CompanyCriteriaBuilder {

    private final EntityManager entityManager;

    public Page<Company> filterCompanies(CompanyFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filter by name
        if (filter.getName() != null && !filter.getName().isEmpty())
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%%" + filter.getName().toLowerCase() + "%%"));

        // Filter by number of employees
        if (filter.getNumberOfEmployees() > 0)
            predicates.add(criteriaBuilder.equal(root.get("numberOfEmployees"), filter.getNumberOfEmployees()));

        // Filter by open jobs number
        if (filter.getOpenJobsNumber() > 0)
            predicates.add(criteriaBuilder.equal(root.get("openJobsNumber"), filter.getOpenJobsNumber()));

        // Filter by industry ID
        Long industryId = filter.getIndustryId();
        if (industryId != null)
            predicates.add(criteriaBuilder.equal(root.get("industry").get("id"), industryId));

        predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

        // Apply predicates to the query
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Execute the query for the paginated results
        TypedQuery<Company> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Company> companies = query.getResultList();

        // Create a query to count the total number of matching records
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Company> countRoot = countQuery.from(Company.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Create a query to count the total number of matching records
        TypedQuery<Company> sizeQuery = entityManager.createQuery(criteriaQuery);
        int total = sizeQuery.getResultList().size();


        return new PageImpl<>(companies, pageable, total);
    }
}
