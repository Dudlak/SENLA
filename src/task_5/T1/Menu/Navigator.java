package task_5.T1.Menu;

import task_5.T1.Hotel;

import java.util.Stack;

public class Navigator {
    private Menu currentMenu;
    private Stack<Menu> menuHistory; // Стек для хранения истории

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
                System.out.println("Back to " + currentMenu.getName());
            } else {
                System.out.println("It is root menu. You can`t go back.");
            }
            return;
        }

        if (index > 0 && index <= items.length) {
            MenuItem selectedItem = items[index - 1];
            selectedItem.doAction(); // Выполняем действие

            // Если есть следующее меню — запоминаем текущее и переходим
            if (selectedItem.getNextMenu() != null) {
                menuHistory.push(currentMenu); // Сохраняем текущее меню в историю
                currentMenu = selectedItem.getNextMenu();
            }
        } else {
            System.out.println("Incorrect input.");
        }
    }

    public void setCurrentMenu(Menu rootMenu) {
        currentMenu = rootMenu;
    }
}