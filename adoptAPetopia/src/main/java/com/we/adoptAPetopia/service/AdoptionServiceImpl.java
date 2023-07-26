package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.AdoptionDao;
import com.we.adoptAPetopia.entities.Adoption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdoptionServiceImpl implements AdoptionService {
    @Autowired
    AdoptionDao adoptionDao;

    @Override
    public Adoption getAdoptionById(int id) {
        return adoptionDao.getAdoptionById(id);
    }

    @Override
    public List<Adoption> getAllAdoptions() {
        return adoptionDao.getAllAdoptions();
    }

    @Override
    public Adoption addAdoption(Adoption adoption) {
        return adoptionDao.addAdoption(adoption);
    }

    @Override
    public void updateAdoption(Adoption adoption) {
        adoptionDao.updateAdoption(adoption);
    }

    @Override
    public void deleteAdoptionById(int id) {
        adoptionDao.deleteAdoptionById(id);
    }

    @Override
    public List<Adoption> getAdoptionsByDate(LocalDateTime date) {
        return adoptionDao.getAdoptionsByDate(date);
    }
}
