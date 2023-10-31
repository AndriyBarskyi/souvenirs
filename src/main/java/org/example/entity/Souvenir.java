package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Souvenir {
    private Long id;
    private String name;
    private Long manufacturerId;
    private BigDecimal price;
    private LocalDate dateOfProduction;

    public Souvenir(String name,  BigDecimal price,
        LocalDate dateOfProduction, Long manufacturerId) {
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.price = price;
        this.dateOfProduction = dateOfProduction;
    }

    @Override
    public String toString() {
        return "\nSouvenir: " +
            "\nid=" + id +
            ", \nname='" + name + '\'' +
            ", \nmanufacturerId='" + manufacturerId + '\'' +
            ", \nprice=" + price +
            ", \ndateOfProduction=" + dateOfProduction +
            ' ';
    }
}
