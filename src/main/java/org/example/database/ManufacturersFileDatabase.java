package org.example.database;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.example.entity.Manufacturer;
import org.example.io.GsonManufacturerFileHandler;
import org.example.io.JSONFileHandler;

public class ManufacturersFileDatabase implements Database<Manufacturer, Long> {
    private static final String FILE_PATH =
        "src/main/java/org/example/resources/manufacturers.json";
    private static ManufacturersFileDatabase instance;
    private final JSONFileHandler<Manufacturer> fileHandler =
        new GsonManufacturerFileHandler();

    private ManufacturersFileDatabase() {

    }

    public static ManufacturersFileDatabase getInstance() {
        if (instance == null) {
            instance = new ManufacturersFileDatabase();
        }
        return instance;
    }

    @Override public void save(Manufacturer manufacturer) {
        List<Manufacturer> manufacturers =
            fileHandler.readFromJSONFile(FILE_PATH);
        if (manufacturers.stream()
            .anyMatch(manufacturer1 -> manufacturer1.getId()
                .equals(manufacturer.getId()))) {
            manufacturers = manufacturers.stream()
                .map(manufacturer1 ->
                    manufacturer1.getId().equals(manufacturer.getId())
                        ? manufacturer : manufacturer1)
                .collect(Collectors.toList());
        } else {
            manufacturer.setId(
                manufacturers.stream().mapToLong(Manufacturer::getId).max()
                    .orElse(0) + 1);
            manufacturers.add(manufacturer);
        }
        fileHandler.writeToJSONFile(FILE_PATH, manufacturers);
    }

    @Override public Manufacturer get(Long id) {
        return fileHandler.readFromJSONFile(FILE_PATH).stream()
            .filter(manufacturer -> manufacturer.getId().equals(id)).findFirst()
            .orElse(null);
    }

    @Override public void delete(Long id) {
        fileHandler.writeToJSONFile(FILE_PATH,
            fileHandler.readFromJSONFile(FILE_PATH).stream()
                .filter(manufacturer -> !manufacturer.getId().equals(id))
                .toList());
    }

    @Override public List<Manufacturer> getAll() {
        return fileHandler.readFromJSONFile(FILE_PATH);
    }

    @Override
    public List<Manufacturer> findAll(Predicate<Manufacturer> predicate,
        Comparator<Manufacturer> comparator,
        boolean reverse) {
        List<Manufacturer> manufacturers =
            new java.util.ArrayList<>(fileHandler.readFromJSONFile(FILE_PATH)
                .stream().filter(predicate).toList());
        if (reverse) {
            manufacturers.sort(comparator.reversed());
        } else {
            manufacturers.sort(comparator);
        }
        return manufacturers;
    }

    // TODO: implement this method better
    @Override
    public void update(Manufacturer manufacturer, Long id) {
        this.save(manufacturer);
    }
}
