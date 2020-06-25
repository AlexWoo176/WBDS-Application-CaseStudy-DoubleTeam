package com.codegym.education.repository;

import com.codegym.education.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Participant, Long> {
    Participant findByUsername(String username);

    Optional<Participant> findByEmail(String email);

    Participant findByConfirmationToken(String confirmationToken);

    Optional<Participant> findByResetToken(String resetToken);

}