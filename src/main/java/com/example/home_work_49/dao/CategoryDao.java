//package com.example.home_work_49.dao;
//
//import com.example.home_work_49.models.Category;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class CategoryDao {
//    private final JdbcTemplate jdbcTemplate;
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    public boolean isCategoryExists(Long id) {
//        String sql = "select exists(select 1 from categories where id = ?)";
//        boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, id);
//        return exists;
//    }
//
//    public List<Category> getCategories(){
//        String sql = "select * from categories";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
//    }
//
//}
