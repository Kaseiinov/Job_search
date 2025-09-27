package com.example.home_work_49.repository;

import com.example.home_work_49.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> , JpaSpecificationExecutor<Publication> {

    Page<Publication> findAllByUser_Email(String userEmail, Pageable pageable);

    @EntityGraph(attributePaths = {"comments", "comments.user", "comments.user.avatar", "user", "user.avatar", "category"})
    Page<Publication> findAll(Specification<Publication> spec, Pageable pageable);
}
