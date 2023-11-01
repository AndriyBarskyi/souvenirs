package org.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        validateManufacturerId(id);
        return manufacturerDatabase.get(id);
    }

    @Override public void save(Manufacturer manufacturer) {
        validateManufacturer(manufacturer);
        manufacturerDatabase.save(manufacturer);
    }


    @Override public void delete(Long id) {
        validateManufacturerId(id);
        souvenirDatabase.findAll(
                souvenir -> souvenir.getManufacturerId().equals(id))
            .forEach(souvenir -> souvenirDatabase.delete(souvenir.getId()));
        manufacturerDatabase.delete(id);
    }

    @Override public List<Manufacturer> getAll() {
        return manufacturerDatabase.getAll();
    }

    @Override public List<Manufacturer> getAllCheaperThan(BigDecimal price) {
        validatePrice(price);
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
        validateSouvenirName(souvenirName);
        validateYearOfProduction(yearOfProduction);
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
        validateManufacturer(manufacturer);
        validateManufacturerId(id);
        manufacturerDatabase.update(manufacturer, id);
    }

    private void validateManufacturerId(Long id) {
        if (id == null || manufacturerDatabase.get(id) == null) {
            throw new IllegalArgumentException("Виробника з таким id не існує!");
        }
    }

    private static void validateManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Дані про виробника не можуть бути порожні!");
        } else if (manufacturer.getName() == null || manufacturer.getName().isEmpty()) {
            throw new IllegalArgumentException("Ім'я виробника не може бути порожнім!");
        } else if (manufacturer.getCountry() == null || manufacturer.getCountry().isEmpty()) {
            throw new IllegalArgumentException("Країна виробника не може бути порожньою!");
        }
    }

    private static void validateSouvenirName(String souvenirName) {
        if (souvenirName == null || souvenirName.isEmpty()) {
            throw new IllegalArgumentException("Назва сувеніру не може бути порожньою!");
        }
    }

    private static void validateYearOfProduction(int yearOfProduction) {
        if (yearOfProduction > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Рік виробництва не може бути більшим за поточний");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Ціна не може бути порожньою!");
        } else if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною!");
        }
    }
}
