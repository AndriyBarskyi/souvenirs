package org.example;

import java.math.BigDecimal;
import java.util.Comparator;

import org.example.database.ManufacturersFileDatabase;
import org.example.database.SouvenirsFileDatabase;
import org.example.entity.Souvenir;
import org.example.service.ManufacturerService;
import org.example.service.ManufacturerServiceImpl;
import org.example.service.SouvenirService;
import org.example.service.SouvenirServiceImpl;

public class App 
{
    public static void main( String[] args ) {
        ManufacturerService manufacturerService = new ManufacturerServiceImpl(
            ManufacturersFileDatabase.getInstance(),
            SouvenirsFileDatabase.getInstance());
        SouvenirService souvenirService = new SouvenirServiceImpl(
            SouvenirsFileDatabase.getInstance(),
            ManufacturersFileDatabase.getInstance());
        ConsoleInterface consoleInterface = new ConsoleInterface(
            manufacturerService, souvenirService);
        consoleInterface.start();
    }
}
