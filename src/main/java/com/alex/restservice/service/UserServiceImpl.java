package com.alex.restservice.service;

import com.alex.restservice.exception.AuthException;
import com.alex.restservice.model.User;
import com.alex.restservice.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String userName, String password) throws AuthException {
        try {
            User user = userRepository.findByUserName(userName);
            if (!BCrypt.checkpw(password, user.getPassword()))
                throw new AuthException("Invalid username or password");
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new AuthException("Invalid username or password");
        }
    }

    @Override
    public User registerUser(String userName, String password, String firstName, String lastName) throws AuthException {
        Integer count = userRepository.countByUser(userName);
        if (count > 0) {
            throw new AuthException("User already in use");
        }
        Integer userId = userRepository.create(userName, password, firstName, lastName);

        return userRepository.findById(userId);
    }

    @Override
    public Integer countUsers() throws AuthException {
        return userRepository.countUser();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
