package com.we.adoptAPetopia.dao;

import com.we.adoptAPetopia.entities.Adopter;

import java.util.List;

public interface AdopterDao {
    Adopter getAdopterById(int id);
    List<Adopter> getAllAdopters();
    Adopter addAdopter(Adopter adopter);
    void updateAdopter(Adopter adopter);
    void deleteAdopterById(int id);
}
