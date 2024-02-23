package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(Integer id);
    void deleteUser(Integer id);
    void updateUser(User user);

}
