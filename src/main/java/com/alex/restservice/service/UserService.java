package com.alex.restservice.service;

import com.alex.restservice.exception.AuthException;
import com.alex.restservice.model.User;

import java.util.List;

public interface UserService {
    User validateUser(String userName, String password) throws AuthException;

    User registerUser(String userName, String password, String firstName, String lastName) throws AuthException;
    Integer countUsers() throws AuthException;

    List<User> getAllUsers();
}
