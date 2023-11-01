package org.example;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.example.entity.Manufacturer;
import org.example.entity.Souvenir;
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
            System.out.println("4. Видалити виробника та його сувеніри");
            System.out.println("5. Додати сувенір");
            System.out.println("6. Редагувати сувенір");
            System.out.println("7. Переглянути всі сувеніри");
            System.out.println("8. Видалити сувенір");
            System.out.println("9. Пошук сувенірів за виробником");
            System.out.println("10. Пошук сувенірів за країною");
            System.out.println(
                "11. Пошук виробників за максимальною ціною сувеніру");
            System.out.println(
                "12. Переглянути всіх викробників та їх сувеніри");
            System.out.println(
                "13. Пошук виробників за назвою сувеніру та роком випуску");
            System.out.println(
                "14. Показати список сувенірів за роками випуску");
            System.out.println("0. Вихід");

            int choice = -1;

            while (choice < 0 || choice > 14) {
                System.out.print("Введіть ваш вибір: ");
                try {
                    choice = scanner.nextInt();
                    if (choice < 0 || choice > 14) {
                        System.out.println("Неправильний вибір. Введіть число від 0 до 14.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Неправильний ввід. Введіть число від 0 до 14.");
                    scanner.nextLine();
                }
            }

            try {
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
                    deleteManufacturerAndSouvenirs();
                    break;
                case 5:
                    addSouvenir();
                    break;
                case 6:
                    editSouvenir();
                    break;
                case 7:
                    viewAllSouvenirs();
                    break;
                case 8:
                    deleteSouvenir();
                    break;
                case 9:
                    searchSouvenirsByManufacturerName();
                    break;
                case 10:
                    searchSouvenirsByCountry();
                    break;
                case 11:
                    searchManufacturersByPrice();
                    break;
                case 12:
                    viewAllManufacturersAndSouvenirs();
                    break;
                case 13:
                    searchManufacturersBySouvenirNameAndYear();
                    break;
                case 14:
                    viewSouvenirsByYears();
                    break;
                case 0:
                    System.out.println("До побачення!");
                    System.exit(0);
                    break;
                default:
                    System.err.println("Неправильний вибір. Спробуйте ще раз.");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.out.println("Спробуйте ще раз.");
            }
        }
    }

    private void viewSouvenirsByYears() {
        List<Integer> allYears = souvenirService.getAllProductionYears();
        for (Integer year : allYears) {
            System.out.println(year);
            System.out.println(souvenirService.findByProductionYear(year));
        }
    }

    private void searchManufacturersBySouvenirNameAndYear() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть назву сувеніру:");
        String souvenirName = scanner.nextLine();

        System.out.println("Введіть рік випуску сувеніру:");
        int year = scanner.nextInt();

        manufacturerService.findAllBySouvenirNameAndProductionYear(souvenirName,
                year)
            .forEach(System.out::println);
    }

    private void viewAllManufacturersAndSouvenirs() {
        List<Manufacturer> manufacturers = manufacturerService.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
            System.out.println(
                souvenirService.findByManufacturerId(manufacturer.getId()));
        }
    }

    private void deleteSouvenir() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ID сувеніру:");
        int id = scanner.nextInt();

        souvenirService.delete((long) id);
    }

    private void editSouvenir() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ID сувеніру:");
        Long id = (long) scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введіть нову назву сувеніру:");
        String name = scanner.nextLine();

        System.out.println("Введіть нову ціну сувеніру:");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Введіть нову дату випуску сувеніру:");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.println("Введіть новий ID виробника:");
        int manufacturerId = scanner.nextInt();

        souvenirService.update(
            new Souvenir(name, java.math.BigDecimal.valueOf(price), date,
                (long) manufacturerId),
            id);
    }

    private void addSouvenir() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть назву сувеніру:");
        String name = scanner.nextLine();

        System.out.println("Введіть ціну сувеніру:");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Введіть дату випуску сувеніру:");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.println("Введіть Id виробника:");
        int manufacturerId = scanner.nextInt();

        souvenirService.save(
            new Souvenir(name, java.math.BigDecimal.valueOf(price), date,
                (long) manufacturerId));
    }

    private void deleteManufacturerAndSouvenirs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть Id виробника:");
        int id = scanner.nextInt();

        manufacturerService.delete((long) id);
    }

    private void searchManufacturersByPrice() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть ціну:");
        double price = scanner.nextDouble();

        manufacturerService.getAllCheaperThan(
                java.math.BigDecimal.valueOf(price))
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

        System.out.println("Введіть Id виробника:");
        Long id = (long) scanner.nextInt();
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

