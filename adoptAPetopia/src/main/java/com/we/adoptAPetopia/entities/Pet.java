package com.we.adoptAPetopia.entities;

import java.util.List;
import java.util.Objects;

public class Pet {
    private int id;
    private String name;
    private Species species;
    private String description;
    private Shelter shelter;
    private List<Breed> breeds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (id != pet.id) return false;
        if (!Objects.equals(name, pet.name)) return false;
        if (!Objects.equals(species, pet.species)) return false;
        if (!Objects.equals(description, pet.description)) return false;
        if (!Objects.equals(shelter, pet.shelter)) return false;
        return Objects.equals(breeds, pet.breeds);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (species != null ? species.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (shelter != null ? shelter.hashCode() : 0);
        result = 31 * result + (breeds != null ? breeds.hashCode() : 0);
        return result;
    }
}
