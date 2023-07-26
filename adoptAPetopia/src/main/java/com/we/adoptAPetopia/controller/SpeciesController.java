package com.we.adoptAPetopia.controller;

import com.we.adoptAPetopia.entities.Species;
import com.we.adoptAPetopia.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SpeciesController {
    @Autowired
    SpeciesService speciesService;

    @GetMapping("species")
    public String displaySpecies(Model model) {
        List<Species> species = speciesService.getAllSpecies();
        model.addAttribute("species", species);
        return "species";
    }

    @GetMapping("addSpecies")
    public String displayAddSpecies(Model model) {
        Species species = new Species();
        model.addAttribute("species", species);
        return "speciesAdd";
    }

    @PostMapping("addSpecies")
    public String addSpecies (@Valid Species species, BindingResult result) {
        if (result.hasErrors()) {
            return "speciesAdd";
        }

        species.setName(capitalizeInput(species.getName()));
        species.setDescription(capitalizeFirstLetterWord(species.getDescription()));

        speciesService.addSpecies(species);
        return "redirect:/species";
    }

    @GetMapping("editSpecies")
    public String displayEditSpecies(Integer id, Model model) {
        Species species = speciesService.getSpeciesById(id);
        model.addAttribute("species", species);
        return "speciesEdit";
    }

    @PostMapping("editSpecies")
    public String editSpecies(@Valid Species species, BindingResult result) {
        if (result.hasErrors()) {
            return "speciesEdit";
        }

        species.setName(capitalizeInput(species.getName()));
        species.setDescription(capitalizeFirstLetterWord(species.getDescription()));

        speciesService.updateSpecies(species);
        return "redirect:/species";
    }

    @GetMapping("deleteSpecies")
    public String deleteSpecies(Integer id) {
        speciesService.deleteSpeciesById(id);
        return "redirect:/species";
    }

    @GetMapping("speciesDetail")
    public String speciesDetail(Integer id, Model model) {
        Species species = speciesService.getSpeciesById(id);
        model.addAttribute("species", species);
        return "speciesDetail";
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
