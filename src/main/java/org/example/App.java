package org.example;

import java.math.BigDecimal;
import java.util.Comparator;

import org.example.database.SouvenirsFileDatabase;
import org.example.entity.Souvenir;

public class App 
{
    public static void main( String[] args ) {
        System.out.println("Sorting and filtering:");
        System.out.println(SouvenirsFileDatabase.getInstance().findAll(
            souvenir -> souvenir.getPrice().compareTo(BigDecimal.valueOf(10.0))
                > 0, Comparator.comparing(Souvenir::getPrice), false));
    }
}
