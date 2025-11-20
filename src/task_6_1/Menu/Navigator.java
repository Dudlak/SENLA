package task_6_1.Menu;

import task_6_1.Hotel;

import java.util.Scanner;
import java.util.Stack;

public class Navigator {
    private Menu currentMenu;
    private Stack<Menu> menuHistory; // Стек для хранения истории
    public static Scanner scanner = new Scanner(System.in);

    public Navigator(Menu initialMenu) {
        this.currentMenu = initialMenu;
        this.menuHistory = new Stack<>();
    }

    public void printMenu() {
        if (currentMenu != null) {
            System.out.println("=== " + currentMenu.getName() + " ===\tDay: " + Hotel.getDAY());
            if (!menuHistory.isEmpty()) {
                System.out.println("0. Back\n"); // Добавляем опцию "Назад"
            }
            MenuItem[] items = currentMenu.getMenuItems();
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i].getTitle());
            }
        }
    }

    public void navigate(Integer index) {
        if (index == null || currentMenu == null) return;

        MenuItem[] items = currentMenu.getMenuItems();

        if (index == 0) {
            // Возврат назад
            if (!menuHistory.isEmpty()) {
                currentMenu = menuHistory.pop(); // Извлекаем предыдущее меню
                System.out.println("Возвращение в " + currentMenu.getName());
            } else {
                System.out.println("Это главное меню. Невозможно вернуться назад");
            }
            return;
        }

        try {
            MenuItem selectedItem = items[index - 1];
            selectedItem.doAction(); // Выполняем действие

            // Если есть следующее меню — запоминаем текущее и переходим
            if (selectedItem.getNextMenu() != null) {
                menuHistory.push(currentMenu); // Сохраняем текущее меню в историю
                currentMenu = selectedItem.getNextMenu();
            }
        } catch (Throwable e) {
            System.out.println("Неверный ввод (" + e + ")");
            scanner.next();
        }
    }

    public void setCurrentMenu(Menu rootMenu) {
        currentMenu = rootMenu;
    }
}