package task.Menu;

import task.Annotations.ConfigProperty;
import task.Hotel.Hotel;
import task.Saver;

import java.util.Scanner;

import static task.Main.config;
import static task.Main.hotel;

public class MenuController {
    private Builder builder;
    private Navigator navigator;
    public Scanner scanner;

    @ConfigProperty(configFileName = "config.properties", propertyName = "db.autoLoadSave")
    boolean autoload;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;

        scanner = new Scanner(System.in);
    }

    public void run() {
        loadConfiguration();
        builder.buildMenu();
        autoLoad();
        navigator.setCurrentMenu(builder.getRootMenu());

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
        if (autoload) {
            hotel = (Hotel) Saver.importState();
        }
    }

    public void loadConfiguration() {
        System.out.println("Загрузить настройки из файла? (д/н)");
        String choice = scanner.next().toLowerCase();
        if (choice.equals("д")){
            config.loadConfiguration(hotel);
            config.loadConfiguration(this);
        } else if (choice.equals("н")) {
            config.loadDefaultConfiguration(hotel);
            config.loadDefaultConfiguration(this);

        } else {
            System.out.println("Неверный ввод. Установлены значения по умолчанию");
            config.loadDefaultConfiguration(hotel);
            config.loadDefaultConfiguration(this);
        }
    }
}