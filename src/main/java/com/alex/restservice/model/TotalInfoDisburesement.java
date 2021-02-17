package com.alex.restservice.model;

public class TotalInfoDisburesement {
    Integer id;
    String title;
    String description;
    String time;
    Double disburesement;
    Double incurns;
    Integer userId;

    public TotalInfoDisburesement(Double disburesement, Integer userId) {
        this.disburesement = disburesement;
        this.userId = userId;
    }

    public TotalInfoDisburesement(String title, String time, String description, Double disburesement, Integer userId, Double incurns) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.disburesement = disburesement;
        this.userId = userId;
        this.incurns = incurns;
    }

    public TotalInfoDisburesement(Integer id, String title, String time, String description, Double disburesement, Integer userId, Double incurns) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.description = description;
        this.disburesement = disburesement;
        this.userId = userId;
        this.incurns = incurns;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getDisburesement() {
        return disburesement;
    }

    public void setDisburesement(Double disburesement) {
        this.disburesement = disburesement;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getIncurns() {
        return incurns;
    }

    public void setIncurns(Double incurns) {
        this.incurns = incurns;
    }
}
