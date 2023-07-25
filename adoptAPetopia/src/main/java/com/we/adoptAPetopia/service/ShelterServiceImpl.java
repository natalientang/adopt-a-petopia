package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.ShelterDao;
import com.we.adoptAPetopia.entities.Shelter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterServiceImpl implements ShelterService {
    @Autowired
    ShelterDao shelterDao;

    @Override
    public Shelter getShelterById(int id) {
        return shelterDao.getShelterById(id);
    }

    @Override
    public List<Shelter> getAllShelters() {
        return shelterDao.getAllShelters();
    }

    @Override
    public Shelter addShelter(Shelter shelter) {
        return shelterDao.addShelter(shelter);
    }

    @Override
    public void updateShelter(Shelter shelter) {
        shelterDao.updateShelter(shelter);
    }

    @Override
    public void deleteShelterById(int id) {
        shelterDao.deleteShelterById(id);
    }
}
