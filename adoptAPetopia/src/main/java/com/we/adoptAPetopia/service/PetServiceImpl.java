package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.AdoptionDao;
import com.we.adoptAPetopia.dao.PetDao;
import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    AdoptionDao adoptionDao;
    @Autowired
    PetDao petDao;

    @Override
    public Pet getPetById(int id) {
        return petDao.getPetById(id);
    }

    @Override
    public List<Pet> getAllPets() {
        return petDao.getAllPets();
    }

    @Override
    public Pet addPet(Pet pet) {
        return petDao.addPet(pet);
    }

    @Override
    public void updatePet(Pet pet) {
        petDao.updatePet(pet);
    }

    @Override
    public void deletePetById(int id) {
        petDao.deletePetById(id);
    }

    @Override
    public List<Pet> getPetsByBreed(Breed breed) {
        return petDao.getPetsByBreed(breed);
    }

    @Override
    public List<Pet> getAvailablePets() {
        // Step 1: Get the list of IDs of pets that have been adopted
        List<Integer> adoptedPetIds = adoptionDao.getAdoptedPetIds();

        // Step 2: Get the list of all pets
        List<Pet> allPets = petDao.getAllPets();

        // Step 3: Filter out the pets that have been adopted from the list of all pets
        List<Pet> availablePets = allPets.stream()
                .filter(pet -> !adoptedPetIds.contains(pet.getId()))
                .collect(Collectors.toList());

        // Step 4: Return the list of available pets that can be adopted
        return availablePets;
    }

}
