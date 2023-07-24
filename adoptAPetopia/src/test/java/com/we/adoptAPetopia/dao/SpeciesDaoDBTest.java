package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.entities.Shelter;
import com.we.adoptAPetopia.entities.Species;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpeciesDaoDBTest {
    @Autowired
    SpeciesDao speciesDao;
    @Autowired
    BreedDao breedDao;
    @Autowired
    PetDao petDao;
    @Autowired
    ShelterDao shelterDao;

    public SpeciesDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Breed> breeds = breedDao.getAllBreeds();
        breeds.forEach(breed -> {
            breedDao.deleteBreedById(breed.getId());
        });

        List<Pet> pets = petDao.getAllPets();
        pets.forEach(pet -> {
            petDao.deletePetById(pet.getId());
        });

        List<Species> species = speciesDao.getAllSpecies();
        species.forEach(species1 -> {
            speciesDao.deleteSpeciesById(species1.getId());
        });
    }

    @Test
    public void testAddAndGetSpeciesById() {
        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Species added = speciesDao.getSpeciesById(species.getId());
        assertEquals(species, added);
    }

    @Test
    public void testGetAllSpecies() {
        Species species1 = new Species();
        species1.setName("Dog");
        species1.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species1 = speciesDao.addSpecies(species1);

        Species species2 = new Species();
        species2.setName("Cat");
        species2.setDescription("Purr-fect companion, graceful and independent, cuddle expert.");
        species2 = speciesDao.addSpecies(species2);

        List<Species> species = speciesDao.getAllSpecies();
        assertEquals(2, species.size());
        assertTrue(species.contains(species1));
        assertTrue(species.contains(species2));
    }

    @Test
    public void testUpdateSpecies() {
        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Species updated = speciesDao.getSpeciesById(species.getId());
        assertEquals(species, updated);

        species.setName("Cat");
        speciesDao.updateSpecies(species);
        assertNotEquals(species, updated);

        updated = speciesDao.getSpeciesById(species.getId());
        assertEquals(species, updated);
    }

    @Test
    public void testDeleteSpeciesById() {
        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Breed breed = new Breed();
        breed.setName("Golden Retriever");
        breed.setDescription("Friendly, loyal, and intelligent family companion with a soft coat.");
        breed = breedDao.addBreed(breed);
        List<Breed> breeds = new ArrayList<>();
        breeds.add(breed);

        Pet pet = new Pet();
        pet.setName("Luna");
        pet.setSpecies(species);
        pet.setDescription("Energetic and playful canine companion.");
        pet.setShelter(shelter);
        pet.setBreeds(breeds);
        pet = petDao.addPet(pet);

        Species deleted = speciesDao.getSpeciesById(species.getId());
        assertEquals(species, deleted);

        speciesDao.deleteSpeciesById(species.getId());

        deleted = speciesDao.getSpeciesById(species.getId());
        assertNull(deleted);

        pet = petDao.getPetById(pet.getId());
        assertNull(pet.getSpecies());
    }
}