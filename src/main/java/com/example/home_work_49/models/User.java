package com.example.home_work_49.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    private String accountType;
    private Boolean enabled;
    private Long roleId;

}
