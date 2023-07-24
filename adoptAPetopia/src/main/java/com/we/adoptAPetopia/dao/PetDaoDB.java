package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.mappers.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetDaoDB implements PetDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Pet getPetById(int id) {
        try {
            final String sql = "SELECT * FROM pet WHERE petId = ?";
            return jdbc.queryForObject(sql, new PetMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Pet> getAllPets() {
        final String sql = "SELECT * FROM pet";
        return jdbc.query(sql, new PetMapper());
    }

    @Override
    public Pet addPet(Pet pet) {
        final String sql = "INSERT INTO pet(name, speciesId, description, shelterId) " + "VALUES(?,?,?,?)";
        jdbc.update(sql, pet.getName(), pet.getSpecies().getId(), pet.getDescription(), pet.getShelter().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        pet.setId(newId);
        return pet;
    }

    @Override
    public void updatePet(Pet pet) {
        final String sql = "UPDATE pet SET name = ?, speciesId = ?, description = ?, shelterId = ? WHERE petId = ?";
        jdbc.update(sql, pet.getName(), pet.getSpecies().getId(), pet.getDescription(), pet.getShelter().getId(), pet.getId());
    }

    @Override
    public void deletePetById(int id) {
        final String DELETE_PET_BREED = "DELETE FROM petBreed WHERE petId = ?";
        jdbc.update(DELETE_PET_BREED, id);

        final String DELETE_ADOPTION = "DELETE FROM adoption WHERE petId = ?";
        jdbc.update(DELETE_ADOPTION, id);

        final String DELETE_PET = "DELETE FROM pet WHERE petId = ?";
        jdbc.update(DELETE_PET, id);
    }
}
