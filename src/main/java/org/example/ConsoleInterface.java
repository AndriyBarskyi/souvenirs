package org.example;

import java.util.Scanner;

import org.example.entity.Manufacturer;
import org.example.service.ManufacturerService;
import org.example.service.SouvenirService;

public class ConsoleInterface {
    private final ManufacturerService manufacturerService;
    private final SouvenirService souvenirService;

    public ConsoleInterface(ManufacturerService manufacturerService,
        SouvenirService souvenirService) {
        this.manufacturerService = manufacturerService;
        this.souvenirService = souvenirService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Додати виробника");
            System.out.println("2. Редагувати виробника");
            System.out.println("3. Переглянути всіх виробників");
            System.out.println("4. Переглянути всі сувеніри");
            System.out.println("5. Пошук сувенірів за виробником");
            System.out.println("6. Пошук сувенірів за країною");
            System.out.println("7. Пошук виробників за ціною");
            System.out.println("8. Видалити виробника та його сувеніри");
            System.out.println("0. Вихід");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера введення

            switch (choice) {
            case 1:
                addManufacturer();
                break;
            case 2:
                editManufacturer();
                break;
            case 3:
                viewAllManufacturers();
                break;
            case 4:
                viewAllSouvenirs();
                break;
            case 5:
                searchSouvenirsByManufacturerName();
                break;
            case 6:
                searchSouvenirsByCountry();
                break;
            case 7:
                searchManufacturersByPrice();
                break;
            case 8:
                deleteManufacturerAndSouvenirs();
                break;
            case 0:
                System.out.println("До побачення!");
                System.exit(0);
                break;
            default:
                System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void deleteManufacturerAndSouvenirs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ID виробника:");
        int id = scanner.nextInt();

        manufacturerService.delete((long) id);
    }

    private void searchManufacturersByPrice() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ціну:");
        double price = scanner.nextDouble();

        manufacturerService.getAllCheaperThan(java.math.BigDecimal.valueOf(price))
            .forEach(System.out::println);
    }

    private void searchSouvenirsByCountry() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть країну:");
        String country = scanner.nextLine();

        souvenirService.findByCountry(country).forEach(System.out::println);
    }

    private void searchSouvenirsByManufacturerName() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть назву виробника:");
        String manufacturerName = scanner.nextLine();

        souvenirService.findByManufacturerName(manufacturerName)
            .forEach(System.out::println);
    }

    private void viewAllSouvenirs() {
        souvenirService.getAll().forEach(System.out::println);
    }

    private void viewAllManufacturers() {
        manufacturerService.getAll().forEach(System.out::println);
    }

    private void editManufacturer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ID виробника:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введіть нову назву виробника:");
        String name = scanner.nextLine();

        System.out.println("Введіть нову країну виробника:");
        String country = scanner.nextLine();

        manufacturerService.update(new Manufacturer(name, country), id);
    }

    private void addManufacturer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть назву виробника:");
        String name = scanner.nextLine();

        System.out.println("Введіть країну виробника:");
        String country = scanner.nextLine();

        manufacturerService.save(new Manufacturer(name, country));
    }
}

