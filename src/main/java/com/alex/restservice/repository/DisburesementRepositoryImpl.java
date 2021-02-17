package com.alex.restservice.repository;

import com.alex.restservice.exception.ApiResourceNotFoundException;
import com.alex.restservice.model.TotalInfoDisburesement;
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
public class DisburesementRepositoryImpl implements DisburesemntRepository {
    public static final String SQL_INSERT_DATA = "insert into disbursement_on_travel(user_id,title,time,descriptions,disbursement) value(?,?,?,?,?)";
    public static final String SQL_FETCH_ALL_DATA = "select id,user_id,title,time,descriptions,disbursement from disbursement_on_travel";
    public static final String SQL_FETCH_DATA_BY_USER_ID = "select id,user_id,title,time,descriptions,disbursement from disbursement_on_travel where user_id=?";
    public static final String SQL_FETCH_DATA_BY_ID = "select id,user_id,title,time,descriptions,disbursement from disbursement_on_travel where id=?";
    public static final String SQL_UPDATE_BY_USER_ID = "update disbursement_on_travel set title=?, time = ?, descriptions=?, disbursement=? where user_id=?";
    public static final String SQL_DELETE_BY_USER_ID = "delete from disbursement_on_travel where user_id=?";
    public static final String SQL_FETCH_INCURNS_ONE_BY_ONE = "select user_id, sum(disbursement) as incurns from disbursement_on_travel group by user_id";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer insertData(Integer userId, String title, String timestamp, String description, Double disburesement) throws ApiResourceNotFoundException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT_DATA, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, timestamp);
                ps.setString(4, description);
                ps.setDouble(5, disburesement);
                return ps;
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            System.out.println(e);
            throw new ApiResourceNotFoundException("Invalid data");
        }
    }

    @Override
    public List<TotalInfoDisburesement> fetchAllData() throws ApiResourceNotFoundException {
        return jdbcTemplate.query(SQL_FETCH_ALL_DATA, dataRowMapper);
    }

    @Override
    public List<TotalInfoDisburesement> fetchDataByUserId(Integer userId) throws ApiResourceNotFoundException {
        return jdbcTemplate.query(SQL_FETCH_DATA_BY_USER_ID, new Object[]{userId}, dataRowMapper);
    }

    @Override
    public void removeDataByUserId(Integer userId) {
        jdbcTemplate.update(SQL_DELETE_BY_USER_ID, new Object[]{userId});
    }

    @Override
    public void updateData(Integer userId, TotalInfoDisburesement updateData) throws ApiResourceNotFoundException {
        try {
            jdbcTemplate.update(SQL_UPDATE_BY_USER_ID, new Object[]{updateData.getTitle(), updateData.getTime(), updateData.getDescription(), updateData.getDisburesement(), updateData.getUserId()});
        } catch (Exception e) {
            System.out.println(e);
            throw new ApiResourceNotFoundException("not updated");
        }
    }

    @Override
    public TotalInfoDisburesement fetchDataByCategoryId(int categoryId) {
        return jdbcTemplate.queryForObject(SQL_FETCH_DATA_BY_ID, new Object[]{categoryId}, dataRowMapper);
    }

    @Override
    public List<TotalInfoDisburesement> fetchIncurnsDataOneByOne() {
        return jdbcTemplate.query(SQL_FETCH_INCURNS_ONE_BY_ONE, dataRowMapperOneByOne);
    }

    private RowMapper<TotalInfoDisburesement> dataRowMapper = (((rs, rowNum) -> {
        return new TotalInfoDisburesement(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("time"),
                rs.getString("descriptions"),
                rs.getDouble("disbursement"),
                rs.getInt("user_id"),
                0.0
        );
    }));

    private RowMapper<TotalInfoDisburesement> dataRowMapperOneByOne = (((rs, rowNum) -> {
        return new TotalInfoDisburesement(
                rs.getDouble("incurns"),
                rs.getInt("user_id")
        );
    }));
}
