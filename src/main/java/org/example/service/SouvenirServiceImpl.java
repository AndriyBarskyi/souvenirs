package org.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.example.database.Database;
import org.example.entity.Manufacturer;
import org.example.entity.Souvenir;

public class SouvenirServiceImpl implements SouvenirService {
    private final Database<Souvenir, Long> souvenirDatabase;
    private final Database<Manufacturer, Long> manufacturerDatabase;

    public SouvenirServiceImpl(Database<Souvenir, Long> souvenirDatabase,
        Database<Manufacturer, Long> manufacturerDatabase) {
        this.souvenirDatabase = souvenirDatabase;
        this.manufacturerDatabase = manufacturerDatabase;
    }

    @Override public void save(Souvenir souvenir) {
        validateSouvenir(souvenir);
        souvenirDatabase.save(souvenir);
    }

    @Override public Souvenir get(Long id) {
        validateId(id);
        return souvenirDatabase.get(id);
    }

    @Override public void delete(Long id) {
        validateId(id);
        souvenirDatabase.delete(id);
    }

    @Override public List<Souvenir> findByManufacturerId(Long manufacturerId) {
        validateManufacturerId(manufacturerId);
        return souvenirDatabase.findAll(
            souvenir -> souvenir.getManufacturerId().equals(manufacturerId));
    }

    @Override
    public List<Souvenir> findByManufacturerName(String manufacturerName) {
        validateName(manufacturerName);
        return souvenirDatabase.findAll(
            souvenir -> manufacturerDatabase.get(souvenir.getManufacturerId())
                .getName().equals(manufacturerName));
    }

    @Override public List<Souvenir> findByCountry(String country) {
        validateCountry(country);
        return souvenirDatabase.findAll(
            souvenir -> manufacturerDatabase.get(souvenir.getManufacturerId())
                .getCountry().equals(country));
    }

    @Override public List<Souvenir> getAll() {
        return souvenirDatabase.getAll();
    }

    @Override public void update(Souvenir souvenir, Long id) {
        validateSouvenir(souvenir);
        validateId(id);
        souvenirDatabase.update(souvenir, id);
    }

    @Override public List<Integer> getAllProductionYears() {
        List<Souvenir> souvenirs = souvenirDatabase.getAll();
        return souvenirs.stream()
            .map(souvenir -> souvenir.getDateOfProduction().getYear())
            .distinct().toList();
    }

    @Override public List<Souvenir> findByProductionYear(int year) {
        validateYear(year);
        return souvenirDatabase.findAll(
            souvenir -> souvenir.getDateOfProduction().getYear() == year);
    }

    private void validateSouvenir(Souvenir souvenir) {
        if (souvenir == null) {
            throw new IllegalArgumentException(
                "Дані про сувенір не можуть бути порожні!");
        } else if (souvenir.getName() == null || souvenir.getName().isEmpty()) {
            throw new IllegalArgumentException(
                "Назва сувеніру не може бути порожньою!");
        } else if (souvenir.getPrice() == null) {
            throw new IllegalArgumentException(
                "Ціна сувеніру не може бути порожньою!");
        } else if (souvenir.getPrice().compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException(
                "Ціна сувеніру не може бути від'ємною!");
        } else if (souvenir.getDateOfProduction() == null) {
            throw new IllegalArgumentException(
                "Дата виробництва сувеніру не може бути порожньою!");
        } else if (souvenir.getDateOfProduction().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                "Дата виробництва не може бути пізніше поточної дати!");
        } else if (souvenir.getManufacturerId() == null
            || manufacturerDatabase.get(souvenir.getManufacturerId()) == null) {
            throw new IllegalArgumentException(
                "Виробника з таким id не існує!");
        }
    }

    private void validateId(Long id) {
        if (id == null || souvenirDatabase.get(id) == null) {
            throw new IllegalArgumentException(
                "Сувеніру з таким id не існує!");
        }
    }

    private void validateManufacturerId(Long manufacturerId) {
        if (manufacturerId == null
            || manufacturerDatabase.get(manufacturerId) == null) {
            throw new IllegalArgumentException(
                "Виробника з таким id не існує!");
        }
    }

    private void validateName(String manufacturerName) {
        if (manufacturerName == null || manufacturerName.isEmpty()) {
            throw new IllegalArgumentException(
                "Ім'я виробника не може бути порожнім!");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException(
                "Країна виробника не може бути порожньою!");
        }
    }

    private void validateYear(int year) {
        if (year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException(
                "Рік виробництва не може бути більшим за поточний");
        }
    }
}
