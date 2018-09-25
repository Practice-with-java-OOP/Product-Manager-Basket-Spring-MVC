package com.codegym.service.user;

import com.codegym.model.Goods;
import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.repository.UserRepository;
import com.codegym.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void save(User user) {
        Role role = roleService.findRoleByRoles("ROLE_USER");

        if (user.getRole() == null) {
            user.setRole(role);
        }
        userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
