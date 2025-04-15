package com.example.home_work_49.dao;

import com.example.home_work_49.models.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkExperienceInfoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    public void addWorkExperience(WorkExperienceInfo experience ){
        String sql = "insert into work_experience_info(resume_id, years, company_name, position, responsibilities) " +
                "values(:resumeId, :years, :companyName, :position, :responsibilities)";
        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("resumeId", experience.getResumeId())
                        .addValue("years", experience.getYears())
                        .addValue("companyName", experience.getCompanyName())
                        .addValue("position", experience.getPosition())
                        .addValue("responsibilities", experience.getResponsibilities())
        );
    }
}
