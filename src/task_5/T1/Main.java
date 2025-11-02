package task_5.T1;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import task_5.T1.Menu.*;

/*
    переписать с использованием Menu
*/

public class Main {
    public static Random random = new Random();
    public static int DAY = 0;

    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        Builder builder = new Builder(hotel);
        Navigator navigator = new Navigator(null);
        MenuController controller = new MenuController(builder, navigator);

        controller.run();
    }

    public static void old_main(String[] args) {
        int number;
        Room room;
        Guest guest;

        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        //стартовый набор комнат
        for (int i = 1; i <= 10; i++) {
            hotel.addRoom(random.nextInt(10,100));
        }

        //стартовый набор услуг
        hotel.addService("еда в номер",10);
        hotel.addService("срочная уборка",20);
        hotel.addService("такси",30);

        String command = "";

        System.out.println(
                """
                    Available commands:
                
                    nextDay(+)
                    checkDay  -  Список номеров которые будут свободны по определенной дате в будущем
                    costs
                    costsSorted
                
                   Room:
                    addRoom
                    room
                    roomHistory  -  Посмотреть 3-х последних постояльцев номера и даты их пребывания
                    rooms
                    roomsSorted
                    moveInto
                    moveOut
                    emptyRooms
                    emptyRoomsSorted
                    emptyRoomsAmount
                
                   Guest:
                    guest
                    guestPay  -  Сумма   оплаты за номер которую должен оплатить постоялец
                    guestServices  -  Посмотреть список услуг постояльца и их цену
                    guestServicesSorted
                    guestUseService
                    guests
                    guestsAmount
                
                   Service:
                    services
                """
        );

        while (!command.equals("stop")) {
            try {
                hotel.update(DAY);
                System.out.println("\nDate: day " + DAY);
                System.out.print("Enter: ");
                command = scanner.next();
                System.out.println();

                switch (command) {
                    case "nextDay", "+":
                        DAY++;
                        break;

                    case "checkDay":
                        System.out.println("Enter the number of day to check: ");
                        number = scanner.nextInt();
                        if (number < DAY) {
                            System.out.println("Can`t see history.");
                        } else {
                            Hotel newHotel = hotel.clone();
                            newHotel.update(number);
                            System.out.println("Amount of empty rooms at this date: " + newHotel.getEmptyRoomsAmount());
                        }
                        break;

                    case "addRoom":
                        System.out.print("Cost: ");
                        number = scanner.nextInt();
                        hotel.addRoom(number);
                        System.out.println("Room added.");
                        break;

                    case "rooms":
                        System.out.println(hotel.getRooms());
                        break;

                    case "roomsSorted":
                        System.out.print("Enter value of sorting (number/cost/capacity): ");
                        command = scanner.next();
                        System.out.println(hotel.getRooms(command));
                        break;

                    case "room":
                        System.out.print("Enter room number: ");
                        number = scanner.nextInt();
                        room = hotel.getRoom(number);
                        if (room != null) {
                            System.out.println(room);
                        }
                        break;

                    case "roomHistory":
                        System.out.print("Enter room number: ");
                        number = scanner.nextInt();
                        room = hotel.getRoom(number);
                        if (room != null) {
                            System.out.println("History: " + room.getGuestsHistory());
                        }
                        break;

                    case "services":
                        System.out.println(hotel.getServices());
                        break;

                    case "guest":
                        System.out.print("Enter the number of guest: ");
                        number = scanner.nextInt();
                        guest = hotel.getGuest(number);
                        if (guest != null) {
                            System.out.println(guest);
                        }
                        break;

                    case "guestPay":
                        System.out.print("Enter the number of guest: ");
                        number = scanner.nextInt();
                        guest = hotel.getGuest(number);
                        if (guest != null) {
                            System.out.println(guest.getName() + " should pay " + guest.getPay() + "$");
                        }
                        break;

                    case "guestServices":
                        System.out.print("Enter the number of guest: ");
                        number = scanner.nextInt();
                        guest = hotel.getGuest(number);
                        if (guest != null) {
                            System.out.println(guest.getServices());
                        }
                        break;

                    case "guestServicesSorted":
                        System.out.print("Enter the number of guest: ");
                        number = scanner.nextInt();

                        guest = hotel.getGuest(number);
                        if (guest != null) {
                            System.out.print("Enter value of sorting (cost/date): ");
                            command = scanner.next();
                            if (command.equals("cost") || command.equals("date")) {
                                System.out.println(guest.getServices(command));
                            }
                        }
                        break;

                    case "guestUseService":
                        System.out.print("Enter the number of guest: ");
                        number = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter the name of service: ");
                        command = scanner.nextLine();
                        if (hotel.serviceExists(command)) {
                            hotel.getGuest(number).useService(hotel.getService(command));
                        } else {
                            System.out.println("Service not found.");
                        }
                        break;

                    case "guests":
                        System.out.println(hotel.getGuests());
                        break;

                    case "guestsSorted":
                        System.out.print("Enter value of sorting (name/moveOutDay): ");
                        command = scanner.next();
                        System.out.println(hotel.getGuests(command));
                        break;

                    case "moveInto":
                        System.out.print("Enter room number: ");
                        number = scanner.nextInt();

                        System.out.print("Enter visite time: ");
                        int time = scanner.nextInt();

                        hotel.moveIntoRoom(number, time);
                        break;

                    case "moveOut":
                        //прописать ручное выселение
                        break;

                    case "emptyRoomsAmount":
                        System.out.println("Amount of empty rooms: " + hotel.getEmptyRoomsAmount());
                        break;

                    case "emptyRooms":
                        System.out.println("List of empty rooms: ");
                        System.out.println(hotel.getEmptyRooms());
                        break;

                    case "emptyRoomsSorted":
                        System.out.print("Enter value of sorting (number/cost/capacity): ");
                        command = scanner.next();
                        System.out.println(hotel.getEmptyRooms(command));
                        break;

                    case "guestsAmount":
                        System.out.println("Amount of guest in hotel: " + hotel.getGuestsAmount());
                        break;

                    case "costs":
                        System.out.println(hotel.getCosts("type"));
                        break;

                    case "costsSorted":
                        System.out.print("Enter value of sorting (type/cost): ");
                        command = scanner.next();
                        System.out.println(hotel.getCosts(command));
                        break;

                    default:
                        System.out.println("Invalid command[" + command + "]");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("It must be number.");
            }
        }
    }
}
