package com.we.adoptAPetopia.controller;

import com.we.adoptAPetopia.entities.Shelter;
import com.we.adoptAPetopia.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ShelterController {
    @Autowired
    ShelterService shelterService;

    @GetMapping("shelters")
    public String displayShelters(Model model) {
        List<Shelter> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);
        return "shelters";
    }

    @GetMapping("addShelter")
    public String displayAddShelter(Model model) {
        Shelter shelter = new Shelter();
        model.addAttribute("shelter", shelter);
        return "shelterAdd";
    }

    @PostMapping("addShelter")
    public String addShelter(@Valid Shelter shelter, BindingResult result) {
        if (result.hasErrors()) {
            return "shelterAdd";
        }

        shelter.setName(capitalizeInput(shelter.getName()));
        shelter.setAddress(capitalizeInput(shelter.getAddress()));
        shelter.setPhone(formatPhoneNumber(shelter.getPhone()));

        shelterService.addShelter(shelter);
        return "redirect:/shelters";
    }

    @GetMapping("editShelter")
    public String displayEditShelter(Integer id, Model model) {
        Shelter shelter = shelterService.getShelterById(id);
        model.addAttribute("shelter", shelter);
        return "shelterEdit";
    }

    @PostMapping("editShelter")
    public String editShelter(@Valid Shelter shelter, BindingResult result) {
        if (result.hasErrors()) {
            return "shelterEdit";
        }

        shelter.setName(capitalizeInput(shelter.getName()));
        shelter.setAddress(capitalizeInput(shelter.getAddress()));
        shelter.setPhone(formatPhoneNumber(shelter.getPhone()));

        shelterService.updateShelter(shelter);
        return "redirect:/shelters";
    }

    @GetMapping("deleteShelter")
    public String deleteShelter(Integer id) {
        shelterService.deleteShelterById(id);
        return "redirect:/shelters";
    }

    @GetMapping("shelterDetail")
    public String shelterDetail(Integer id, Model model) {
        Shelter shelter = shelterService.getShelterById(id);
        model.addAttribute("shelter", shelter);
        return "shelterDetail";
    }

    // Method to format phone numbers as "XXX-XXX-XXXX"
    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return phoneNumber;
        }

        // Remove any non-digit characters from the phone number
        String cleanedNumber = phoneNumber.replaceAll("[^\\d]", "");

        // Check if the cleaned number has exactly 10 digits
        if (cleanedNumber.length() != 10) {
            // If not, return the original phone number (not properly formatted)
            return phoneNumber;
        }

        // Format the phone number as "XXX-XXX-XXXX"
        return cleanedNumber.substring(0, 3) + "-" +
                cleanedNumber.substring(3, 6) + "-" +
                cleanedNumber.substring(6);
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
}
