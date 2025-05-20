package com.example.home_work_49.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "responded_applicants")
public class RespondedApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "resume_id")
    private Long resumeId;
    @Column(name= "vacancy_id")
    private Long vacancyId;
    private boolean confirmation;
}