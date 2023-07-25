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
}
