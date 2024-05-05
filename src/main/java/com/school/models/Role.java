package com.school.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Role {
    @Id
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
