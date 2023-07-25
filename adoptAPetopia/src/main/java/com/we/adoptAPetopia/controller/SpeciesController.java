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
}
