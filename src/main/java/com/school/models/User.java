package com.school.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class User {
    @Id
    private int id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "first_name", nullable = false, unique = true)
    private String firstName;
    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
