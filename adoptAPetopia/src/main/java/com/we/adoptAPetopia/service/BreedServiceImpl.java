package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.BreedDao;
import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedServiceImpl implements BreedService {
    @Autowired
    BreedDao breedDao;

    @Override
    public Breed getBreedById(int id) {
        return breedDao.getBreedById(id);
    }

    @Override
    public List<Breed> getAllBreeds() {
        return breedDao.getAllBreeds();
    }

    @Override
    public Breed addBreed(Breed breed) {
        return breedDao.addBreed(breed);
    }

    @Override
    public void updateBreed(Breed breed) {
        breedDao.updateBreed(breed);
    }

    @Override
    public void deleteBreedById(int id) {
        breedDao.deleteBreedById(id);
    }

    @Override
    public List<Breed> getBreedsByPet(Pet pet) {
        return breedDao.getBreedsByPet(pet);
    }
}
