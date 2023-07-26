package com.we.adoptAPetopia.controller;

import com.we.adoptAPetopia.entities.Adopter;
import com.we.adoptAPetopia.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdopterController {
    @Autowired
    AdopterService adopterService;

    @GetMapping("adopters")
    public String displayAdopters(Model model) {
        List<Adopter> adopters = adopterService.getAllAdopters();
        model.addAttribute("adopters", adopters);
        return "adopters";
    }

    @GetMapping("addAdopter")
    public String displayAddAdopter(Model model) {
        Adopter adopter = new Adopter();
        model.addAttribute("adopter", adopter);
        return "adopterAdd";
    }

    @PostMapping("addAdopter")
    public String addAdopter(@Valid Adopter adopter, BindingResult result) {
        if (result.hasErrors()) {
            return "adopterAdd";
        }

        adopter.setPhone(formatPhoneNumber(adopter.getPhone()));
        adopter.setName(capitalizeInput(adopter.getName()));
        adopter.setAddress(capitalizeInput(adopter.getAddress()));

        adopterService.addAdopter(adopter);
        return "redirect:/adopters";
    }

    @GetMapping("editAdopter")
    public String displayEditAdopter(Integer id, Model model) {
        Adopter adopter = adopterService.getAdopterById(id);
        model.addAttribute("adopter", adopter);
        return "adopterEdit";
    }

    @PostMapping("editAdopter")
    public String editAdopter(@Valid Adopter adopter, BindingResult result) {
        if (result.hasErrors()) {
            return "adopterEdit";
        }

        adopter.setPhone(formatPhoneNumber(adopter.getPhone()));
        adopter.setName(capitalizeInput(adopter.getName()));
        adopter.setAddress(capitalizeInput(adopter.getAddress()));

        adopterService.updateAdopter(adopter);
        return "redirect:/adopters";
    }

    @GetMapping("deleteAdopter")
    public String deleteAdopter(Integer id) {
        adopterService.deleteAdopterById(id);
        return "redirect:/adopters";
    }

    @GetMapping("adopterDetail")
    public String adopterDetail(Integer id, Model model) {
        Adopter adopter = adopterService.getAdopterById(id);
        model.addAttribute("adopter", adopter);
        return "adopterDetail";
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
