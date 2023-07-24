package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreedDaoDBTest {
    @Autowired
    AdopterDao adopterDao;
    @Autowired
    AdoptionDao adoptionDao;
    @Autowired
    BreedDao breedDao;
    @Autowired
    PetDao petDao;
    @Autowired
    ShelterDao shelterDao;
    @Autowired
    SpeciesDao speciesDao;

    public BreedDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Adopter> adopters = adopterDao.getAllAdopters();
        adopters.forEach(adopter -> {
            adopterDao.deleteAdopterById(adopter.getId());
        });

        List<Adoption> adoptions = adoptionDao.getAllAdoptions();
        adoptions.forEach(adoption -> {
            adoptionDao.deleteAdoptionById(adoption.getId());
        });

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

        List<Species> species = speciesDao.getAllSpecies();
        species.forEach(species1 -> {
            speciesDao.deleteSpeciesById(species1.getId());
        });
    }

    @Test
    public void testAddAndGetBreedById() {
        Breed breed = new Breed();
        breed.setName("Golden Retriever");
        breed.setDescription("Friendly, loyal, and intelligent family companion with a soft coat.");
        breed.setPets(new ArrayList<>());
        breed = breedDao.addBreed(breed);

        Breed added = breedDao.getBreedById(breed.getId());
        assertEquals(breed, added);
    }

    @Test
    public void testGetAllBreeds() {
        Breed breed1 = new Breed();
        breed1.setName("Golden Retriever");
        breed1.setDescription("Friendly, loyal, and intelligent family companion with a soft coat.");
        breed1.setPets(new ArrayList<>());
        breed1 = breedDao.addBreed(breed1);

        Breed breed2 = new Breed();
        breed2.setName("Labrador");
        breed2.setDescription("Playful, energetic, and affectionate family dog with a friendly nature.");
        breed2.setPets(new ArrayList<>());
        breed2 = breedDao.addBreed(breed2);

        List<Breed> breeds = breedDao.getAllBreeds();

        assertEquals(2, breeds.size());
        assertTrue(breeds.contains(breed1));
        assertTrue(breeds.contains(breed2));
    }

    @Test
    public void testUpdateBreed() {
        Breed breed = new Breed();
        breed.setName("Golden Retriever");
        breed.setDescription("Friendly, loyal, and intelligent family companion with a soft coat.");
        breed.setPets(new ArrayList<>());
        breed = breedDao.addBreed(breed);

        Breed updated = breedDao.getBreedById(breed.getId());
        assertEquals(breed, updated);

        breed.setDescription("A big golden ball of fluffiness.");
        breedDao.updateBreed(breed);
        assertNotEquals(breed, updated);

        updated = breedDao.getBreedById(breed.getId());
        assertEquals(breed, updated);
    }

    @Test
    public void testDeleteBreedById() {
        Breed breed = new Breed();
        breed.setName("Golden Retriever");
        breed.setDescription("Friendly, loyal, and intelligent family companion with a soft coat.");
        breed = breedDao.addBreed(breed);
        List<Breed> breeds = new ArrayList<>();
        breeds.add(breed);

        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Pet pet = new Pet();
        pet.setName("Luna");
        pet.setSpecies(species);
        pet.setDescription("Energetic and playful canine companion.");
        pet.setShelter(shelter);
        pet.setBreeds(breeds);
        petDao.addPet(pet);

        breedDao.deleteBreedById(breed.getId());

        Breed deleted = breedDao.getBreedById(breed.getId());
        assertNull(deleted);
    }

    @Test
    public void testGetBreedsByPet() {
        Breed breed1 = new Breed();
        breed1.setName("Golden Retriever");
        breed1.setDescription("Friendly, loyal, and intelligent family companion with a soft coat.");
        breed1 = breedDao.addBreed(breed1);

        Breed breed2 = new Breed();
        breed2.setName("Labrador");
        breed2.setDescription("Playful, energetic, and affectionate family dog with a friendly nature.");
        breed2 = breedDao.addBreed(breed2);

        List<Breed> breeds = new ArrayList<>();
        breeds.add(breed1);
        breeds.add(breed2);

        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Pet pet = new Pet();
        pet.setName("Luna");
        pet.setSpecies(species);
        pet.setDescription("Energetic and playful canine companion.");
        pet.setShelter(shelter);
        pet.setBreeds(breeds);
        petDao.addPet(pet);

        List<Breed> petBreeds = breedDao.getBreedsByPet(pet);
        assertEquals(2, petBreeds.size());
        assertEquals("Golden Retriever", petBreeds.get(0).getName());
        assertEquals("Labrador", petBreeds.get(1).getName());
    }
}