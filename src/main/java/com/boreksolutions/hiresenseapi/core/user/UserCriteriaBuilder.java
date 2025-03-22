package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.core.user.dto.request.UserFilter;
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
public class UserCriteriaBuilder {

    private final EntityManager entityManager;

    public Page<User> filterUsers(UserFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filter by first name
        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                    root.get("firstName")), "%%" + filter.getFirstName().toLowerCase() + "%%"));
        }

        // Filter by last name
        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                    root.get("lastName")), "%%" + filter.getLastName().toLowerCase() + "%%"));
        }

        // Filter by email
        if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                    root.get("email")), "%%" + filter.getEmail().toLowerCase() + "%%"));
        }

        predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

        // Apply predicates to the query
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Execute the query for the paginated results
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> users = query.getResultList();

        // Create a query to count the total number of matching records
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Create a query to count the total number of matching records
        TypedQuery<User> sizeQuery = entityManager.createQuery(criteriaQuery);
        int total = sizeQuery.getResultList().size();

        return new PageImpl<>(users, pageable, total);
    }
}