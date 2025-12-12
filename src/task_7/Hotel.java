package task_7;

import java.io.Serializable;
import java.util.*;

public class Hotel implements Cloneable, Serializable {
    private int DAY;

    /*

    */

    // Массив: (id, Room)  |  гость хранится в комнате
    private final Map<Integer, Room> data = new LinkedHashMap<>();

    private final List<Service> services = new ArrayList<>();

    public final List<Cost> costs = new ArrayList<>();

    public Hotel() {
        // набор комнат и услуг по умолчанию(для тестов)
//        for (int i = 1; i < 5; i++) {
//            addRoom(i*5);
//        }
//
//        moveIntoRoom(1,5);
//        moveIntoRoom(2,10);
//        moveIntoRoom(3,15);

        System.out.println("| " + display(getGuests()) + " |");

        addService("уборка",10);
        addService("такси",20);
        addService("вечеринка",100);
    }

    private static class Cost implements Serializable {
        String name; // number/name
        String type; // room/service
        int cost;

        public Cost(Room room) {
            name = String.valueOf(room.getNumber());
            type = "room";
            cost = room.getCost();
        }

        public Cost(Service service) {
            name = service.getName();
            type = "service";
            cost = service.getCost();
        }

        public int getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return name + "=" + cost;
        }
    }

    public String getCosts(String value) {
        String data = "";

        switch (value){
            case "тип":
                List<Cost> rooms = new ArrayList<>();
                List<Cost> services = new ArrayList<>();

                for (Cost cost : costs) {
                    if (cost.type.equals("room")) {
                        rooms.add(cost);
                    } else {
                        services.add(cost);
                    }
                }

                data += getCosts(rooms, services);

                break;

            case "цена":
                List<Cost> sorted = new ArrayList<>(costs);
                sorted.sort(Comparator.comparing(Cost::getCost));

                data = getCosts(sorted);
                break;

            default:
                System.out.println("Некорректный параметр сортировки цен");
        }
        return data;
    }

    private String getCosts(List<Cost> sorted) {
        String data = "";
        for (Cost cost : sorted) {
            if (cost.type.equals("room")){
                data += String.format("\tКомната №%-2s | Cost: %d\n",cost.name, cost.cost);
            } else {
                data += String.format("\t%-11s | Cost: %d\n", cost.name, cost.cost);
            }
        }
        return data;
    }

    private String getCosts(List<Cost> rooms, List<Cost> services) {
        String data = "Type \"Room\":\n";
        for (Cost cost : rooms) {
            data += String.format("\tКомната №%-2s | Cost: %d\n",cost.name, cost.cost);
        }
        data += "\nType \"Service\":\n";
        for (Cost cost : services) {
            data += String.format("\t%-11s | Cost: %d\n", cost.name, cost.cost);
        }
        return data;
    }

// === Работа с комнатами ===
    public void addRoom(int cost) {
        Room room = new Room(findAvailableNumber() ,cost);
        data.put(room.getNumber(), room);
    }

    public void addRoom(int id, Room room) {
        data.put(id, room);
    }

    private int findAvailableNumber() {
        int number = 1;
        boolean flag;
        while (true){
            flag = true;
            for (Room room : data.values()) {
                if (room.getNumber() == number) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return number;
            }
            number++;
        }
    }

    public List<Room> getRooms() {
        return new ArrayList<>(data.values());
    }

    public List<Room> getRooms(String value /* номер/цена/вместимость/звёзды */) {
        List<Room> sorted = getRooms();

        switch (value) {
            case "номер":
                break;
            case "вместимость":
                sorted.sort(Comparator.comparing(Room::getCapacity));
                break;
            case "цена":
                sorted.sort(Comparator.comparing(Room::getCost));
                break;
            case "звёзды":
                sorted.sort(Comparator.comparing(Room::getStars));
                break;
            default:
                System.out.println("Некорректное значение сортировки комнат");
                return null;
        }
        return sorted;

    }

    public Room getRoom(int id) {
        for (Room room : getRooms()) {
            if (getId(room) == id) {
                return room;
            }
        }
        return null;
    }

    private void addGuest(Room room, Guest guest) {
        room.setGuest(guest);
    }

    private void moveIntoRoom(Room room, int time) {
        if (room.getStatus().equals("empty")) {
            room.setStatusOccupied(time + DAY);
            addGuest(room, new Guest(time + DAY));
            System.out.println("Заселение в комнату №" + room.getNumber() + ".");
        } else {
            System.out.println("Заселение в комнату №" + room.getNumber() + " не возможно.");
        }
    }

    public void moveIntoRoom(int roomNumber, int time) {
        Room room = getRoom(roomNumber);
        moveIntoRoom(room, time);
        room.getGuest().setRoomPay(room.getCost());
    }

    public List<Guest> getGuests() {
        List<Guest> guests = new ArrayList<>();
        for (Room room : data.values()) {
            if (room.getGuest() != null) {
                guests.add(room.getGuest());
            }
        }
        return guests;
    }

    public List<Guest> getGuests(String value /* name, moveOutDay */) {
        List<Guest> sorted = new ArrayList<>(getGuests());
        switch (value) {
            case "name":
                sorted.sort(Comparator.comparing(Guest::getName));

            case "moveOutDay":
                sorted.sort(Comparator.comparing(Guest::getMoveOutDay));
        }
        return sorted;
    }


// === Работа с услугами ===
    public void addService(String name, int cost) {
        services.add(new Service(name, cost));
    }

    public String displayServices() {
        String data = "";
        if (services.isEmpty()) {
            return "Отель не предоставляет услуг.";
        }
        for (Service el : services){
            data += String.format("Услуга №%-2d — %-15s | Цена = $ %-3d\n", services.indexOf(el) + 1, el, el.getCost());
        }
        return data;
    }

// === Универсальная работа с ценами ===
    public void setCost(String name, int cost) {
        List<Room> rooms = getRooms();
        try {
            int number = Integer.parseInt(name);
            try {
                rooms.get(number-1).setCost(cost);
            } catch (IndexOutOfBoundsException e){
                System.out.println("Комната №" + number + " не существует.");
            }
        } catch (NumberFormatException e) {
            for (Service service : services) {
                if (service.getName().equals(name)) {
                    service.setCost(cost);
                    return;
                }
            }
            System.out.println("Услуга \"" + name + "\" не существует.");
        }
    }

    public void setCost(int roomNumber, int cost) {
        List<Room> rooms = getRooms();
        try {
            rooms.get(roomNumber).setCost(cost);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Комната №" + roomNumber + " не существует.");
        }
    }


// === Синхронизация данных ===

    public void update() {
        update(DAY);
    }

    public void update(int day) {
        for (int i = DAY; i <= day; i++) {
            roomsUpdate(i);
            costsUpdate();
        }
    }

    private void roomsUpdate(int day) {
        for (Room room : getRooms()) {
            if (room.getUpdateDay() == day) {
                room.update();
            }
        }
    }

    private void costsUpdate() {
        costs.clear();
        for (Room room : getRooms()) {
            costs.add(new Cost(room));
        }
        for (Service service : services){
            costs.add(new Cost(service));
        }
    }

    public int getDAY() {
        return DAY;
    }

    public void nextDAY() {
        DAY++;
    }

    public List<Room> getEmptyRooms() {
        List<Room> emptyRooms = new ArrayList<>();
        for (Room room: getRooms()) {
            if (room.getStatus().equals("empty")){
                emptyRooms.add(room);
            }
        }
        return emptyRooms;
    }

    public List<Room> getEmptyRooms(String value /* номер/цена/вместимость/звёзды */) {
        List<Room> sorted = getEmptyRooms();
        switch (value) {
            case "номер":
                break;

            case "вместимость":
                sorted.sort(Comparator.comparing(Room::getCapacity));
                break;

            case "цена":
                sorted.sort(Comparator.comparing(Room::getCost));
                break;

            case "звёзды":
                sorted.sort(Comparator.comparing(Room::getStars));
                break;

            default:
                System.out.println("Некорректное значение сортировки комнат");
        }
        return sorted;
    }

    public int getEmptyRoomsAmount() {
        int counter = 0;
        for (Room room: getRooms()) {
            if (room.getStatus().equals("empty")){
                counter++;
            }
        }
        return counter;
    }

    public int getGuestsAmount() {
        return getGuests().size();
    }

    @Override
    public Hotel clone() {
        try {
            return (Hotel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // гость по номеру КОМНАТЫ
    public Guest getGuest(int roomNumber) {
        List<Guest> guests = getGuests();
        if ( roomNumber > 0 && roomNumber < guests.size()+1) {
            return guests.get(roomNumber -1);
        }
        return null;
    }

    public Guest getGuest(Room room) {
        return room.getGuest();
    }

    public boolean serviceExists(String serviceName) {
        for (Service el : services) {
            if (el.getName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    public Service getService(String serviceName) {
        for (Service el : services) {
            if (el.getName().equals(serviceName)) {
                return el;
            }
        }
        return null;
    }

    public int getId(Room room) {
        for (Map.Entry<Integer, Room> set : data.entrySet()) {
            if (set.getValue().equals(room)){
                return set.getKey();
            }
        }
        return -1;
    }

    public int getId(Guest guest) {
        for (Map.Entry<Integer, Room> set : data.entrySet()) {
            Guest currentGuest = set.getValue().getGuest();
            if (currentGuest != null) {
                if (currentGuest.equals(guest)){
                    return set.getKey();
                }
            }
        }
        return -1;
    }

    public String display(List<? extends Displayable> list) {
        List<String> data = new ArrayList<>();
        for (Displayable element : list) {
            data.add(element.display());
        }
        return data.toString();
    }

    public String display(Displayable obj) {
        return obj.display();
    }
}
