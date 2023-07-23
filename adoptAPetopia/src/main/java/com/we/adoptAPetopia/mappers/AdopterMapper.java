package com.we.adoptAPetopia.mappers;

import com.we.adoptAPetopia.entities.Adopter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdopterMapper implements RowMapper<Adopter> {
    @Override
    public Adopter mapRow(ResultSet rs, int rowNum) throws SQLException {
        Adopter adopter = new Adopter();
        adopter.setId(rs.getInt("adopterId"));
        adopter.setName(rs.getString("name"));
        adopter.setEmail(rs.getString("email"));
        adopter.setPhone(rs.getString("phone"));
        adopter.setAddress(rs.getString("address"));
        return adopter;
    }
}
