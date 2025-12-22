package task.Menu;

import task.Hotel.Hotel;
import task.Saver;

import java.util.Scanner;

import static task.Main.config;
import static task.Main.hotel;

public class MenuController {
    private Builder builder;
    private Navigator navigator;
    public Scanner scanner;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        loadConfig();
        builder.buildMenu();// Строим меню
        autoLoad();
        navigator.setCurrentMenu(builder.getRootMenu()); // Устанавливаем корневое меню

        scanner = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                update();
                navigator.printMenu();
                System.out.print("Выберите пункт: ");
                choice = scanner.nextInt();
                System.out.println();
                navigator.navigate(choice);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                scanner.next();
            }

            System.out.println(); // Разделитель
        }
    }

    public void update() {
        hotel.update();
    }

    public void autoLoad() {
        if (config.getBoolean("db.autoLoadSave")) {
            System.out.println("Автозагрузка сохранения:");
            hotel = (Hotel) Saver.importState();
        }
    }

    public void loadConfig() {
        System.out.println("Загрузить настройки из файла? (д/н)");
        String choice = scanner.next().toLowerCase();
        if (choice.equals("д")){
            config.loadConfig();
        } else if (choice.equals("н")) {
            config.loadDefaultProperties();
        } else {
            System.out.println("Неверный ввод. Установлены значения по умолчанию");
            config.loadDefaultProperties();
        }
    }
}