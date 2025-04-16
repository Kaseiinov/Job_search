package com.example.home_work_49.dao;

import com.example.home_work_49.models.UserImage;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserImageDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<UserImage> getImageById(long imageId) {
        String sql = "select * from users_images where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new BeanPropertyRowMapper<>(UserImage.class),
                                imageId
                        )
                )
        );
    }

    public void save(Long userId, String filename) {
        String sql = "insert into users_images (user_id, file_name) values (?, ?)";
        jdbcTemplate.update(sql, userId, filename);
    }

    public Optional<UserImage> findByUserId(long userId) {
        String sql = "select * from users_images where user_id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserImage.class), userId)
                )
        );
    }
}
