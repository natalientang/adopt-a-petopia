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
class AdoptionDaoDBTest {
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

    public AdoptionDaoDBTest() {
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
    void testAddAndGetAdoptionById() {
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
        adoption = adoptionDao.addAdoption(adoption);

        Adoption added = adoptionDao.getAdoptionById(adoption.getId());
    }

    @Test
    void testGetAllAdoptions() {
        Species species = new Species();
        species.setName("Dog");
        species.setDescription("Loyal tail-wagger, always by your side, spreading joy.");
        species = speciesDao.addSpecies(species);

        Shelter shelter = new Shelter();
        shelter.setName("Pawsitive Haven");
        shelter.setAddress("123 Park Ave, Anytown, USA");
        shelter.setPhone("555-111-1111");
        shelter = shelterDao.addShelter(shelter);

        Adopter adopter1 = new Adopter();
        adopter1.setName("Isabella Johnson");
        adopter1.setEmail("isabella123@example.com");
        adopter1.setPhone("555-123-4567");
        adopter1.setAddress("123 Main Street, Anytown, USA");
        adopter1 = adopterDao.addAdopter(adopter1);

        Adopter adopter2 = new Adopter();
        adopter2.setName("Bob Smith");
        adopter2.setEmail("bob_smith@example.com");
        adopter2.setPhone("555-444-5555");
        adopter2.setAddress("123 Maple Street, Springfield, USA");
        adopter2 = adopterDao.addAdopter(adopter2);

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

        Adoption adoption1 = new Adoption();
        adoption1.setDate(date);
        adoption1.setPet(pet);
        adoption1.setAdopter(adopter1);
        adoption1 = adoptionDao.addAdoption(adoption1);

        Adoption adoption2 = new Adoption();
        adoption2.setDate(date);
        adoption2.setPet(pet);
        adoption2.setAdopter(adopter2);
        adoption2 = adoptionDao.addAdoption(adoption2);

        List<Adoption> adoptions = adoptionDao.getAllAdoptions();

        assertEquals(2, adoptions.size());
    }

    @Test
    void testUpdateAdoption() {
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

        Adopter newAdopter = new Adopter();
        newAdopter.setName("John Smith");
        newAdopter.setEmail("john.smith@example.com");
        newAdopter.setPhone("555-987-6543");
        newAdopter.setAddress("456 Elm St, Otherville, USA");
        newAdopter = adopterDao.addAdopter(newAdopter);

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
        adoption = adoptionDao.addAdoption(adoption);

        Adoption updated = adoptionDao.getAdoptionById(adoption.getId());

        adoption.setAdopter(newAdopter);
        adoptionDao.updateAdoption(adoption);

        updated = adoptionDao.getAdoptionById(adoption.getId());
    }

    @Test
    void testDeleteAdoptionById() {
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
        adoption = adoptionDao.addAdoption(adoption);

        Adoption deleted = adoptionDao.getAdoptionById(adoption.getId());

        adoptionDao.deleteAdoptionById(adoption.getId());
        deleted = adoptionDao.getAdoptionById(adoption.getId());
        assertNull(deleted);
    }
}