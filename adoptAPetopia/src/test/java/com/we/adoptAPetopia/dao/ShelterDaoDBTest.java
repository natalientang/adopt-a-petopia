package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShelterDaoDBTest {
    @Autowired
    SpeciesDao speciesDao;
    @Autowired
    BreedDao breedDao;
    @Autowired
    PetDao petDao;
    @Autowired
    ShelterDao shelterDao;

    public ShelterDaoDBTest() {
    }

    @BeforeEach
    void setUp() {
        List<Breed> breeds = breedDao.getAllBreeds();
        breeds.forEach(breed -> {
            breedDao.deleteBreedById(breed.getId());
        });

        List<Pet> pets = petDao.getAllPets();
        pets.forEach(pet -> {
            petDao.deletePetById(pet.getId());
        });

        List<Shelter> shelters = shelterDao.getAllShelters();
        shelters.forEach(shelter -> {
            shelterDao.deleteShelterById(shelter.getId());
        });
    }

    @Test
    void testAddAndGetShelterById() {
        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Shelter added = shelterDao.getShelterById(shelter.getId());
        assertEquals(shelter, added);
    }

    @Test
    void testGetAllShelters() {
        Shelter shelter1 = new Shelter();
        shelter1.setName("Pawsitive Haven");
        shelter1.setAddress("123 Park Ave, Anytown, USA");
        shelter1.setPhone("555-111-1111");
        shelter1 = shelterDao.addShelter(shelter1);

        Shelter shelter2 = new Shelter();
        shelter2.setName("Happy Paws");
        shelter2.setAddress("4456 Oak St, Otherville, USA");
        shelter2.setPhone("555-222-2222");
        shelter2 = shelterDao.addShelter(shelter2);

        List<Shelter> shelters = shelterDao.getAllShelters();
        assertEquals(2, shelters.size());
        assertTrue(shelters.contains(shelter1));
        assertTrue(shelters.contains(shelter2));
    }

    @Test
    void testUpdateShelter() {
        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Shelter updated = shelterDao.getShelterById(shelter.getId());
        assertEquals(shelter, updated);

        shelter.setName("Haven Pawsitive");
        shelterDao.updateShelter(shelter);
        assertNotEquals(shelter, updated);

        updated = shelterDao.getShelterById(shelter.getId());
        assertEquals(shelter, updated);
    }

    @Test
    void testDeleteShelterById() {
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

        Shelter deleted = shelterDao.getShelterById(shelter.getId());
        assertEquals(shelter, deleted);

        shelterDao.deleteShelterById(shelter.getId());

        deleted = shelterDao.getShelterById(shelter.getId());
        assertNull(deleted);

        pet = petDao.getPetById(pet.getId());
        assertNull(pet.getShelter());
    }
}