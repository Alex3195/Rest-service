package com.alex.restservice.controller;

import com.alex.restservice.Constants;
import com.alex.restservice.model.User;
import com.alex.restservice.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map> reggisterUser(@RequestBody Map<String, Object> useraMap) {
        String userName = useraMap.get("userName").toString();
        String firstName = useraMap.get("firstName").toString();
        String lastName = useraMap.get("lastName").toString();
        String password = useraMap.get("password").toString();
        User user = userService.registerUser(userName, password, firstName, lastName);
        return new ResponseEntity(generateJWTToen(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> useraMap) {
        String userName = useraMap.get("userName").toString();
        String password = useraMap.get("password").toString();
        User user = userService.validateUser(userName, password);
        return new ResponseEntity(generateJWTToen(user), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Map<String, Object>> getAllUsers(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        List<User> users = userService.getAllUsers();
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        System.out.println(userId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    private Map<String, String> generateJWTToen(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDATE))
                .claim("userId", user.getId())
                .claim("userName", user.getUserName())
                .claim("fistName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return result;
    }
}
