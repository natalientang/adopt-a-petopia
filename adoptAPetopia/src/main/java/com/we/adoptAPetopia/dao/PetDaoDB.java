package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.entities.Shelter;
import com.we.adoptAPetopia.entities.Species;
import com.we.adoptAPetopia.mappers.BreedMapper;
import com.we.adoptAPetopia.mappers.PetMapper;
import com.we.adoptAPetopia.mappers.ShelterMapper;
import com.we.adoptAPetopia.mappers.SpeciesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

@Repository
public class PetDaoDB implements PetDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Pet getPetById(int id) {
        try {
            final String sql = "SELECT * FROM pet WHERE petId = ?";
            Pet pet = jdbc.queryForObject(sql, new PetMapper(), id);
            pet.setSpecies(getSpeciesForPet(id));
            pet.setShelter(getShelterForPet(id));
            pet.setBreeds(getBreedsForPet(id));
            return pet;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Breed> getBreedsForPet(int id) {
        final String sql = "SELECT b.* " + "FROM pet p " + "JOIN petBreed pb ON p.petId = pb.petId " + "JOIN breed b ON pb.breedId = b.breedId " + "WHERE p.petId = ?";
        return jdbc.query(sql, new BreedMapper(), id);
    }

    private Shelter getShelterForPet(int id) {
        try {
            final String sql = "SELECT s.* FROM pet p " + "JOIN shelter s ON p.shelterId = s.shelterId " + "WHERE p.petId = ?";
            return jdbc.queryForObject(sql, new ShelterMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Species getSpeciesForPet(int id) {
        try {
            final String sql = "SELECT s.* FROM pet p " + "JOIN species s ON p.speciesId = s.speciesId " + "WHERE p.petId = ?";
            return jdbc.queryForObject(sql, new SpeciesMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Pet> getAllPets() {
        try{
            final String sql = "SELECT * FROM pet";
            List<Pet> petList = jdbc.query(sql, new PetMapper());

            for(Pet pet : petList) {
                pet.setSpecies(getSpeciesForPet(pet.getId()));
                pet.setShelter(getShelterForPet(pet.getId()));
                pet.setBreeds(getBreedsForPet(pet.getId()));
            }
            return petList;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Pet addPet(Pet pet) {
        final String sql = "INSERT INTO pet(name, speciesId, description, shelterId) " + "VALUES(?,?,?,?)";
        jdbc.update(sql, pet.getName(), pet.getSpecies().getId(), pet.getDescription(), pet.getShelter().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        pet.setId(newId);
        insertPetBreed(pet);
        return pet;
    }

    @Override
    @Transactional
    public void updatePet(Pet pet) {
        final String sql = "UPDATE pet SET name = ?, speciesId = ?, description = ?, shelterId = ? WHERE petId = ?";
        jdbc.update(sql, pet.getName(), pet.getSpecies().getId(), pet.getDescription(), pet.getShelter().getId(), pet.getId());

        final String DELETE_PET_BREED = "DELETE FROM petBreed WHERE petId = ?";
        jdbc.update(DELETE_PET_BREED, pet.getId());
        insertPetBreed(pet);
    }

    private void insertPetBreed(Pet pet) {
        final String sql = "INSERT INTO petBreed(petId, breedId) " + "VALUES(?,?)";
        for(Breed breed : pet.getBreeds()) {
            jdbc.update(sql, pet.getId(), breed.getId());
        }
    }

    @Override
    @Transactional
    public void deletePetById(int id) {
        final String DELETE_PET_BREED = "DELETE FROM petBreed WHERE petId = ?";
        jdbc.update(DELETE_PET_BREED, id);

        final String DELETE_ADOPTION = "DELETE FROM adoption WHERE petId = ?";
        jdbc.update(DELETE_ADOPTION, id);

        final String DELETE_PET = "DELETE FROM pet WHERE petId = ?";
        jdbc.update(DELETE_PET, id);
    }

    @Override
    public List<Pet> getPetsByBreed(Breed breed) {
        final String sql = "SELECT DISTINCT p.* FROM pet p " + "INNER JOIN petBreed pb ON p.petId = pb.petId " + "WHERE pb.breedId = ?";
        List<Pet> pets = jdbc.query(sql, new PetMapper(), breed.getId());
        for(Pet pet : pets) {
            pet.setSpecies(getSpeciesForPet(pet.getId()));
            pet.setShelter(getShelterForPet(pet.getId()));
            pet.setBreeds(getBreedsForPet(pet.getId()));
        }
        return pets;
    }
}
