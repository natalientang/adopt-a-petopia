package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Shelter;
import com.we.adoptAPetopia.mappers.ShelterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShelterDaoDB implements ShelterDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Shelter getShelterById(int id) {
        try {
            final String sql = "SELECT * FROM shelter WHERE shelterId = ?";
            return jdbc.queryForObject(sql, new ShelterMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Shelter> getAllShelters() {
        final String sql = "SELECT * FROM shelter";
        return jdbc.query(sql, new ShelterMapper());
    }

    @Override
    public Shelter addShelter(Shelter shelter) {
        final String sql = "INSERT INTO shelter(name, address, phone) " + "VALUES(?,?,?)";
        jdbc.update(sql, shelter.getName(), shelter.getAddress(), shelter.getPhone());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        shelter.setId(newId);
        return shelter;
    }

    @Override
    public void updateShelter(Shelter shelter) {
        final String sql = "UPDATE shelter SET name = ?, address = ?, phone = ? WHERE shelterId = ?";
        jdbc.update(sql, shelter.getName(), shelter.getAddress(), shelter.getPhone(), shelter.getId());
    }

    @Override
    public void deleteShelterById(int id) {
        final String UPDATE_PET = "UPDATE pet SET shelterId = NULL WHERE shelterId = ?";
        jdbc.update(UPDATE_PET, id);

        final String DELETE_SHELTER = "DELETE FROM shelter WHERE shelterId = ?";
        jdbc.update(DELETE_SHELTER, id);
    }
}
