package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.mappers.BreedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BreedDaoDB implements BreedDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Breed getBreedById(int id) {
        try {
            final String sql = "SELECT * FROM breed WHERE breedId = ?";
            return jdbc.queryForObject(sql, new BreedMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Breed> getAllBreeds() {
        final String sql = "SELECT * FROM breed";
        return jdbc.query(sql, new BreedMapper());
    }

    @Override
    public Breed addBreed(Breed breed) {
        final String sql = "INSERT INTO breed(name, description) " + "VALUES(?,?)";
        jdbc.update(sql, breed.getName(), breed.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        breed.setId(newId);
        return breed;
    }

    @Override
    public void updateBreed(Breed breed) {
        final String sql = "UPDATE breed SET name = ?, description = ? WHERE breedId = ?";
        jdbc.update(sql, breed.getName(), breed.getDescription());
    }

    @Override
    public void deleteBreedById(int id) {
        final String DELETE_PET_BREED = "DELETE FROM petBreed WHERE breedId = ?";
        jdbc.update(DELETE_PET_BREED, id);

        final String DELETE_BREED = "DELETE FROM breed WHERE breedId = ?";
        jdbc.update(DELETE_BREED, id);
    }

    @Override
    public List<Breed> getBreedsByPet(Pet pet) {
        final String sql = "SELECT b.* FROM breed b " + "INNER JOIN petBreed pb ON l.breedId = pb.breedId " + "WHERE pb.petId = ?";

        List<Breed> breeds = jdbc.query(sql, new BreedMapper(), pet.getId());
        return breeds;
    }
}
