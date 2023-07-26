package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.entities.Adoption;

import java.time.LocalDateTime;
import java.util.List;

public interface AdoptionService {
    Adoption getAdoptionById(int id);
    List<Adoption> getAllAdoptions();
    Adoption addAdoption(Adoption adoption);
    void updateAdoption(Adoption adoption);
    void deleteAdoptionById(int id);

    List<Adoption> getAdoptionsByDate(LocalDateTime localDate);
}
