package com.example.home_work_49.dao;

import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Vacancy> getAllVacancies() {
        String sql  = "select * from vacancies";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
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
                        .addValue("is_active", vacancy.isActive())
                        .addValue("authorId", vacancy.getAuthorId())
                        .addValue("createdDate", vacancy.getCreatedDate())
                        .addValue("updateTime", vacancy.getUpdateTime())
        );
    }
}
