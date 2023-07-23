package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Shelter;

import java.util.List;

public interface ShelterDao {
    Shelter getShelterById(int id);
    List<Shelter> getAllShelters();
    Shelter addShelter(Shelter shelter);
    void updateShelter(Shelter shelter);
    void deleteShelterById(int id);
}
