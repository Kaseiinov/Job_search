//package com.example.home_work_49.dao;
//
//import com.example.home_work_49.models.Resume;
//import com.example.home_work_49.models.User;
//import com.example.home_work_49.models.WorkExperienceInfo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.dao.support.DataAccessUtils;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class ResumeDao {
//    private final JdbcTemplate jdbcTemplate;
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//    private final KeyHolder keyHolder = new GeneratedKeyHolder();
//
//    public boolean resumeIdExists(Long id) {
//        String isExistsCheck = "SELECT EXISTS(SELECT 1 FROM resumes WHERE id = ?)";
//        boolean exists = jdbcTemplate.queryForObject(isExistsCheck, Boolean.class, id);
//
//        return exists;
//    }
//
//
//    public List<Resume> getAllActiveResumes(){
//        String sql = "select * from resumes" +
//                " where is_active = true";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
//    }
//
//    public List<Resume> getResumeByCategory(String resumeCategory) {
//        String sql = "select r.*, c.name as category" +
//                " from resumes r join categories c on r.category_id = c.id where lower(c.name) = lower(?)";
//
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), resumeCategory);
//    }
//
//    public List<Resume> getResumeByUser(String userEmail) {
//        String sql = "select r.*" +
//                " from resumes r join users u on r.applicant_id = u.id where lower(u.email) = lower(?)";
//
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), userEmail);
//    }
//
//    public Optional<Resume> getResumeById(Long id){
//        String sql = "select * from  resumes where id = ?";
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
//                )
//        );
//    }
//
//    public Optional<Resume> getResumeByName(String name){
//        String sql = "SELECT * FROM resumes WHERE lower(name) = lower(?) " +
//                "ORDER BY created_date DESC LIMIT 1";
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), name)
//                )
//        );
//    }
//
//    public void deleteResumeById(Long id){
//        String sql = "delete from resumes where id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    public void updateResumeById(Long id, Resume resume){
//        String sql = "UPDATE resumes SET " +
//                "name = :name, " +
//                "category_id = :categoryId, " +
//                "salary = :salary, " +
//                "is_active = :isActive, " +
//                "update_time = :updateTime " +
//                "WHERE id = :id";
//
//        namedParameterJdbcTemplate.update(sql,
//                new MapSqlParameterSource()
//                        .addValue("name", resume.getName())
//                        .addValue("categoryId", resume.getCategory().getId())
//                        .addValue("salary", resume.getSalary())
//                        .addValue("isActive", resume.getIsActive())
//                        .addValue("updateTime", resume.getUpdateTime())
//                        .addValue("id", id)
//        );
//    }
//
//    public void addResume(Resume resume) {
//        String sql = "insert into resumes(applicant_id, name, category_id, salary, is_active, created_date, update_time) " +
//                "values( :applicant_id,:name, :category_id, :salary, :is_active, :created_date, :update_time)";
//
//        namedParameterJdbcTemplate.update(sql,
//                new MapSqlParameterSource()
//                        .addValue("applicant_id", resume.getApplicant().getId())
//                        .addValue("name", resume.getName())
//                        .addValue("category_id", resume.getCategory().getId())
//                        .addValue("salary", resume.getSalary())
//                        .addValue("is_active", resume.getIsActive())
//                        .addValue("created_date", resume.getCreatedDate())
//                        .addValue("update_time", resume.getUpdateTime())
//
//        );
//    }
//}
