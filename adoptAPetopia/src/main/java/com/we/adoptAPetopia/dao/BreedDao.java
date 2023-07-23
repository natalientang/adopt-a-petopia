package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;

import java.util.List;

public interface BreedDao {
    Breed getBreedById(int id);
    List<Breed> getAllBreeds();
    Breed addBreed(Breed breed);
    void updateBreed(Breed breed);
    void deleteBreedById(int id);

    List<Breed> getBreedsByPet(Pet pet);
}
