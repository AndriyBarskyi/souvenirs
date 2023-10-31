package org.example.entity;

import lombok.Data;

@Data
public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    @Override
    public String toString() {
        return "\nManufacturer: " +
            "\nid=" + id +
            ", \nname='" + name + '\'' +
            ", \ncountry='" + country + '\'' +
            ' ';
    }
}
