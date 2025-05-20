package com.example.home_work_49.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "applicant_id")
    private User applicant;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    private Double salary;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToOne(mappedBy = "resume")
    private WorkExperienceInfo experience;
    @OneToOne(mappedBy = "resume")
    private EducationInfo education;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<ContactInfo> contacts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "responded_applicants",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private Collection<Vacancy> vacancies;
}
