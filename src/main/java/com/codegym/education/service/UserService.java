package com.codegym.education.service;

import com.codegym.education.model.Participant;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(Participant user);

    Iterable<Participant> findAll();

    Participant findByUsername(String username);

    boolean checkLogin(Participant user);

    boolean isRegister(Participant user);

    Optional<Participant> findUserByEmail(String email);

    Optional<Participant> findById(Long id);

    Participant getCurrentUser();

    Participant findByConfirmationToken(String confirmationToken);

    Optional<Participant> findUserByResetToken(String resetToken);
}
