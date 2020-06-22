package com.codegym.education.service.impl;

import com.codegym.education.model.Participant;
import com.codegym.education.model.UserPrinciple;
import com.codegym.education.repository.UserRepository;
import com.codegym.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(Participant participant) {
        userRepository.save(participant);
    }

    @Override
    public Iterable<Participant> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Participant findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean checkLogin(Participant participant) {
        Iterable<Participant> users = this.findAll();
        boolean isCorrectUser = false;
        for (Participant currentParticipant : users) {
            if (currentParticipant.getUsername().equals(participant.getUsername())
                    && participant.getPassword().equals(currentParticipant.getPassword())&&
                    currentParticipant.isEnabled()) {
                isCorrectUser = true;
                break;
            }
        }
        return isCorrectUser;
    }

    @Override
    public boolean isRegister(Participant participant) {
        boolean isRegister = false;
        Iterable<Participant> users = this.findAll();
        for (Participant currentParticipant : users) {
            if (participant.getUsername().equals(currentParticipant.getUsername())||
                    participant.getEmail().equals(currentParticipant.getEmail())) {
                isRegister = true;
                break;
            }
        }
        return isRegister;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
        Participant participant = userRepository.findByUsername(username);
        if (participant == null) {
            throw new UsernameNotFoundException(username);
        }
        if(this.checkLogin(participant)){
            return UserPrinciple.build(participant);
        }
        boolean enable = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        return new org.springframework.security.core.userdetails.User(participant.getUsername(),
                participant.getPassword(),enable,accountNonExpired,credentialsNonExpired,
                accountNonLocked,null);
    }

    @Override
    public Participant findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isCorrectConfirmPassword(Participant participant) {
        boolean isCorrentConfirmPassword = false;
        if(participant.getPassword().equals(participant.getConfirmPassword())){
            isCorrentConfirmPassword = true;
        }
        return isCorrentConfirmPassword;
    }

    @Override
    public Optional<Participant> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Participant getCurrentUser() {
        Participant participant;
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        participant = this.findByUsername(userName);
        return participant;
    }

    /*Test*/
//    @Override
//    public Participant findByVerificationToken(String verificationToken) {
//        return userRepository.findByVerificationToken(verificationToken);
//    }
}