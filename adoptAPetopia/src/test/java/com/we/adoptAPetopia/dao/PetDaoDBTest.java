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
class PetDaoDBTest {
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

    LocalDateTime date = LocalDateTime.now();

    public PetDaoDBTest() {
    }

    @BeforeEach
    void setUp() {
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
    void testAddAndGetPetById() {
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
        pet = petDao.addPet(pet);

        Pet added = petDao.getPetById(pet.getId());
        assertEquals(pet, added);
    }

    @Test
    void testGetAllPets() {
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

        Pet pet1 = new Pet();
        pet1.setName("Luna");
        pet1.setSpecies(species);
        pet1.setDescription("Energetic and playful canine companion.");
        pet1.setShelter(shelter);
        pet1.setBreeds(breeds);
        petDao.addPet(pet1);

        Pet pet2 = new Pet();
        pet2.setName("Simba");
        pet2.setSpecies(species);
        pet2.setDescription("Regal and affectionate feline friend.");
        pet2.setShelter(shelter);
        pet2.setBreeds(breeds);
        petDao.addPet(pet2);

        List<Pet> pets = petDao.getAllPets();

        assertEquals(2, pets.size());
        assertTrue(pets.contains(pet1));
        assertTrue(pets.contains(pet2));
    }

    @Test
    void testUpdatePet() {
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

        Pet updated = petDao.getPetById(pet.getId());
        assertEquals(pet, updated);

        pet.setDescription("Playful and energetic.");
        petDao.updatePet(pet);
        assertNotEquals(pet, updated);

        updated = petDao.getPetById(pet.getId());
        assertEquals(pet, updated);
    }

    @Test
    void testDeletePetById() {
        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Adopter adopter = new Adopter();
        adopter.setName("Isabella Johnson");
        adopter.setEmail("isabella123@example.com");
        adopter.setPhone("555-123-4567");
        adopter.setAddress("123 Main Street, Anytown, USA");
        adopter = adopterDao.addAdopter(adopter);

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

        Adoption adoption = new Adoption();
        adoption.setDate(date);
        adoption.setPet(pet);
        adoption.setAdopter(adopter);
        adoptionDao.addAdoption(adoption);

        Pet deleted = petDao.getPetById(pet.getId());
        assertEquals(pet, deleted);

        petDao.deletePetById(pet.getId());

        deleted = petDao.getPetById(pet.getId());
        assertNull(deleted);
    }
}