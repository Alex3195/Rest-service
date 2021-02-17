package com.alex.restservice.repository;

import com.alex.restservice.exception.AuthException;
import com.alex.restservice.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_CREATE = "insert into user(user_name,password,first_name,last_name) value (?,?,?,?)";
    private static final String SQL_COUNT_BY_USERNAME = "select count(*) from user where user_name=?";
    private static final String SQL_COUNT_USERS = "select count(*) from user";
    private static final String SQL_FIND_BY_USERID = "select id,user_name,password,first_name,last_name from user where id=?";
    private static final String SQL_FIND_BY_USERNAME_AND_PASSWORD = "select id,user_name,password,first_name,last_name from user where user_name=?";
    private static final String SQL_FETCH_ALL_USERS = "select id,user_name,password,first_name,last_name from user";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String userName, String password, String firstName, String lastName) throws AuthException {
        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, userName);
                ps.setString(2, hashPass);
                ps.setString(3, firstName);
                ps.setString(4, lastName);
                return ps;
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            throw new AuthException("Invalid details. Failed create account");
        }
    }

    @Override
    public User findByUserName(String userName) throws AuthException {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME_AND_PASSWORD, new Object[]{userName}, userRowMapper);
    }

    @Override
    public Integer countByUser(String userName) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_USERNAME, new Object[]{userName}, Integer.class);
    }

    @Override
    public Integer countUser() {
        return jdbcTemplate.queryForObject(SQL_COUNT_USERS, Integer.class);
    }

    @Override
    public User findById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_USERID, new Object[]{id}, userRowMapper);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_FETCH_ALL_USERS, userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(
                rs.getInt("id"),
                rs.getString("user_name"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"));
    });
}
