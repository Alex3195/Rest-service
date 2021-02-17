package com.alex.restservice.controller;

import com.alex.restservice.model.TotalInfoDisburesement;
import com.alex.restservice.service.DisburesementService;
import com.alex.restservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryConltroller {
    @Autowired
    DisburesementService disburesementService;
    @Autowired
    UserService userService;

    @GetMapping("")
    public String getAllCategories(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        return "Authenticated! userId: " + userId;
    }

    @PostMapping("/add")
    public ResponseEntity<TotalInfoDisburesement> addData(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        int userId = (Integer) request.getAttribute("userId");
        String title = map.get("title").toString();
        String description = map.get("description").toString();
        Double disbursement = Double.parseDouble(map.get("disbursement").toString());
        System.out.println(userId + ", " + title + ", " + description + ", " + disbursement);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(format);
        TotalInfoDisburesement disburesement = disburesementService.addData(userId, title, timestamp, description, disbursement);
        return new ResponseEntity(disburesement, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateData(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        int userId = (Integer) map.get("userId");
        String title = map.get("title").toString();
        String description = map.get("description").toString();
        Double disbursement = Double.parseDouble(map.get("disbursement").toString());
        System.out.println(userId + ", " + title + ", " + description + ", " + disbursement);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(format);
        TotalInfoDisburesement obj = new TotalInfoDisburesement(title, timestamp, description, disbursement, userId,0.0);
        disburesementService.updateData(userId, obj);
        Map<String, String> mapRes = new HashMap();
        mapRes.put("message", "updated successfully!!!");
        request.setAttribute("userId", userId);
        return new ResponseEntity(mapRes, HttpStatus.OK);
    }

    @GetMapping("/fetch_user_data")
    public ResponseEntity<Map<String, Object>> fetchData(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        double totalIncurns = 0;
        List<TotalInfoDisburesement> list = disburesementService.fetchAllDataByUserId(userId);
        for (TotalInfoDisburesement d : list) {
            totalIncurns = totalIncurns + d.getDisburesement();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("totalSum", totalIncurns);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/fetch_data_balans")
    public ResponseEntity<Map<String, Object>> fetchDataBalans(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        int userCount = userService.countUsers();
        List<TotalInfoDisburesement> list = disburesementService.fetchAllData();
        List<TotalInfoDisburesement> listUserData = disburesementService.fetchAllDataByUserId(userId);
        double totalSum = 0;
        double userTotalSum = 0;
        for (TotalInfoDisburesement d : list) {
            totalSum = totalSum + d.getDisburesement();
        }
        for (TotalInfoDisburesement u : listUserData) {
            userTotalSum = userTotalSum + u.getDisburesement();
        }
        double onePersonIncurs = totalSum / userCount;
        List<TotalInfoDisburesement> fetchIncursOneByOne = disburesementService.fetchIncursOneByOne();

        for (int i = 0; i < fetchIncursOneByOne.size(); i++) {
            fetchIncursOneByOne.get(i).setIncurns(fetchIncursOneByOne.get(i).getDisburesement() - onePersonIncurs);
        }
        Map<String, Object> results = new HashMap<>();
        results.put("totalSum", totalSum);
        results.put("usersIncurns", userTotalSum);
        results.put("infoIncurnsOfUsers", fetchIncursOneByOne);
        return new ResponseEntity(results, HttpStatus.OK);
    }
}
