package com.we.adoptAPetopia.mappers;

import com.we.adoptAPetopia.entities.Shelter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShelterMapper implements RowMapper<Shelter> {
    @Override
    public Shelter mapRow(ResultSet rs, int rowNum) throws SQLException {
        Shelter shelter = new Shelter();
        shelter.setId(rs.getInt("shelterId"));
        shelter.setName(rs.getString("name"));
        shelter.setAddress(rs.getString("address"));
        shelter.setPhone(rs.getString("phone"));
        return shelter;
    }
}
