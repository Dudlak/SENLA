package task_7.Menu;

import task_7.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import static task_7.Main.config;

public class Builder {
    Scanner scanner = Navigator.scanner;

    private Hotel hotel;
    private Menu rootMenu;

    public Builder(Hotel hotel) {
        this.hotel = hotel;
    }

    public void buildMenu() {

        //все элементы меню
        MenuItem nextDay = new MenuItem("Следующий день", new IAction() {
            @Override
            public void execute() {
                hotel.nextDAY();
            }
        }, null);

        MenuItem checkDay = new MenuItem("Количество пустых номеров по дате", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите день для проверки: ");
                int number = scanner.nextInt();
                if (number < Hotel.getDAY()) {
                    System.out.println("Не вижу прошлое(");
                } else {
                    Hotel newHotel = hotel.clone();
                    newHotel.update(number);
                    System.out.println("Количество пустых номеров в эту дату: " + newHotel.getEmptyRoomsAmount());
                }
            }
        }, null);

        MenuItem costs = new MenuItem("Цены", new IAction() {
            @Override
            public void execute() {
                System.out.println(hotel.getCosts("тип"));
            }
        }, null);

        MenuItem costsSorted = new MenuItem("Цены(sorted)", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите параметр сортировки (тип/цена): ");
                String command = scanner.next();
                System.out.println(hotel.getCosts(command));
            }
        }, null);

        MenuItem addRoom = new MenuItem("Добавить комнату", new IAction() {
            @Override
            public void execute() {
                System.out.print("Цена: ");
                int number = scanner.nextInt();
                hotel.addRoom(number);
                System.out.println("Комната добавлена");
            }
        }, null);

        MenuItem room = new MenuItem("Комната", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите номер комнаты: ");
                int number = scanner.nextInt();
                Room room = hotel.getRoom(number);
                if (room != null) {
                    System.out.println(room);
                }
            }
        }, null);

        MenuItem roomHistory = new MenuItem("История постояльцев номера", new IAction() {
// проверить (кажется выводит 2 из 3 постояльцев)
            @Override
            public void execute() {
                System.out.print("Введите номер комнаты: ");
                int number = scanner.nextInt();
                Room room = hotel.getRoom(number);
                if (room != null) {
                    System.out.print("История: [");
                    Iterator<String> iterator = Arrays.stream(room.getGuestsHistory()).iterator();
                    String next = iterator.next();
                    if (next != null) {
                        System.out.print(next);
                    }
                    if (iterator.hasNext()) {
                        next = iterator.next();
                        if (next != null) {
                            System.out.println("; "+ next);
                        }
                    }
                    System.out.println("]");
                }
            }
        }, null);

        MenuItem rooms = new MenuItem("Все комнаты", new IAction() {
            @Override
            public void execute() {
                for (Room room : hotel.getRooms()) {
                    System.out.printf("%-65s | id: %d\n", room.display(), hotel.getId(room));
                }
            }
        }, null);

        MenuItem roomsSorted = new MenuItem("Все комнаты(sorted)", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите параметр сортировки(номер/цена/вместимость/звёзды): ");
                String command = scanner.next();
                for (Room room : hotel.getRooms(command)) {
                    System.out.printf("%-65s | id: %d\n", room.display(), hotel.getId(room));
                }
            }
        }, null);

        MenuItem moveInto = new MenuItem("Заселение", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите номер комнаты: ");
                int number = scanner.nextInt();

                System.out.print("Введите длительность приезда: ");
                int time = scanner.nextInt();

                hotel.moveIntoRoom(number, time);
            }
        }, null);

        MenuItem emptyRooms = new MenuItem("Все пустые комнаты", new IAction() {
            @Override
            public void execute() {
                System.out.println("Список пустых комнат: ");
                System.out.println(hotel.getEmptyRooms());
            }
        }, null);

        MenuItem emptyRoomsSorted = new MenuItem("Все пустые комнаты(sorted)", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите параметр сортировки(номер/цена/вместимость/звёзды): ");
                String command = scanner.next();
                System.out.println(hotel.getEmptyRooms(command));
            }
        }, null);

        MenuItem emptyRoomsAmount = new MenuItem("Количество пустых комнат", new IAction() {
            @Override
            public void execute() {
                System.out.println("Количество пустых комнат: " + hotel.getEmptyRoomsAmount());
            }
        }, null);

        MenuItem guest = new MenuItem("Гость", new IAction() {
            @Override
            public void execute() {
                try {
                    System.out.print("Введите id гостя: ");
                    int id = scanner.nextInt();
                    Guest guest = hotel.getRoom(id).getGuest();
                    if (guest != null) {
                        System.out.println(guest);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Неверный id");
                }
            }
        }, null);

        MenuItem guestPay = new MenuItem("Плата гостя", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите номер гостя: ");
                int number = scanner.nextInt();
                Guest guest = hotel.getGuest(number);
                if (guest != null) {
                    System.out.println(guest.getName() + " должен заплатить " + guest.getPay() + "$");
                }
            }
        }, null);

        MenuItem guestServices = new MenuItem("История услуг гостя", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите id гостя: ");
                int id = scanner.nextInt();
                Guest guest = hotel.getRoom(id).getGuest();
                if (guest != null) {
                    System.out.println(guest.getServices());
                }
            }
        }, null);

        MenuItem guestServicesSorted = new MenuItem("История услуг гостя(sorted)", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите id гостя: ");
                int id = scanner.nextInt();
                Guest guest = hotel.getRoom(id).getGuest();
                if (guest != null) {
                    System.out.print("Введите параметр сортировки(цена/дата): ");
                    String command = scanner.nextLine();
                    if (command.equals("цена") || command.equals("дата")) {
                        System.out.println(guest.getServices(command));
                    }
                }
            }
        }, null);

        MenuItem guestUseService = new MenuItem("Использование сервиса гостем", new IAction() {
            @Override
            public void execute() {
                System.out.print("Введите id гостя: ");
                int id = scanner.nextInt();
                scanner.nextLine(); //сброс буфера
                System.out.print("Введите название услуги: ");
                String command = scanner.nextLine();
                if (hotel.serviceExists(command)) {
                    hotel.getRoom(id).getGuest().useService(hotel.getService(command));
                } else {
                    System.out.println("Услуга не найдена.");
                }
            }
        }, null);

        MenuItem guests = new MenuItem("Все гости", new IAction() {
            @Override
            public void execute() {
                for (Guest guest : hotel.getGuests()) {
                    System.out.printf("%-23s | id: %d\n", guest.display(), hotel.getId(guest));
                }
            }
        }, null);

        MenuItem guestsAmount = new MenuItem("Количество гостей", new IAction() {
            @Override
            public void execute() {
                System.out.println("Количество гостей в отеле: " + hotel.getGuestsAmount());
            }
        }, null);

        MenuItem services = new MenuItem("Все услуги", new IAction() {
            @Override
            public void execute() {
                System.out.println(hotel.displayServices());
            }
        }, null);

        MenuItem saveAll = new MenuItem("Сохранить всё", new IAction() {
            @Override
            public void execute() {
                Saver.exportState(hotel);
            }
        }, null);

        MenuItem clearSaves = new MenuItem("Удалить все сохранения", new IAction() {
            @Override
            public void execute() {
                System.out.println("Сохранения будут безвозвратно утеряны. Уверены? (д/н)");
                String choice = scanner.next().toLowerCase();
                if (choice.equals("д")){
                    Saver.clear();
                } else if (choice.equals("н")) {
                    System.out.println("Удаление отменено.");
                } else {
                    System.out.println("Неверный ввод. Удаление отменено.");
                }
            }
        }, null);

        MenuItem loadSaves = new MenuItem("Загрузить все сохранения", new IAction() {
            @Override
            public void execute() {
                hotel = (Hotel) Saver.importState();
            }
        }, null);

        //все варианты MenuItem[] для подменю
        MenuItem[] roomItems = {addRoom, room, roomHistory, rooms, roomsSorted, moveInto, emptyRooms, emptyRoomsSorted, emptyRoomsAmount};
        MenuItem[] guestItems = {moveInto, guest, guestPay, guestServices, guestServicesSorted, guestUseService, guests, guestsAmount};
        MenuItem[] saveItems = {saveAll, clearSaves, loadSaves};


        //команды переходов в подменю
        MenuItem guestMenu = new MenuItem("Управление гостями".toUpperCase(), new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Управление гостями", guestItems));

        MenuItem roomMenu = new MenuItem("Управление комнатами".toUpperCase(), new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Управление комнатами", roomItems));

        MenuItem saveMenu = new MenuItem("Управление сохранениями".toUpperCase(), new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Управление сохранениями", saveItems));

        //MenuItem[] для полного меню
        MenuItem[] fullItems = {nextDay, checkDay, costs, costsSorted, addRoom, room,
                roomHistory, rooms, roomsSorted, moveInto, emptyRooms, emptyRoomsSorted,
                emptyRoomsAmount, guest, guestPay, guestServices, guestServicesSorted,
                guestUseService, guests, guestsAmount, services, saveAll, clearSaves, loadSaves};

        //переход для полного меню
        MenuItem fullMenu = new MenuItem("Полное меню".toUpperCase(), new IAction() {
            @Override
            public void execute() {
            }
        }, new Menu("Полное меню", fullItems));

        //MenuItem[] для главного меню
        MenuItem[] mainItems = {nextDay, checkDay, costs, costsSorted, services, fullMenu, guestMenu, roomMenu, saveMenu};

        this.rootMenu = new Menu("Главное меню", mainItems);
    }

    public void update() {
        hotel.update();
    }

    public Menu getRootMenu() {
        return rootMenu;
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
