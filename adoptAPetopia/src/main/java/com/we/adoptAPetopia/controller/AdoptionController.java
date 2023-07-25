package com.we.adoptAPetopia.controller;

import com.we.adoptAPetopia.entities.Adopter;
import com.we.adoptAPetopia.entities.Adoption;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.service.AdopterService;
import com.we.adoptAPetopia.service.AdoptionService;
import com.we.adoptAPetopia.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdoptionController {
    @Autowired
    AdoptionService adoptionService;
    @Autowired
    PetService petService;
    @Autowired
    AdopterService adopterService;

    @GetMapping("adoptions")
    public String displayAdoptions(Model model) {
        List<Adoption> adoptions = adoptionService.getAllAdoptions();
        model.addAttribute("adoptions", adoptions);

        List<Adopter> adopters = adopterService.getAllAdopters();
        model.addAttribute("adopters", adopters);

        return "adoptions";
    }

    @GetMapping("addAdoption")
    public String displayAddAdoption(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);

        List<Adopter> adopters = adopterService.getAllAdopters();
        model.addAttribute("adopters", adopters);

        return "addAdoption";
    }

    @PostMapping("addAdoption")
    public String addAdoption(@Valid Adoption adoption, BindingResult result) {
        if (result.hasErrors()) {
            return "addAdoption";
        }
        adoptionService.addAdoption(adoption);
        return "redirect:/adoptions";
    }

    @GetMapping("editAdoption")
    public String displayEditAdoption(Integer id, Model model) {
        Adoption adoption = adoptionService.getAdoptionById(id);
        model.addAttribute("adoption", adoption);
        return "editAdoption";
    }

    @PostMapping("editAdoption")
    public String editAdoption(@Valid Adoption adoption, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Pet> pets = petService.getAllPets();
            model.addAttribute("pets", pets);

            List<Adopter> adopters = adopterService.getAllAdopters();
            model.addAttribute("adopters", adopters);

            return "editAdoption";
        }
        adoptionService.updateAdoption(adoption);
        return "redirect:/adoptions";
    }

    @GetMapping("deleteAdoption")
    public String deleteAdoption(Integer id) {
        adoptionService.deleteAdoptionById(id);
        return "redirect:/adoptions";
    }

    @GetMapping("adoptionDetail")
    public String adoptionDetail(Integer id, Model model) {
        Adoption adoption = adoptionService.getAdoptionById(id);
        model.addAttribute("adoption", adoption);
        return "adoptionDetail";
    }
}
