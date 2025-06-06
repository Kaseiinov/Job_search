package com.example.home_work_49.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "contact_types")
public class ContactType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<ContactInfo> contacts;
}