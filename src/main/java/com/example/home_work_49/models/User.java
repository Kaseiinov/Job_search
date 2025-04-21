package com.example.home_work_49.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String avatar;
    @Column(name = "account_type")
    private String accountType;
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant")
    private List<Resume> resumes = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Vacancy> vacancies = new ArrayList<>();
    @OneToOne(mappedBy = "user")
    private UserImage userImage;


}
