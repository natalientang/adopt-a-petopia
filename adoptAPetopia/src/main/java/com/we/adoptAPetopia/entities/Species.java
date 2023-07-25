package com.we.adoptAPetopia.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Species {
    private int id;
    @NotBlank(message = "Name must not be blank!")
    @Size(max = 25, message = "Name must be fewer than 25 characters")
    private String name;
    @NotBlank(message = "Description must not be blank!")
    @Size(max = 255, message = "Description must be fewer than 255 characters")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Species species = (Species) o;

        if (id != species.id) return false;
        if (!Objects.equals(name, species.name)) return false;
        return Objects.equals(description, species.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
