package com.we.adoptAPetopia.controller;

import com.we.adoptAPetopia.entities.Breed;
import com.we.adoptAPetopia.entities.Pet;
import com.we.adoptAPetopia.service.BreedService;
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
public class BreedController {
    @Autowired
    BreedService breedService;

    @Autowired
    PetService petService;

    @GetMapping("breeds")
    public String displayBreeds(Model model) {
        List<Breed> breeds = breedService.getAllBreeds();
        model.addAttribute("breeds", breeds);
        return "breeds";
    }

    @GetMapping("addBreed")
    public String displayAddBreed(Model model) {
        Breed breed = new Breed();
        model.addAttribute("breed", breed);
        return "breedAdd";
    }

    @PostMapping("addBreed")
    public String addBreed(@Valid Breed breed, BindingResult result) {
        if (result.hasErrors()) {
            return "breedAdd";
        }

        breed.setName(capitalizeInput(breed.getName()));
        breed.setDescription(capitalizeFirstLetterWord(breed.getDescription()));

        breedService.addBreed(breed);
        return "redirect:/breeds";
    }

    @GetMapping("editBreed")
    public String displayEditBreed(Integer id, Model model) {
        Breed breed = breedService.getBreedById(id);
        model.addAttribute("breed", breed);
        return "breedEdit";
    }

    @PostMapping("editBreed")
    public String editBreed(@Valid Breed breed, BindingResult result) {
        if (result.hasErrors()) {
            return "breedEdit";
        }

        breed.setName(capitalizeInput(breed.getName()));
        breed.setDescription(capitalizeFirstLetterWord(breed.getDescription()));

        breedService.updateBreed(breed);
        return "redirect:/breeds";
    }

    @GetMapping("deleteBreed")
    public String deleteBreed(Integer id) {
        breedService.deleteBreedById(id);
        return "redirect:/breeds";
    }

    @GetMapping("breedDetail")
    public String breedDetail(Integer id, Model model) {
        Breed breed = breedService.getBreedById(id);
        model.addAttribute("breed", breed);

        List<Pet> petsByBreed = petService.getPetsByBreed(breed);
        model.addAttribute("pets", petsByBreed);

        return "breedDetail";
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
