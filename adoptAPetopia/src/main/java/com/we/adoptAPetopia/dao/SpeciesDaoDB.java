package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Species;
import com.we.adoptAPetopia.mappers.SpeciesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpeciesDaoDB implements SpeciesDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Species getSpeciesById(int id) {
        try {
            final String sql = "SELECT * FROM species WHERE speciesId = ?";
            return jdbc.queryForObject(sql, new SpeciesMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Species> getAllSpecies() {
        final String sql = "SELECT * FROM species";
        return jdbc.query(sql, new SpeciesMapper());
    }

    @Override
    public Species addSpecies(Species species) {
        final String sql = "INSERT INTO species(name, description) " + "VALUES(?,?)";
        jdbc.update(sql, species.getName(), species.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        species.setId(newId);
        return species;
    }

    @Override
    public void updateSpecies(Species species) {
        final String sql = "UPDATE species SET name = ?, description = ? WHERE speciesId = ?";
        jdbc.update(sql, species.getName(), species.getDescription(), species.getId());
    }

    @Override
    public void deleteSpeciesById(int id) {
        final String UPDATE_PET = "UPDATE pet SET petId = NULL WHERE petId = ?";
        jdbc.update(UPDATE_PET, id);

        final String DELETE_SPECIES = "DELETE FROM species WHERE speciesId = ?";
        jdbc.update(DELETE_SPECIES, id);
    }
}
