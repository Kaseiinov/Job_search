package com.example.home_work_49.repository;

import com.example.home_work_49.models.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findAllVacanciesByAuthor_Email(String authorEmail);

    List<Vacancy> findAllByCategory_Name(String categoryName);

    Page<Vacancy> findAllByIsActiveOrderByCreatedDateDesc(Boolean isActive, Pageable pageable);

    Page<Vacancy> findAllByIsActiveOrderByCreatedDateAsc(Boolean isActive, Pageable pageable);

    Page<Vacancy> findAllByIsActive(Boolean isActive, Pageable pageable);
}
