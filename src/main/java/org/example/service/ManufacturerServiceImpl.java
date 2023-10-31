package org.example.service;

import java.math.BigDecimal;
import java.util.List;

import org.example.database.Database;
import org.example.entity.Manufacturer;
import org.example.entity.Souvenir;

public class ManufacturerServiceImpl implements ManufacturerService {
    private final Database<Manufacturer, Long> manufacturerDatabase;
    private final Database<Souvenir, Long> souvenirDatabase;

    public ManufacturerServiceImpl(
        Database<Manufacturer, Long> manufacturerDatabase,
        Database<Souvenir, Long> souvenirDatabase) {
        this.manufacturerDatabase = manufacturerDatabase;
        this.souvenirDatabase = souvenirDatabase;
    }

    @Override public Manufacturer get(Long id) {
        return manufacturerDatabase.get(id);
    }

    @Override public void save(Manufacturer manufacturer) {
        manufacturerDatabase.save(manufacturer);
    }

    @Override public void delete(Long id) {
        souvenirDatabase.findAll(
                souvenir -> souvenir.getManufacturerId().equals(id))
            .forEach(souvenir -> souvenirDatabase.delete(souvenir.getId()));
        manufacturerDatabase.delete(id);
    }

    @Override public List<Manufacturer> getAll() {
        return manufacturerDatabase.getAll();
    }

    @Override public List<Manufacturer> getAllCheaperThan(BigDecimal price) {
        return manufacturerDatabase.findAll(
            manufacturer -> {
                List<Souvenir> souvenirs = souvenirDatabase.findAll(
                    souvenir -> souvenir.getManufacturerId()
                        .equals(manufacturer.getId()));
                return souvenirs.stream().map(Souvenir::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(price)
                    < 0;
            });
    }

    @Override public List<Manufacturer> findAllBySouvenirNameAndProductionYear(
        String souvenirName, int yearOfProduction) {
        return manufacturerDatabase.findAll(
            manufacturer -> {
                List<Souvenir> souvenirs = souvenirDatabase.findAll(
                    souvenir -> souvenir.getManufacturerId()
                        .equals(manufacturer.getId()));
                return souvenirs.stream()
                    .anyMatch(
                        souvenir -> souvenir.getName().equals(souvenirName)
                            && souvenir.getDateOfProduction().getYear()
                            == yearOfProduction);
            });
    }

    @Override public void update(Manufacturer manufacturer, Long id) {
        manufacturerDatabase.update(manufacturer, id);
    }
}
