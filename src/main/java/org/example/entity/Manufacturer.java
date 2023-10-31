package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "\nManufacturer: " +
            "\nid=" + id +
            ", \nname='" + name + '\'' +
            ", \ncountry='" + country + '\'' +
            ' ';
    }
}
