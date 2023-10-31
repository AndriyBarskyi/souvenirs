package org.example.service;

import java.util.List;

import org.example.entity.Souvenir;

public interface SouvenirService {
    void save(Souvenir souvenir);
    Souvenir get(Long id);
    void delete(Long id);
    List<Souvenir> findByManufacturerId(Long manufacturerId);
    List<Souvenir> findByManufacturerName(String manufacturerName);
    List<Souvenir> findByCountry(String country);

    List<Souvenir> getAll();
}
