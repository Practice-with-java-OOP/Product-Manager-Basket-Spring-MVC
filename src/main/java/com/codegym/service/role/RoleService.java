package com.codegym.service.role;

import com.codegym.model.Role;

public interface RoleService {

    Iterable<Role> finfAll();

    Role findById(Long id);

    void save(Role role);

    void remove(Long id);

    Role findRoleByRoles(String roles);
}
