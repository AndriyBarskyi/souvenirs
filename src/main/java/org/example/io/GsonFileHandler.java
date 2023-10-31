package org.example.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.entity.Souvenir;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonFileHandler implements JSONFileHandler<Souvenir> {
    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create();

    @Override public List<Souvenir> readFromJSONFile(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<List<Souvenir>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void writeToJSONFile(String filePath, List<Souvenir> objects) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(objects, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
