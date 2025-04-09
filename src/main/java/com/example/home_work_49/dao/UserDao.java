package com.example.home_work_49.dao;

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
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public void updateUserByName(String userName, User user){
        String sql = "UPDATE users SET " +
                "name = :name, " +
                "surname = :surname, " +
                "age = :age, " +
                "password = :password, " +
                "phone_number = :phoneNumber, " +
                "avatar = :avatar, " +
                "account_type = :accountType ," +
                "role_id = :roleId " +
                "WHERE lower(name) = lower(:userName)";

        namedParameterJdbcTemplate.update(sql, mapper(userName, user));
    }

    public Optional<Boolean> isUser(Long id, String type) {
        String sql = "SELECT id FROM users WHERE id = ? AND account_type = UPPER(?)";

        List<Long> result = jdbcTemplate.queryForList(sql, Long.class, id, type);
        boolean exists = !result.isEmpty();
        return Optional.of(exists);
    }

    public Optional<User> getUserByName(String userName) {
        String sql = "select * from users where lower(name) = lower(?)";

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userName)
                ));
    }

    public Optional<User> getUserByPhone(String userPhone) {
        String sql = "select * from users where phone_number = ?";

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userPhone)
                ));
    }

    public Optional<User> getUserByEmail(String userEmail) {
        String sql = "select * from users where lower(email) = lower(?)";

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userEmail)
                ));
    }

    public List<User> getApplicantsByVacancy(String vacancyName) {
        String sql = """
        SELECT
            u.id AS id,
            u.name AS name,
            u.surname AS surname,
            u.age as age,
            u.email as  email,
            v.name AS password,
            r.name AS phone_number,
            ra.confirmation AS avatar,
            u.account_type
        FROM
            responded_applicants ra
                INNER JOIN
            resumes r ON ra.resume_id = r.id
                INNER JOIN
            users u ON r.applicant_id = u.id
                INNER JOIN
            vacancies v ON ra.vacancy_id = v.id
        WHERE
            lower(v.name) = lower(?)
        """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), vacancyName);
    }

    public boolean emailExists(String email) {
        String isExistsCheck = "SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)";
        boolean exists = jdbcTemplate.queryForObject(isExistsCheck, Boolean.class, email);

        return exists;
    }

    public void addUser(User user) {
        String sql = "insert into users(name, surname, age, email, password, phone_number, avatar, account_type, enabled, role_id) " +
                    "values(:name, :surname, :age, :email, :password, :phone_number, :avatar, :account_type, :enabled, :role_id)";

            namedParameterJdbcTemplate.update(sql, mapper(user));

    }

    public MapSqlParameterSource mapper(User user){
        return new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("avatar", user.getAvatar())
                .addValue("account_type", user.getAccountType())
                .addValue("enabled", user.getEnabled())
                .addValue("role_id", user.getRoleId());
    }

    public MapSqlParameterSource mapper(String userName, User user){
        return new MapSqlParameterSource()
                .addValue("userName", userName)
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("password", user.getPassword())
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("avatar", user.getAvatar())
                .addValue("accountType", user.getAccountType())
                .addValue("enabled", user.getEnabled())
                .addValue("roleId", user.getRoleId());
    }
}
