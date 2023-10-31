package org.example.io;

import java.util.List;

public interface JSONFileHandler <T>{
    List<T> readFromJSONFile(String filePath);
    void writeToJSONFile(String filePath, List<T> objects);
}
