package com.alex.restservice.repository;

import com.alex.restservice.exception.AuthException;
import com.alex.restservice.model.User;

import java.util.List;

public interface UserRepository {
    Integer create(String userName,String password,String firstName,String lastName) throws AuthException;
    User findByUserName(String userName) throws AuthException;
    Integer countByUser(String userName);
    Integer countUser();
    User findById(Integer id);

    List<User> getAllUsers();
}
