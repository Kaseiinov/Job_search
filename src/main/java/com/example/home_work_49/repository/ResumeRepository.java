package com.example.home_work_49.repository;

import com.example.home_work_49.models.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Long> {
    Page<Resume> findAllByIsActive(Boolean isActive, Pageable pageable);

    List<Resume> findAllByCategory_Name(String categoryName);

    List<Resume> findAllByApplicant_Email(String applicantEmail);

    Page<Resume> findAllByIsActiveOrderByCreatedDateDesc(Boolean isActive, Pageable pageable);

    Page<Resume> findAllByIsActiveOrderByCreatedDateAsc(Boolean isActive, Pageable pageable);
}
