package com.we.adoptAPetopia.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Adopter {
    private int id;
    @NotBlank(message = "Name must not be blank!")
    @Size(max = 25, message = "Name must be fewer than 25 characters")
    private String name;
    @NotBlank(message = "Email must not be blank!")
    @Size(max = 50, message = "Email must be fewer than 50 characters")
    private String email;
    @NotBlank(message = "Phone must not be blank!")
    @Size(max = 25, message = "Phone must be fewer than 25 characters")
    private String phone;
    @NotBlank(message = "Address must not be blank!")
    @Size(max = 255, message = "Address must be fewer than 255 characters")
    private String address;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adopter adopter = (Adopter) o;

        if (id != adopter.id) return false;
        if (!Objects.equals(name, adopter.name)) return false;
        if (!Objects.equals(email, adopter.email)) return false;
        if (!Objects.equals(phone, adopter.phone)) return false;
        return Objects.equals(address, adopter.address);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
