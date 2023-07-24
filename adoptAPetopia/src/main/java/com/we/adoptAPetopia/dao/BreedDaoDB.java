package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.mappers.BreedMapper;
import com.we.adoptAPetopia.mappers.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BreedDaoDB implements BreedDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Breed getBreedById(int id) {
        try {
            final String sql = "SELECT * FROM breed WHERE breedId = ?";
            Breed breed = jdbc.queryForObject(sql, new BreedMapper(), id);
            breed.setPets(getPetsByBreed(breed));
            return breed;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Pet> getPetsByBreed(Breed breed) {
        final String sql = "SELECT DISTINCT p.* FROM pet p " + "INNER JOIN petBreed pb ON p.petId = pb.petId " + "WHERE breedId = ?";
        return jdbc.query(sql, new PetMapper(), breed.getId());
    }

    @Override
    public List<Breed> getAllBreeds() {
        final String sql = "SELECT * FROM breed";
        List<Breed> breeds = jdbc.query(sql, new BreedMapper());
        for (Breed breed : breeds) {
            breed.setPets(getPetsByBreed(breed));
        }
        return breeds;
    }

    @Override
    @Transactional
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
        jdbc.update(sql, breed.getName(), breed.getDescription(), breed.getId());
    }

    @Override
    @Transactional
    public void deleteBreedById(int id) {
        final String DELETE_PET_BREED = "DELETE FROM petBreed WHERE breedId = ?";
        jdbc.update(DELETE_PET_BREED, id);

        final String DELETE_BREED = "DELETE FROM breed WHERE breedId = ?";
        jdbc.update(DELETE_BREED, id);
    }

    @Override
    public List<Breed> getBreedsByPet(Pet pet) {
        final String sql = "SELECT b.* FROM breed b " + "INNER JOIN petBreed pb ON b.breedId = pb.breedId " + "WHERE pb.petId = ?";

        List<Breed> breeds = jdbc.query(sql, new BreedMapper(), pet.getId());
        return breeds;
    }


}
