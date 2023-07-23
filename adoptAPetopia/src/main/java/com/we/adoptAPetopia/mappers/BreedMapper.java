package com.we.adoptAPetopia.mappers;

import com.we.adoptAPetopia.entities.Breed;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BreedMapper implements RowMapper<Breed> {
    @Override
    public Breed mapRow(ResultSet rs, int rowNum) throws SQLException {
        Breed breed = new Breed();
        breed.setId(rs.getInt("breedId"));
        breed.setName(rs.getString("name"));
        breed.setDescription(rs.getString("description"));
        return breed;
    }
}
