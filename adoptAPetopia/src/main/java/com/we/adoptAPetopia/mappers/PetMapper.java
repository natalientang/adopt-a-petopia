package com.we.adoptAPetopia.mappers;

import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.entities.Shelter;
import com.we.adoptAPetopia.entities.Species;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetMapper implements RowMapper<Pet> {
    @Override
    public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {
        Species species = new Species();
        species.setId(rs.getInt("speciesId"));

        Shelter shelter = new Shelter();
        shelter.setId(rs.getInt("shelterId"));

        Pet pet = new Pet();
        pet.setId(rs.getInt("petId"));
        pet.setName(rs.getString("name"));
        pet.setSpecies(species);
        pet.setDescription(rs.getString("description"));
        pet.setShelter(shelter);
        return pet;
    }
}
