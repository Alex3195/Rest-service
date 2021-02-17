package com.alex.restservice.service;

import com.alex.restservice.exception.ApiResourceNotFoundException;
import com.alex.restservice.model.TotalInfoDisburesement;
import com.alex.restservice.repository.DisburesemntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DisburesementServiceImpl implements DisburesementService {

    @Autowired
    DisburesemntRepository disburesemntRepository;

    @Override
    public List<TotalInfoDisburesement> fetchAllDataByUserId(Integer userId) {
        return disburesemntRepository.fetchDataByUserId(userId);
    }

    @Override
    public List<TotalInfoDisburesement> fetchAllData() throws ApiResourceNotFoundException {
        return disburesemntRepository.fetchAllData();
    }

    @Override
    public TotalInfoDisburesement addData(Integer userId, String title, String timestamp, String description, Double disbursement) throws ApiResourceNotFoundException {
        int categoryId = disburesemntRepository.insertData(userId, title, timestamp, description, disbursement);
        return disburesemntRepository.fetchDataByCategoryId(categoryId);
    }

    @Override
    public void removeDataByUserId(Integer userId) {
        disburesemntRepository.removeDataByUserId(userId);
    }

    @Override
    public void updateData(Integer userId, TotalInfoDisburesement updateData) throws ApiResourceNotFoundException {
        disburesemntRepository.updateData(userId, updateData);
    }

    @Override
    public List<TotalInfoDisburesement> fetchIncursOneByOne() {
        return disburesemntRepository.fetchIncurnsDataOneByOne();
    }


}
