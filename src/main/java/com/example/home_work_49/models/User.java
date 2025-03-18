package com.example.home_work_49.models;

import lombok.Getter;

public class User {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    @Getter
    private String accountType;

}
