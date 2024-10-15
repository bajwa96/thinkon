package com.thinkon.service;

import java.util.List;

import com.thinkon.model.User;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User userDetails);
    boolean deleteUser(Long id);
}
