package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Species;

import java.util.List;

public interface SpeciesDao {
    Species getSpeciesById(int id);
    List<Species> getAllSpecies();
    Species addSpecies(Species species);
    void updateSpecies(Species species);
    void deleteSpeciesById(int id);
}
