package com.codegym.education.repository;

import com.codegym.education.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Participant, Long> {
    Participant findByUserName(String username);

    Optional<Participant> findByEmail(String email);

    Participant findByConfirmationToken(String confirmationToken);

    Optional<Participant> findByResetToken(String resetToken);

}