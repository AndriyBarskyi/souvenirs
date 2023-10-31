package org.example.service;

import java.util.List;

import org.example.entity.Manufacturer;
import org.example.entity.Souvenir;
import org.example.database.Database;

public class SouvenirServiceImpl implements SouvenirService {
    private final Database<Souvenir, Long> souvenirDatabase;
    private final Database<Manufacturer, Long> manufacturerDatabase;

    public SouvenirServiceImpl(Database<Souvenir, Long> souvenirDatabase,
        Database<Manufacturer, Long> manufacturerDatabase) {
        this.souvenirDatabase = souvenirDatabase;
        this.manufacturerDatabase = manufacturerDatabase;
    }

    @Override
    public void save(Souvenir souvenir) {
        souvenirDatabase.save(souvenir);
    }

    @Override
    public Souvenir get(Long id) {
        return souvenirDatabase.get(id);
    }

    @Override
    public void delete(Long id) {
        souvenirDatabase.delete(id);
    }

    @Override
    public List<Souvenir> findByManufacturerId(Long manufacturerId) {
        return souvenirDatabase.findAll(
            souvenir -> souvenir.getManufacturerId().equals(manufacturerId),
            null,
            false);
    }

    @Override
    public List<Souvenir> findByManufacturerName(String manufacturerName) {
        return souvenirDatabase.findAll(
            souvenir -> manufacturerDatabase.get(souvenir.getManufacturerId())
                .getName().equals(manufacturerName),
            null,
            false);
    }

    @Override
    public List<Souvenir> findByCountry(String country) {
        return souvenirDatabase.findAll(
            souvenir -> manufacturerDatabase.get(souvenir.getManufacturerId())
                .getCountry().equals(country),
            null,
            false);
    }

    @Override
    public List<Souvenir> getAll() {
        return souvenirDatabase.getAll();
    }

    @Override
    public void update(Souvenir souvenir, Long id) {
        souvenirDatabase.update(souvenir, id);
    }
}
