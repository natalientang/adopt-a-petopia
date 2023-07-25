package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.entities.Species;

import java.util.List;

public interface SpeciesService {
    Species getSpeciesById(int id);
    List<Species> getAllSpecies();
    Species addSpecies(Species species);
    void updateSpecies(Species species);
    void deleteSpeciesById(int id);
}
