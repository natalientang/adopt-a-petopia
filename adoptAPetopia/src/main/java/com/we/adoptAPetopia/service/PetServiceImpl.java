package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.PetDao;
import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
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
}
