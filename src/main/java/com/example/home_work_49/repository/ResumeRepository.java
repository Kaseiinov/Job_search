package com.example.home_work_49.repository;

import com.example.home_work_49.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findAllByIsActive(Boolean isActive);

    List<Resume> findAllByCategory_Name(String categoryName);

    List<Resume> findAllByApplicant_Email(String applicantEmail);
}
