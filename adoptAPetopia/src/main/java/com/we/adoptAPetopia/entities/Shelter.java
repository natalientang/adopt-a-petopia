package com.we.adoptAPetopia.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Shelter {
    private int id;
    @NotBlank(message = "Please input a name!")
    @Size(max = 25, message = "Name must be fewer than 25 characters")
    private String name;
    @NotBlank(message = "Please input an address!")
    @Size(max = 255, message = "Address must be fewer than 255 characters")
    private String address;
    @NotBlank(message = "Please input a phone number!")
    @Size(max = 25, message = "Phone must be fewer than 25 characters")
    private String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelter shelter = (Shelter) o;

        if (id != shelter.id) return false;
        if (!Objects.equals(name, shelter.name)) return false;
        if (!Objects.equals(address, shelter.address)) return false;
        return Objects.equals(phone, shelter.phone);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
