package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Adoption;

import java.util.List;

public interface AdoptionDao {
    Adoption getAdoptionById(int id);
    List<Adoption> getAllAdoptions();
    Adoption addAdoption(Adoption adoption);
    void updateAdoption(Adoption adoption);
    void deleteAdoptionById(int id);

}
