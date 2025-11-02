package task_5.T1.Menu;

public class Navigator {
    private Menu currentMenu;

    public Navigator(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        if (currentMenu != null) {
            System.out.println("Меню: " + currentMenu.getName());
            MenuItem[] items = currentMenu.getMenuItems();
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i].getTitle());
            }
        }
    }

    public void navigate(Integer index) {
        if (currentMenu != null && index != null && index > 0 && index <= currentMenu.getMenuItems().length) {
            MenuItem selectedItem = currentMenu.getMenuItems()[index - 1];
            selectedItem.doAction(); // Выполняем действие
            if (selectedItem.getNextMenu() != null) {
                this.currentMenu = selectedItem.getNextMenu(); // Переходим к следующему меню
            }
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
