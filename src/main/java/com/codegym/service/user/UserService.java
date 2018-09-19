package com.codegym.service.user;

import com.codegym.model.User;

public interface UserService {

    Iterable<User> findAll();

    User findById(Long id);

    void save(User user);

    void remove(Long id);

    User findUserByEmail(String email);
}
