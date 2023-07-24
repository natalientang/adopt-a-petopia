package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Adoption;
import com.we.adoptAPetopia.mappers.AdoptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AdoptionDaoDB implements AdoptionDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Adoption getAdoptionById(int id) {
        try {
            final String sql = "SELECT * FROM adoption WHERE adoptionId = ?";
            return jdbc.queryForObject(sql, new AdoptionMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Adoption> getAllAdoptions() {
        final String sql = "SELECT * FROM adoption";
        return jdbc.query(sql, new AdoptionMapper());
    }

    @Override
    @Transactional
    public Adoption addAdoption(Adoption adoption) {
        final String sql = "INSERT INTO adoption(date, petId, adopterId) " + "VALUES(?,?,?)";
        jdbc.update(sql, adoption.getDate(), adoption.getPet().getId(), adoption.getAdopter().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        adoption.setId(newId);
        return adoption;
    }

    @Override
    public void updateAdoption(Adoption adoption) {
        final String sql = "UPDATE adoption SET date = ?, petId = ?, adopterId = ? WHERE adoptionId = ?";
        jdbc.update(sql, adoption.getDate(), adoption.getPet().getId(), adoption.getAdopter().getId(), adoption.getId());
    }

    @Override
    public void deleteAdoptionById(int id) {
        final String sql = "DELETE FROM adoption WHERE adoptionId = ?";
        jdbc.update(sql, id);
    }
}
