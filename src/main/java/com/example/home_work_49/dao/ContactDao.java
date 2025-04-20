//package com.example.home_work_49.dao;
//
//import com.example.home_work_49.models.ContactInfo;
//import com.example.home_work_49.models.EducationInfo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
////@RequiredArgsConstructor
//public class ContactDao {
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//    private final JdbcTemplate jdbcTemplate;
//
//    public void addContact(ContactInfo contact){
//        String sql = "insert into contacts_info(type_id, resume_id, contact_value) " +
//                "values(:typeId, :resumeId, :contactValue)";
//        namedParameterJdbcTemplate.update(sql,
//                new MapSqlParameterSource()
//                        .addValue("typeId", contact.getType().getId())
//                        .addValue("resumeId", contact.getResume().getId())
//                        .addValue("contactValue", contact.getValue())
//        );
//    }
//}
