package task_5.T1.Menu;

import task_5.T1.Hotel;

import java.util.Scanner;

public class Builder {
    Scanner scanner = new Scanner(System.in);

    private final Hotel hotel;
    private Menu rootMenu;

    public Builder(Hotel hotel) {
        this.hotel = hotel;
    }

    public void buildMenu() {
        // Здесь должна быть логика построения меню.
        // Для примера создадим простое меню.
        MenuItem nextDay = new MenuItem("nextDay(+)", new IAction() {
            @Override
            public void execute() {
                hotel.nextDAY();
            }
        }, null);

        MenuItem addRoom = new MenuItem("addRoom", new IAction() {
            @Override
            public void execute() {
                System.out.print("Cost: ");
                int number = scanner.nextInt();
                hotel.addRoom(number);
                System.out.println("Room added.");
            }
        }, null);

        MenuItem[] items = {nextDay, addRoom};
        this.rootMenu = new Menu("Главное меню", items);
    }

    public Menu getRootMenu() {
        return rootMenu;
    }
}
