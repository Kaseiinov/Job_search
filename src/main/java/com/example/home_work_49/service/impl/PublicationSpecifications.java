package com.example.home_work_49.service.impl;

import com.example.home_work_49.models.Publication;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PublicationSpecifications {
    public static Specification<Publication> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Publication> createdAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"), date);
    }

    public static Specification<Publication> updatedAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("updateDate"), date);
    }

    public static Specification<Publication> containsText(String text) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + text.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
            );
        };
    }

    public static Specification<Publication> isEnabled() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("enabled"));
    }
}
