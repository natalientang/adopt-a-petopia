package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;

import java.util.List;

public interface BreedService {
    Breed getBreedById(int id);
    List<Breed> getAllBreeds();
    Breed addBreed(Breed breed);
    void updateBreed(Breed breed);
    void deleteBreedById(int id);
    List<Breed> getBreedsByPet(Pet pet);
}
