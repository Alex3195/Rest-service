package com.alex.restservice.service;

import com.alex.restservice.exception.ApiResourceNotFoundException;
import com.alex.restservice.model.TotalInfoDisburesement;

import java.util.List;

public interface DisburesementService {
    List<TotalInfoDisburesement> fetchAllDataByUserId(Integer userId);

    List<TotalInfoDisburesement> fetchAllData() throws ApiResourceNotFoundException;

    TotalInfoDisburesement addData(Integer userId, String title,String timestamp, String description, Double disbursement) throws ApiResourceNotFoundException;

    void removeDataByUserId(Integer userId);

    void updateData(Integer userId, TotalInfoDisburesement updateData) throws ApiResourceNotFoundException;

    List<TotalInfoDisburesement> fetchIncursOneByOne();
}
