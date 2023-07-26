package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Adopter;
import com.we.adoptAPetopia.entities.Adoption;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.mappers.AdopterMapper;
import com.we.adoptAPetopia.mappers.AdoptionMapper;
import com.we.adoptAPetopia.mappers.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        List<Adoption> adoptionList = jdbc.query(sql, new AdoptionMapper());
        return setPetAdopterToAdoptionList(adoptionList);
    }

    private List<Adoption> setPetAdopterToAdoptionList(List<Adoption> adoptionList) {
        adoptionList.forEach(adoption -> {
            adoption.setPet(getPetForAdoption(adoption.getPet().getId()));
            adoption.setAdopter(getAdopterForAdoption(adoption.getAdopter().getId()));
        });
        return adoptionList;
    }

    private Adopter getAdopterForAdoption(int id) {
        try {
            final String GET_ADOPTER_ID = "SELECT * FROM adopter WHERE adopterId = ?";
            return jdbc.queryForObject(GET_ADOPTER_ID, new AdopterMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Pet getPetForAdoption(int id) {
        try {
            final String GET_PET_ID = "SELECT * FROM pet WHERE petId = ?";
            return jdbc.queryForObject(GET_PET_ID, new PetMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
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

    @Override
    public List<Adoption> getAdoptionsByDate(LocalDateTime date) {
        try {
            final String sql = "SELECT * FROM adoption WHERE date = ?";
            List<Adoption> adoptionList = jdbc.query(sql, new AdoptionMapper(), date);

            return setPetAdopterToAdoptionList(adoptionList);
        } catch (DataAccessException ex) {
            return null;
        }
    }
}
