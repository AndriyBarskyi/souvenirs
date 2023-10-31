package org.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.example.entity.Manufacturer;

public interface ManufacturerService {
    Manufacturer get(Long id);
    void save(Manufacturer manufacturer);
    void delete(Long id);
    List<Manufacturer> getAll();
    List<Manufacturer> getAllCheaperThan(BigDecimal price);

    List<Manufacturer> getAllBySouvenirIdAndProductionDate(Long souvenirId, LocalDate dateOfProduction);
}
