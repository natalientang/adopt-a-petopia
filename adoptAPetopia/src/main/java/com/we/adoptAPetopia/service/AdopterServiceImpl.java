package com.we.adoptAPetopia.service;

import com.we.adoptAPetopia.dao.AdopterDao;
import com.we.adoptAPetopia.entities.Adopter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterServiceImpl implements AdopterService {
    @Autowired
    private AdopterDao adopterDao;

    @Override
    public Adopter getAdopterById(int id) {
        return adopterDao.getAdopterById(id);
    }

    @Override
    public List<Adopter> getAllAdopters() {
        return adopterDao.getAllAdopters();
    }

    @Override
    public Adopter addAdopter(Adopter adopter) {
        return adopterDao.addAdopter(adopter);
    }

    @Override
    public void updateAdopter(Adopter adopter) {
        adopterDao.updateAdopter(adopter);
    }

    @Override
    public void deleteAdopterById(int id) {
        adopterDao.deleteAdopterById(id);
    }
}
