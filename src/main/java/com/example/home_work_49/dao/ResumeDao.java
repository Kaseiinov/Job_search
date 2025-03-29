package com.example.home_work_49.dao;

import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public boolean resumeIdExists(Long id) {
        String isExistsCheck = "SELECT EXISTS(SELECT 1 FROM resumes WHERE id = ?)";
        boolean exists = jdbcTemplate.queryForObject(isExistsCheck, Boolean.class, id);

        return exists;
    }


    public List<Resume> getAllActiveResumes(){
        String sql = "select * from resumes" +
                " where is_active = true";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> getResumeByCategory(String resumeCategory) {
        String sql = "select r.*, c.name as category" +
                " from resumes r join categories c on r.category_id = c.id where lower(c.name) = lower(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), resumeCategory);
    }

    public List<Resume> getResumeByUser(String userName) {
        String sql = "select r.*" +
                " from resumes r join users u on r.applicant_id = u.id where lower(u.name) = lower(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), userName);
    }

    public Optional<Resume> getResumeById(Long id){
        String sql = "select * from  resumes where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
                )
        );
    }

    public void deleteResumeById(Long id){
        String sql = "delete from resumes where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateResumeById(Long id, Resume resume){
        String sql = "UPDATE resumes SET " +
                "applicant_id = :applicantId, " +
                "name = :name, " +
                "category_id = :categoryId, " +
                "salary = :salary, " +
                "is_active = :isActive, " +
                "created_date = :createdDate, " +
                "update_time = :updateTime " +
                "WHERE id = :id";

        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("applicantId", resume.getApplicantId())
                        .addValue("name", resume.getName())
                        .addValue("categoryId", resume.getCategoryId())
                        .addValue("salary", resume.getSalary())
                        .addValue("isActive", resume.isActive())
                        .addValue("createdDate", resume.getCreatedDate())
                        .addValue("updateTime", resume.getUpdateTime())
                        .addValue("id", id)
        );
    }


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
