package org.example.database;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Database <T, R> {
    void save(T t);
    T get(R id);
    void delete(R id);

    List<T> getAll();
    List<T> findAll(Predicate<T> predicate);

    void update(T manufacturer, R id);
}
