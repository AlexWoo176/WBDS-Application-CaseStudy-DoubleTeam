package com.codegym.education.service.impl;

import com.codegym.education.model.Participant;
import com.codegym.education.repository.UserRepository;
import com.codegym.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(Participant user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<Participant> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Participant findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public boolean checkLogin(Participant user) {
        Iterable<Participant> users = this.findAll();
        boolean isCorrectUser = false;
        for (Participant currentUser : users) {
            if (currentUser.getUserName().equals(user.getUserName()) &&
                    user.getPassword().equals(currentUser.getPassword()) && currentUser.isEnabled()) {
                isCorrectUser = true;
                break;
            }
        }
        return isCorrectUser;
    }

    @Override
    public boolean isRegister(Participant user) {
        boolean isRegister = false;
        Iterable<Participant> users = this.findAll();
        for (Participant currentUser : users) {
            if (user.getUserName().equals(currentUser.getUserName()) ||
                    user.getEmail().equals(currentUser.getEmail())) {
                isRegister = true;
                break;
            }
        }
        return isRegister;
    }

    @Override
    public Optional<Participant> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Participant> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Participant getCurrentUser() {
        Participant user;
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        user = this.findByUsername(userName);
        return user;
    }

    @Override
    public Participant findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    @Override
    public Optional<Participant> findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Participant user = userRepository.findByUserName(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(user.getRole());

        UserDetails userDetails = new User(user.getUserName(), user.getPassword(), authorities);
        return userDetails;
    }
}
