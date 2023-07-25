package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.SpeciesDao;
import com.we.adoptAPetopia.entities.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {
    @Autowired
    SpeciesDao speciesDao;

    @Override
    public Species getSpeciesById(int id) {
        return speciesDao.getSpeciesById(id);
    }

    @Override
    public List<Species> getAllSpecies() {
        return speciesDao.getAllSpecies();
    }

    @Override
    public Species addSpecies(Species species) {
        return speciesDao.addSpecies(species);
    }

    @Override
    public void updateSpecies(Species species) {
        speciesDao.updateSpecies(species);
    }

    @Override
    public void deleteSpeciesById(int id) {
        speciesDao.deleteSpeciesById(id);
    }
}
