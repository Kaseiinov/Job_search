package com.example.home_work_49.dao;

import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();


    public void addResume(Resume resume) {
        String sql = "insert into resumes(applicant_id, name, category_id, salary, is_active, created_date, update_time) " +
                "values( :applicant_id,:name, :category_id, :salary, :is_active, :created_date, :update_time)";

        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("applicant_id", resume.getApplicantId())
                        .addValue("name", resume.getName())
                        .addValue("category_id", resume.getCategoryId())
                        .addValue("salary", resume.getSalary())
                        .addValue("is_active", resume.isActive())
                        .addValue("created_date", resume.getCreatedDate())
                        .addValue("update_time", resume.getUpdateTime())

        );
    }
}
