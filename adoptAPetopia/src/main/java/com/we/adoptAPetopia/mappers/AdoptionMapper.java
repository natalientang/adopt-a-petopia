package com.we.adoptAPetopia.mappers;

import com.we.adoptAPetopia.entities.Adopter;
import com.we.adoptAPetopia.entities.Adoption;
import com.we.adoptAPetopia.entities.Pet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdoptionMapper implements RowMapper<Adoption> {
    @Override
    public Adoption mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pet pet = new Pet();
        pet.setId(rs.getInt("petId"));

        Adopter adopter = new Adopter();
        adopter.setId(rs.getInt("adopterId"));

        Adoption adoption = new Adoption();
        adoption.setId(rs.getInt("adoptionId"));
        adoption.setDate(rs.getObject("adoptionDate", LocalDateTime.class));
        adoption.setPet(pet);
        adoption.setAdopter(adopter);
        return adoption;
    }
}
