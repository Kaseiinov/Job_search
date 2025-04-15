package com.example.home_work_49.dao;

import com.example.home_work_49.models.EducationInfo;
import com.example.home_work_49.models.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public void addEducation(EducationInfo education){
        String sql = "insert into education_info(resume_id, institution, program, start_date, end_date, degree) " +
                "values(:resumeId, :institution, :program, :startDate, :endDate, :degree)";
        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("resumeId", education.getResumeId())
                        .addValue("institution", education.getInstitution())
                        .addValue("program", education.getProgram())
                        .addValue("startDate", education.getStartDate())
                        .addValue("endDate", education.getEndDate())
                        .addValue("degree", education.getDegree())
        );
    }
}
