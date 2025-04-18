package com.example.home_work_49.repository;

import com.example.home_work_49.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findAllByIsActive(Boolean isActive);

    List<Vacancy> findAllVacanciesByAuthor_Email(String authorEmail);
}
