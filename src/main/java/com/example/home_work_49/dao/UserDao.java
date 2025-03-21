package com.example.home_work_49.dao;

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
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();


    public void addUser(User user) {
        String sql = "insert into users(name, surname, age, email, password, phone_number, avatar, account_type) " +
                "values(:name, :surname, :age, :email, :password, :phone_number, :avatar, :account_type)";

        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("name", user.getName())
                        .addValue("surname", user.getSurname())
                        .addValue("age", user.getAge())
                        .addValue("email", user.getEmail())
                        .addValue("password", user.getPassword())
                        .addValue("phone_number", user.getPhoneNumber())
                        .addValue("avatar", user.getAvatar())
                        .addValue("account_type", user.getAccountType())
        );
    }
}
