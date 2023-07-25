package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.entities.Adopter;

import java.util.List;

public interface AdopterService {
    Adopter getAdopterById(int id);
    List<Adopter> getAllAdopters();
    Adopter addAdopter(Adopter adopter);
    void updateAdopter(Adopter adopter);
    void deleteAdopterById(int id);
}
