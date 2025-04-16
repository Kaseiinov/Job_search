package com.example.home_work_49.dao;

import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public boolean vacancyIdExists(Long id) {
        String isExistsCheck = "SELECT EXISTS(SELECT 1 FROM vacancies WHERE id = ?)";
        boolean exists = jdbcTemplate.queryForObject(isExistsCheck, Boolean.class, id);

        return exists;
    }

    public void deleteVacancyById(Long id) {
        String sql = "delete from vacancies where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Vacancy> getAllActiveVacancies() {
        String sql  = "select * from vacancies where is_active = true";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> getAllVacancies() {
        String sql  = "select * from vacancies";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> getVacanciesByUser(String userEmail) {
        String sql = "select v.*" +
                " from vacancies v join users u on v.author_id = u.id where lower(u.email) = lower(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userEmail);
    }

    public Optional<Vacancy> getVacancyById(Long id) {
        String sql = "select * from vacancies where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id))
        );
    }


    public void updateVacancyById(Long id, Vacancy vacancy){
        String sql = "UPDATE vacancies SET " +
                "name = :name, " +
                "description = :description, " +
                "category_id = :categoryId, " +
                "salary = :salary, " +
                "exp_from = :expFrom, " +
                "exp_to = :expTo, " +
                "is_active = :isActive, " +
                "update_time = :updateTime " +
                "WHERE id = :id";

        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("name", vacancy.getName())
                        .addValue("description", vacancy.getDescription())
                        .addValue("categoryId", vacancy.getCategoryId())
                        .addValue("salary", vacancy.getSalary())
                        .addValue("expFrom", vacancy.getExpFrom())
                        .addValue("expTo", vacancy.getExpTo())
                        .addValue("isActive", vacancy.getIsActive())
                        .addValue("updateTime", vacancy.getUpdateTime())
                        .addValue("id", id)
        );
    }

    public List<Vacancy> getVacancyByCategory(String vacancyCategory) {
        String sql = "select v.*, c.name as category" +
                " from vacancies v join categories c on v.category_id = c.id where lower(c.name) = lower(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), vacancyCategory);
    }

    public List<Vacancy> getVacancyByApplicant(String applicantName) {
        String sql = "select v.* from vacancies v join users u on v.author_id = u.id" +
                " where lower(u.name) = lower(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), applicantName);
    }

    public void addVacancy(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) " +
                "VALUES (:name, :description, :categoryId, :salary, :expFrom, :expTo, :is_active, :authorId, :createdDate, :updateTime)";

        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("name", vacancy.getName())
                        .addValue("description", vacancy.getDescription())
                        .addValue("categoryId", vacancy.getCategoryId())
                        .addValue("salary", vacancy.getSalary())
                        .addValue("expFrom", vacancy.getExpFrom())
                        .addValue("expTo", vacancy.getExpTo())
                        .addValue("is_active", vacancy.getIsActive())
                        .addValue("authorId", vacancy.getAuthorId())
                        .addValue("createdDate", LocalDateTime.now())
                        .addValue("updateTime", vacancy.getUpdateTime())
        );
    }
}
