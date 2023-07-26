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

    @GetMapping("petsByBreed")
    public String getPetsByBreed(Integer breedId, Model model) {
        Breed breed = breedService.getBreedById(breedId);
        List<Pet> pets = petService.getPetsByBreed(breed);
        model.addAttribute("pets", pets);

        List<Breed> breeds = breedService.getAllBreeds();
        model.addAttribute("breeds", breeds);

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

        List<Breed> breedArrayList = new ArrayList<>();
        for (String breedId : breedIds) {
            breedArrayList.add(breedService.getBreedById(Integer.parseInt(breedId)));
        }
        pet.setBreeds(breedArrayList);

        if (result.hasErrors()) {
            List<Shelter> shelters = shelterService.getAllShelters();
            model.addAttribute("shelters", shelters);

            List<Species> species = speciesService.getAllSpecies();
            model.addAttribute("species", species);

            List<Breed> breeds = breedService.getAllBreeds();
            model.addAttribute("breeds", breeds);
            return "petAdd";
        }

        pet.setName(capitalizeInput(pet.getName()));
        pet.setDescription((capitalizeFirstLetterWord(pet.getDescription())));

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
    public String editPet(@Valid Pet pet, BindingResult result, HttpServletRequest request) {
        String shelterIds = request.getParameter("shelterId");
        pet.setShelter(shelterService.getShelterById(Integer.parseInt(shelterIds)));

        String speciesIds = request.getParameter("speciesId");
        pet.setSpecies(speciesService.getSpeciesById(Integer.parseInt(speciesIds)));

        String[] breedIds = request.getParameterValues("breedId");

        List<Breed> breedArrayList = new ArrayList<>();
        if (breedIds != null) {
            for (String breedId : breedIds) {
                breedArrayList.add(breedService.getBreedById(Integer.parseInt(breedId)));
            }
        }
        pet.setBreeds(breedArrayList);

        if (result.hasErrors()) {
            return "petEdit";
        }

        pet.setName(capitalizeInput(pet.getName()));
        pet.setDescription((capitalizeFirstLetterWord(pet.getDescription())));

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

    // Method to capitalize first letter of every word
    private String capitalizeInput(String str) {
        if (str.length() == 0) {
            return "";
        } else {
            // Split the input string into individual words based on spaces.
            String[] words = str.split(" ");
            StringBuilder capitalizedString = new StringBuilder();

            for (String word : words) {
                if (!word.isEmpty()) {
                    // Capitalize the first letter of the word and append it to the result.
                    capitalizedString.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));

                    // Add a space between words (preserve original spaces from the input).
                    capitalizedString.append(" ");
                }
            }

            // Remove the trailing space and return the final capitalized string.
            return capitalizedString.toString().trim();
        }
    }

    // Method tp capitalize first letter of first word
    private String capitalizeFirstLetterWord(String str){
        // If input string is empty, it will return empty string
        // Otherwise, extracts first character and converts to uppercase
        // Then it extracts rest of string and concatenates first char with rest of string
        return str.length() == 0 ? "" : str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
