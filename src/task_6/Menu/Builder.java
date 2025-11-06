package task_6.Menu;

import task_6.Guest;
import task_6.Hotel;
import task_6.Room;

import java.util.Scanner;

public class Builder {
    Scanner scanner = new Scanner(System.in);

    private final Hotel hotel;
    private Menu rootMenu;

    public Builder(Hotel hotel) {
        this.hotel = hotel;
    }

    public void buildMenu() {

        //все элементы меню
        MenuItem nextDay = new MenuItem("nextDay", new IAction() {
            @Override
            public void execute() {
                hotel.nextDAY();
            }
        }, null);

        MenuItem checkDay = new MenuItem("checkDay", new IAction() {
            @Override
            public void execute() {
                System.out.println("Enter the number of day to check: ");
                int number = scanner.nextInt();
                if (number < hotel.getDAY()) {
                    System.out.println("Can`t see history.");
                } else {
                    Hotel newHotel = hotel.clone();
                    newHotel.update(number);
                    System.out.println("Amount of empty rooms at this date: " + newHotel.getEmptyRoomsAmount());
                }
            }
        }, null);

        MenuItem costs = new MenuItem("costs", new IAction() {
            @Override
            public void execute() {
                System.out.println(hotel.getCosts("type"));
            }
        }, null);

        MenuItem costsSorted = new MenuItem("costsSorted", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter value of sorting (type/cost): ");
                String command = scanner.next();
                System.out.println(hotel.getCosts(command));
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

        MenuItem room = new MenuItem("room", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter room number: ");
                int number = scanner.nextInt();
                Room room = hotel.getRoom(number);
                if (room != null) {
                    System.out.println(room);
                }
            }
        }, null);

        MenuItem roomHistory = new MenuItem("roomHistory", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter room number: ");
                int number = scanner.nextInt();
                Room room = hotel.getRoom(number);
                if (room != null) {
                    System.out.println("History: " + room.getGuestsHistory());
                }
            }
        }, null);

        MenuItem rooms = new MenuItem("rooms", new IAction() {
            @Override
            public void execute() {
                System.out.println(hotel.getRooms());
            }
        }, null);

        MenuItem roomsSorted = new MenuItem("roomsSorted", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter value of sorting (number/cost/capacity): ");
                String command = scanner.next();
                System.out.println(hotel.getRooms(command));
            }
        }, null);

        MenuItem moveInto = new MenuItem("moveInto", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter room number: ");
                int number = scanner.nextInt();

                System.out.print("Enter visite time: ");
                int time = scanner.nextInt();

                hotel.moveIntoRoom(number, time);
            }
        }, null);

        MenuItem emptyRooms = new MenuItem("emptyRooms", new IAction() {
            @Override
            public void execute() {
                System.out.println("List of empty rooms: ");
                System.out.println(hotel.getEmptyRooms());
            }
        }, null);

        MenuItem emptyRoomsSorted = new MenuItem("emptyRoomsSorted", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter value of sorting (number/cost/capacity): ");
                String command = scanner.next();
                System.out.println(hotel.getEmptyRooms(command));
            }
        }, null);

        MenuItem emptyRoomsAmount = new MenuItem("emptyRoomsAmount", new IAction() {
            @Override
            public void execute() {
                System.out.println("Amount of empty rooms: " + hotel.getEmptyRoomsAmount());
            }
        }, null);

        MenuItem guest = new MenuItem("guest", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter the number of guest: ");
                int number = scanner.nextInt();
                Guest guest = hotel.getGuest(number);
                if (guest != null) {
                    System.out.println(guest);
                }
            }
        }, null);

        MenuItem guestPay = new MenuItem("guestPay", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter the number of guest: ");
                int number = scanner.nextInt();
                Guest guest = hotel.getGuest(number);
                if (guest != null) {
                    System.out.println(guest.getName() + " should pay " + guest.getPay() + "$");
                }
            }
        }, null);

        MenuItem guestServices = new MenuItem("guestServices", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter the number of guest: ");
                int number = scanner.nextInt();
                Guest guest = hotel.getGuest(number);
                if (guest != null) {
                    System.out.println(guest.getServices());
                }
            }
        }, null);

        MenuItem guestServicesSorted = new MenuItem("guestServicesSorted", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter the number of guest: ");
                int number = scanner.nextInt();
                Guest guest = hotel.getGuest(number);
                if (guest != null) {
                    System.out.print("Enter value of sorting (cost/date): ");
                    String command = scanner.next();
                    if (command.equals("cost") || command.equals("date")) {
                        System.out.println(guest.getServices(command));
                    }
                }
            }
        }, null);

        MenuItem guestUseService = new MenuItem("guestUseService", new IAction() {
            @Override
            public void execute() {
                System.out.print("Enter the number of guest: ");
                int number = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter the name of service: ");
                String command = scanner.nextLine();
                if (hotel.serviceExists(command)) {
                    hotel.getGuest(number).useService(hotel.getService(command));
                } else {
                    System.out.println("Service not found.");
                }
            }
        }, null);

        MenuItem guests = new MenuItem("guests", new IAction() {
            @Override
            public void execute() {
                System.out.println(hotel.getGuests());
            }
        }, null);

        MenuItem guestsAmount = new MenuItem("guestsAmount", new IAction() {
            @Override
            public void execute() {
                System.out.println("Amount of guest in hotel: " + hotel.getGuestsAmount());
            }
        }, null);

        MenuItem services = new MenuItem("services", new IAction() {
            @Override
            public void execute() {
                System.out.println(hotel.getServices());
            }
        }, null);

        //все варианты MenuItem[] для подменю
        MenuItem[] roomItems = {addRoom, room, roomHistory, rooms, roomsSorted, moveInto, emptyRooms, emptyRoomsSorted, emptyRoomsAmount};
        MenuItem[] guestItems = {guest, guestPay, guestServices, guestServicesSorted, guestUseService, guests, guestsAmount};

        //команды переходов в подменю
        MenuItem guestMenu = new MenuItem("Guest menu", new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Guest menu", guestItems));

        MenuItem roomMenu = new MenuItem("Room menu", new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Room menu", roomItems));

        //MenuItem[] для полного меню
        MenuItem[] fullItems = {nextDay, checkDay, costs, costsSorted, addRoom, room, roomHistory, rooms, roomsSorted, moveInto, emptyRooms, emptyRoomsSorted, emptyRoomsAmount, guest, guestPay, guestServices, guestServicesSorted, guestUseService, guests, guestsAmount, services,  guestMenu, roomMenu};

        //переход для полного меню
        MenuItem fullMenu = new MenuItem("Full menu", new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Guest menu", fullItems));

        //MenuItem[] для главного меню
        MenuItem[] mainItems = {nextDay, checkDay, costs, costsSorted, services, fullMenu, guestMenu, roomMenu};

        this.rootMenu = new Menu("Main menu", mainItems);
    }

    public void update() {
        hotel.update();
    }

    public Menu getRootMenu() {
        return rootMenu;
    }
}
