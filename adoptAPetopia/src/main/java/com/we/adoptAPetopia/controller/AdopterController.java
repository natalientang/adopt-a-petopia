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
            return "addAdopter";
        }
        adopterService.addAdopter(adopter);
        return "redirect:/adopters";
    }

    @GetMapping("editAdopter")
    public String displayEditAdopter(Integer id, Model model) {
        Adopter adopter = adopterService.getAdopterById(id);
        model.addAttribute("adopter", adopter);
        return "editAdopter";
    }

    @PostMapping("editAdopter")
    public String editAdopter(@Valid Adopter adopter, BindingResult result) {
        if (result.hasErrors()) {
            return "editAdopter";
        }
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
}
