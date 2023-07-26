package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.*;
import com.we.adoptAPetopia.mappers.*;
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
            Pet pet = jdbc.queryForObject(GET_PET_ID, new PetMapper(), id);
            if (pet != null && pet.getShelter() != null && pet.getSpecies() != null) {
                int idShelter = pet.getShelter().getId();
                Shelter shelterPet = getShelterForPet(idShelter);
                pet.setShelter(shelterPet);

                int idSpecies = pet.getSpecies().getId();
                Species speciesPet = getSpeciesForPet(idSpecies);
                pet.setSpecies(speciesPet);
            }
            return pet;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Species getSpeciesForPet(int idSpecies) {
        try{
            return jdbc.queryForObject("SELECT * FROM species WHERE speciesId = ?", new SpeciesMapper(), idSpecies);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Shelter getShelterForPet(int idShelter) {
        try{
            return jdbc.queryForObject("SELECT * FROM shelter WHERE shelterId = ?", new ShelterMapper(), idShelter);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Adoption addAdoption(Adoption adoption) {
        try {
            final String sql = "INSERT INTO adoption(adoptionDate, petId, adopterId) " + "VALUES(?,?,?)";
            jdbc.update(sql, adoption.getDate(), adoption.getPet().getId(), adoption.getAdopter().getId());

            int lastId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            adoption.setId(lastId);

            Adopter adopter = getAdopterForAdoption(adoption.getAdopter().getId());
            Pet pet = getPetForAdoption(adoption.getPet().getId());

            adoption.setPet(pet);
            adoption.setAdopter(adopter);

            return adoption;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateAdoption(Adoption adoption) {
        final String sql = "UPDATE adoption SET adoptionDate = ?, petId = ?, adopterId = ? WHERE adoptionId = ?";
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
            final String sql = "SELECT * FROM adoption WHERE adoptionDate = ?";
            List<Adoption> adoptionList = jdbc.query(sql, new AdoptionMapper(), date);

            return setPetAdopterToAdoptionList(adoptionList);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Integer> getAdoptedPetIds() {
        final String sql = "SELECT petId FROM adoption";
        return jdbc.queryForList(sql, Integer.class);
    }
}
