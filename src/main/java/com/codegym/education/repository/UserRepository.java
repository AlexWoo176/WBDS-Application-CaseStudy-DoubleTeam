package com.codegym.education.repository;

import com.codegym.education.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Participant, Long> {
    Participant findByUsername(String username);

    Participant findByEmail(String email);
}