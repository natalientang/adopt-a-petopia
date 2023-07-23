package com.we.adoptAPetopia.mappers;

import com.we.adoptAPetopia.entities.Species;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeciesMapper implements RowMapper<Species> {
    @Override
    public Species mapRow(ResultSet rs, int rowNum) throws SQLException {
        Species species = new Species();
        species.setId(rs.getInt("speciesId"));
        species.setName(rs.getString("name"));
        species.setDescription(rs.getString("description"));
        return species;
    }
}
