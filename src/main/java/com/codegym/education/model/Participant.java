package com.codegym.education.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name")
    @NotEmpty(message = "Please provide your full name")
    private String fullName;

    @Column(name = "user_name", nullable = false, unique = true)
    @NotEmpty(message = "Please provide your user name")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;

    @Column(name = "password")
//    @Transient
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    public Participant() {
    }

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "reset_token")
    private String resetToken;

    @ManyToOne
    private Role role;

}