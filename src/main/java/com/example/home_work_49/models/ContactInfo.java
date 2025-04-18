package com.example.home_work_49.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "contacts_info")
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "type_id")
    private ContactType type;
    @ManyToOne()
    @JoinColumn(name = "resume_id")
    private Resume resume;
    @Column(name = "contact_value")
    private String value;
}
