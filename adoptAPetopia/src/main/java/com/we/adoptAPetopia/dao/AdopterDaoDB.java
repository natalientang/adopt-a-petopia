package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Adopter;
import com.we.adoptAPetopia.mappers.AdopterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdopterDaoDB implements AdopterDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Adopter getAdopterById(int id) {
        try {
            final String sql = "SELECT * FROM adopter WHERE adopterId = ?";
            return jdbc.queryForObject(sql, new AdopterMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Adopter> getAllAdopters() {
        final String sql = "SELECT * FROM adopter";
        return jdbc.query(sql, new AdopterMapper());
    }

    @Override
    public Adopter addAdopter(Adopter adopter) {
        final String sql = "INSERT INTO adopter(name, email, phone, address) " + "VALUES(?,?,?,?)";
        jdbc.update(sql, adopter.getName(), adopter.getEmail(), adopter.getPhone(), adopter.getAddress());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        adopter.setId(newId);

        return adopter;
    }

    @Override
    public void updateAdopter(Adopter adopter) {
        final String sql = "UPDATE adopter SET name = ?, email = ?, phone = ?, address = ? WHERE adopterId = ?";
        jdbc.update(sql, adopter.getName(), adopter.getEmail(), adopter.getPhone(), adopter.getAddress(), adopter.getId());
    }

    @Override
    public void deleteAdopterById(int id) {
        final String DELETE_ADOPTION = "DELETE FROM adoption WHERE adopterId = ?";
        jdbc.update(DELETE_ADOPTION, id);

        final String DELETE_ADOPTER = "DELETE FROM adopter WHERE adopterId = ?";
        jdbc.update(DELETE_ADOPTER, id);
    }
}
