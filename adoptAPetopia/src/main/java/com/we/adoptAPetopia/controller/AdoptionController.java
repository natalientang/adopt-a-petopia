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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("adoptionsByDate")
    public String getAdoptionsByDate(Model model, HttpServletRequest request, LocalDateTime localDate) {
        if (!request.getParameter("date").equals("")){
            localDate = LocalDateTime.parse(request.getParameter("date"));
        }
        else{
            List<Adopter> adopters = adopterService.getAllAdopters();
            model.addAttribute("adopters", adopters);
            return "adoptions";
        }

        List<Adoption> adoptions = adoptionService.getAdoptionsByDate(localDate);
        model.addAttribute("adoptions", adoptions);

        List<Adopter> adopters = adopterService.getAllAdopters();
        model.addAttribute("adopters", adopters);

        return "adoptions";
    }

    @GetMapping("addAdoption")
    public String displayAddAdoption(Model model) {
        List<Pet> availablePets = petService.getAvailablePets(); // Fetch only available pets
        model.addAttribute("pets", availablePets);

        List<Adopter> adopters = adopterService.getAllAdopters();
        model.addAttribute("adopters", adopters);

        Adoption adoption = new Adoption();
        model.addAttribute("adoption", adoption);

        return "adoptionAdd";
    }

    @PostMapping("addAdoption")
    public String addAdoption(@Valid Adoption adoption, BindingResult result, HttpServletRequest request, Model model) {
        String petIds = request.getParameter("petId");
        adoption.setPet(petService.getPetById(Integer.parseInt(petIds)));

        String adopterIds = request.getParameter("adopterId");
        adoption.setAdopter(adopterService.getAdopterById(Integer.parseInt(adopterIds)));

        String date = request.getParameter("adoptionDate");
        LocalDateTime dateTime = LocalDateTime.parse(date);
        adoption.setDate(dateTime);

        if (result.hasErrors()) {
            return "adoptionAdd";
        }
        adoptionService.addAdoption(adoption);
        return "redirect:/adoptions";
    }

    @GetMapping("editAdoption")
    public String displayEditAdoption(Integer id, Model model) {
        Adoption adoption = adoptionService.getAdoptionById(id);
        model.addAttribute("adoption", adoption);

        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);

        List<Adopter> adopters = adopterService.getAllAdopters();
        model.addAttribute("adopters", adopters);

        return "adoptionEdit";
    }

    @PostMapping("editAdoption")
    public String editAdoption(@Valid Adoption adoption, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            List<Pet> pets = petService.getAllPets();
            model.addAttribute("pets", pets);

            List<Adopter> adopters = adopterService.getAllAdopters();
            model.addAttribute("adopters", adopters);

            return "adoptionEdit";
        }

        int petId = Integer.parseInt(request.getParameter("petId"));
        int adopterId = Integer.parseInt(request.getParameter("adopterId"));

        adoption.setPet(petService.getPetById(petId));
        adoption.setAdopter(adopterService.getAdopterById(adopterId));
        adoption.setDate(LocalDateTime.parse(request.getParameter("adoptionDate")));

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
