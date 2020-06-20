package com.codegym.education.service;

import com.codegym.education.model.Role;

public interface RoleService {
    Role findRoleByName(String roleName);
}
