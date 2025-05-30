package com.example.home_work_49.repository;

import com.example.home_work_49.models.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {

    List<Vacancy> findAllVacanciesByAuthor_Email(String authorEmail);

    List<Vacancy> findAllByCategory_Name(String categoryName);

    Page<Vacancy> findAllByIsActiveOrderByCreatedDateDesc(Boolean isActive, Pageable pageable);

    Page<Vacancy> findAllByIsActiveOrderByCreatedDateAsc(Boolean isActive, Pageable pageable);

    Page<Vacancy> findAllByIsActive(Boolean isActive, Pageable pageable);

    @Query(
            value = "SELECT v.* " +
                    "FROM VACANCIES v " +
                    "JOIN RESPONDED_APPLICANTS ra ON ra.VACANCY_ID = v.ID " +
                    "WHERE v.is_active = :isActive " +
                    "group by v.id " +
                    "ORDER BY count(ra.ID) DESC",

            nativeQuery = true
    )
    Page<Vacancy> findAllByIsActiveOrderByResponseDesc(Boolean isActive, Pageable pageable);

    @Query(
            value = "SELECT v.* " +
                    "FROM VACANCIES v " +
                    "JOIN RESPONDED_APPLICANTS ra ON ra.VACANCY_ID = v.ID " +
                    "WHERE v.is_active = :isActive " +
                    "group by v.id " +
                    "ORDER BY count(ra.ID) ASC ",

            nativeQuery = true
    )
    Page<Vacancy> findAllByIsActiveOrderByResponseASC(Boolean isActive, Pageable pageable);

    Page<Vacancy> findAllVacanciesByNameContainingAndIsActiveIsTrue(String name, Boolean isActive, Pageable pageable);

    Page<Vacancy> findAllVacanciesBySalaryGreaterThanEqualAndIsActiveIsTrue(Double salaryIsGreaterThan, Boolean isActive, Pageable pageable);

    List<Vacancy> findAllByIsActiveAndCategory_IdAndSalaryIsGreaterThanEqual(Boolean isActive, Long categoryId, Double salaryIsGreaterThan);
}