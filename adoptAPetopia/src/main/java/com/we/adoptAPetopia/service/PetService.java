package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;

import java.util.List;

public interface PetService {
    Pet getPetById(int id);
    List<Pet> getAllPets();
    Pet addPet(Pet pet);
    void updatePet(Pet pet);
    void deletePetById(int id);

    List<Pet> getPetsByBreed(Breed breed);

    List<Pet> getAvailablePets();
}
