package com.codegym.education.service.impl;

import com.codegym.education.model.Role;
import com.codegym.education.repository.RoleRepository;
import com.codegym.education.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
