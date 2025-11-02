package task_5.T1.Menu;

import java.util.Scanner;

public class MenuController {
    private Builder builder;
    private Navigator navigator;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        builder.buildMenu(); // Строим меню
        navigator.setCurrentMenu(builder.getRootMenu()); // Устанавливаем корневое меню

        Scanner scanner = new Scanner(System.in);
        while (true) {
            builder.update();
            navigator.printMenu();
            System.out.print("Выберите пункт ('exit' для выхода): ");

            String input = scanner.nextLine().trim();
            System.out.println();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Выход из программы.");
                break;
            }

            try {
                int choice = Integer.parseInt(input);
                navigator.navigate(choice);
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Введите число или 'exit'.");
            }

            System.out.println(); // Разделитель
        }
    }
}