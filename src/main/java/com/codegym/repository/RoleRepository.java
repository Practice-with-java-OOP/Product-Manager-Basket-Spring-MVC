package com.codegym.repository;

import com.codegym.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByRoles(String roles);
}
