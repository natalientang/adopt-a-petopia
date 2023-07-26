package com.we.adoptAPetopia.controller;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.entities.Shelter;
import com.we.adoptAPetopia.entities.Species;
import com.we.adoptAPetopia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PetController {
    @Autowired
    PetService petService;
    @Autowired
    ShelterService shelterService;
    @Autowired
    SpeciesService speciesService;
    @Autowired
    BreedService breedService;
    @Autowired
    AdoptionService adoptionService;

    @GetMapping("pets")
    public String displayPets(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);

        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);

        List<Species> species = speciesService.getAllSpecies();
        model.addAttribute("species", species);

        return "pets";
    }

    @GetMapping("addPet")
    public String displayAddPet(Model model) {
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);

        List<Species> species = speciesService.getAllSpecies();
        model.addAttribute("species", species);

        List<Breed> breeds = breedService.getAllBreeds();
        model.addAttribute("breeds", breeds);

        model.addAttribute("pet", new Pet());

        return "petAdd";
    }

    @PostMapping("addPet")
    public String addPet(@Valid Pet pet, BindingResult result, HttpServletRequest request, Model model) {
        String shelterIds = request.getParameter("shelterId");
        pet.setShelter(shelterService.getShelterById(Integer.parseInt(shelterIds)));

        String speciesIds = request.getParameter("speciesId");
        pet.setSpecies(speciesService.getSpeciesById(Integer.parseInt(speciesIds)));

        String[] breedIds = request.getParameterValues("breedId");

        model.addAttribute("pet", new Pet());

        List<Breed> breedArrayList = new ArrayList<>();
        for (String breedId : breedIds) {
            breedArrayList.add(breedService.getBreedById(Integer.parseInt(breedId)));
        }
        pet.setBreeds(breedArrayList);

        if (result.hasErrors()) {
            return "petAdd";
        }
        petService.addPet(pet);
        return "redirect:/pets";
    }

    @GetMapping("editPet")
    public String displayEditPet(Integer id, Model model) {
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);

        List<Species> species = speciesService.getAllSpecies();
        model.addAttribute("species", species);

        List<Breed> breeds = breedService.getAllBreeds();
        model.addAttribute("breeds", breeds);

        Pet pet = petService.getPetById(id);
        model.addAttribute("pet", pet);
        return "petEdit";
    }

    @PostMapping("editPet")
    public String editPet(Integer id, HttpServletRequest request, BindingResult result) {
        Pet pet = petService.getPetById(id);

        String shelterIds = request.getParameter("shelterId");
        pet.setShelter(shelterService.getShelterById(Integer.parseInt(shelterIds)));

        String speciesIds = request.getParameter("speciesId");
        pet.setSpecies(speciesService.getSpeciesById(Integer.parseInt(speciesIds)));

        String [] breedIds = request.getParameterValues("breedId");

        List<Breed> breedArrayList = new ArrayList<>();
        for (String breedId : breedIds) {
            breedArrayList.add(breedService.getBreedById(Integer.parseInt(breedId)));
        }
        pet.setBreeds(breedArrayList);

        if (result.hasErrors()) {
            return "petEdit";
        }
        petService.updatePet(pet);
        return "redirect:/pets";
    }

    @GetMapping("deletePet")
    public String deletePet(Integer id) {
        petService.deletePetById(id);
        return "redirect:/pets";
    }

    @GetMapping("petDetail")
    public String petDetail(Integer id, Model model) {
        Pet pet = petService.getPetById(id);
        model.addAttribute("pet", pet);

        List<Breed> getPetsByBreed = breedService.getBreedsByPet(pet);
        model.addAttribute("breeds", getPetsByBreed);

        return "petDetail";
    }
}
