package com.codegym.education.service;

import com.codegym.education.model.Participant;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(Participant participant);

    Iterable<Participant> findAll();

    Participant findByUsername(String username);

    boolean checkLogin(Participant participant);

    boolean isRegister(Participant participant);

    Participant findByEmail(String email);

    boolean isCorrectConfirmPassword(Participant participant);

    Optional<Participant> findById(Long id);

    Participant getCurrentUser();

    /*Test*/
//    Participant findByVerificationToken(String verificationToken);
}
