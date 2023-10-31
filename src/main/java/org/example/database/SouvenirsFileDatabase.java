package org.example.database;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.example.io.GsonSouvenirFileHandler;
import org.example.io.JSONFileHandler;
import org.example.entity.Souvenir;

public class SouvenirsFileDatabase implements Database<Souvenir, Long> {
    private static final String FILE_PATH =
        "src/main/java/org/example/resources/souvenirs.json";
    private static SouvenirsFileDatabase instance;
    private final JSONFileHandler<Souvenir> fileHandler =
        new GsonSouvenirFileHandler();

    private SouvenirsFileDatabase() {

    }

    public static SouvenirsFileDatabase getInstance() {
        if (instance == null) {
            instance = new SouvenirsFileDatabase();
        }
        return instance;
    }

    @Override
    public void save(Souvenir souvenir) {
        List<Souvenir> souvenirs = fileHandler.readFromJSONFile(FILE_PATH);
        if (souvenirs.stream().anyMatch(souvenir1 -> souvenir1.getId()
            .equals(souvenir.getId()))) {
            souvenirs = souvenirs.stream()
                .map(souvenir1 -> souvenir1.getId().equals(souvenir.getId())
                    ? souvenir : souvenir1)
                .collect(Collectors.toList());
        } else {
            souvenir.setId(souvenirs.stream().mapToLong(Souvenir::getId).max()
                .orElse(0) + 1);
            souvenirs.add(souvenir);
        }
        fileHandler.writeToJSONFile(FILE_PATH, souvenirs);
    }

    @Override
    public Souvenir get(Long id) {
        return fileHandler.readFromJSONFile(FILE_PATH).stream()
            .filter(souvenir -> souvenir.getId().equals(id)).findFirst()
            .orElse(null);
    }

    @Override
    public void delete(Long id) {
        fileHandler.writeToJSONFile(FILE_PATH,
            fileHandler.readFromJSONFile(FILE_PATH).stream()
                .filter(souvenir -> !souvenir.getId().equals(id))
                .toList());
    }

    @Override
    public List<Souvenir> getAll() {
        return fileHandler.readFromJSONFile(FILE_PATH);
    }

    @Override
    public List<Souvenir> findAll(Predicate<Souvenir> predicate,
        Comparator<Souvenir> comparator,
        boolean reverse) {
        List<Souvenir> souvenirs =
            new java.util.ArrayList<>(fileHandler.readFromJSONFile(FILE_PATH)
                .stream().filter(predicate).toList());
        if (reverse) {
            souvenirs.sort(comparator.reversed());
        } else {
            souvenirs.sort(comparator);
        }
        return souvenirs;
    }

    // TODO: implement this method better
    @Override
    public void update(Souvenir manufacturer, Long id) {
        this.save(manufacturer);
    }
}
