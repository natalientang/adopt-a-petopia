package com.we.adoptAPetopia.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Adoption {
    private int id;
    private LocalDateTime date;
    private Pet pet;
    private Adopter adopter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Adopter getAdopter() {
        return adopter;
    }

    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adoption adoption = (Adoption) o;

        if (id != adoption.id) return false;
        if (!Objects.equals(date, adoption.date)) return false;
        if (!Objects.equals(pet, adoption.pet)) return false;
        return Objects.equals(adopter, adoption.adopter);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (pet != null ? pet.hashCode() : 0);
        result = 31 * result + (adopter != null ? adopter.hashCode() : 0);
        return result;
    }
}
