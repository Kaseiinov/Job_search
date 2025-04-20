package com.example.home_work_49.repository;

import com.example.home_work_49.models.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperienceInfo,Long> {
}
