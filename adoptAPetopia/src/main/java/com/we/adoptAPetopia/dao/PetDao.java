package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Pet;

import java.util.List;

public interface PetDao {
    Pet getPetById(int id);
    List<Pet> getAllPets();
    Pet addPet(Pet pet);
    void updatePet(Pet pet);
    void deletePetById(int id);
}
