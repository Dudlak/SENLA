package task_7.Menu;

import java.util.Scanner;

public class MenuController {
    private Builder builder;
    private Navigator navigator;
    public Scanner scanner;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        builder.buildMenu(); // Строим меню
        navigator.setCurrentMenu(builder.getRootMenu()); // Устанавливаем корневое меню

        scanner = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                builder.update();
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
}