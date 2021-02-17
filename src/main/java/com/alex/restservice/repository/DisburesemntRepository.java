package com.alex.restservice.repository;


import com.alex.restservice.exception.ApiResourceNotFoundException;
import com.alex.restservice.model.TotalInfoDisburesement;

import java.util.List;

public interface DisburesemntRepository {
    Integer insertData(Integer userId, String title,String timestamp, String description, Double disburesement) throws ApiResourceNotFoundException;

    List<TotalInfoDisburesement> fetchAllData() throws ApiResourceNotFoundException;

    List<TotalInfoDisburesement> fetchDataByUserId(Integer userId) throws ApiResourceNotFoundException;

    void removeDataByUserId(Integer userId);

    void updateData(Integer userId, TotalInfoDisburesement updateData) throws ApiResourceNotFoundException;

    TotalInfoDisburesement fetchDataByCategoryId(int categoryId);

    List<TotalInfoDisburesement> fetchIncurnsDataOneByOne();
}
