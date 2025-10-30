package task_4.T1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static task_4.T1.Main.DAY;

public class Hotel implements Cloneable{

    private List<Guest> guests = new ArrayList<>();
    private int guestsAmount;

    private List<Room> rooms = new ArrayList<>();
    private List<Room> emptyRooms = new ArrayList<>();
    private int emptyRoomsAmount;

    private final List<Service> services = new ArrayList<>();

    public final List<Cost> costs = new ArrayList<>();

    private class Cost {
        String name; //number, name
        String type; //room, service
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
            case "type":
                List<Cost> rooms = new ArrayList<>();
                List<Cost> services = new ArrayList<>();

                for (Cost cost : costs) {
                    if (cost.type.equals("room")) {
                        rooms.add(cost);
                    } else {
                        services.add(cost);
                    }
                }

                data += "Type \"Room\":\n";
                for (Cost cost : rooms) {
                    data += "\tКомната №" + cost.name + " | Cost: " + cost.cost + "\n";
                }
                data += "\nType \"Service\":\n";
                for (Cost cost : services) {
                    data += "\t" + cost.name + " | Cost: " + cost.cost + "\n";
                }
                break;

            case "cost":
                List<Cost> sorted = new ArrayList<>(costs);
                sorted.sort(Comparator.comparing(Cost::getCost));

                for (Cost cost : sorted) {
                    if (cost.type.equals("room")){
                        data += "\tКомната №" + cost.name + " | Cost: " + cost.cost + "\n";
                    } else {
                        data += "\t" + cost.name + " | Cost: " + cost.cost + "\n";
                    }
                }
                break;
        }
        return data;
    }

    // === Работа с комнатами ===
    public void addRoom(int cost) {
        rooms.add(new Room(rooms.size()+1 ,cost));
    }

    public String getRooms() {
        return getRooms(rooms);
    }

    public String getRooms(List<Room> thisRooms) {
        String data = "";
        if (thisRooms.isEmpty()) {
            return "Здесь пусто.";
        }
        for (Room el : thisRooms){
            data += el.display() + "\n";
        }
        return data;
    }

    public String getRooms(String value /* number, capacity, cost, stars */) {
        List<Room> sorted = new ArrayList<>(rooms);

        switch (value) {
            case "number":
                return getRooms();

            case "capacity":
                sorted.sort(Comparator.comparing(Room::getCapacity));
                return getRooms(sorted);

            case "cost":
                sorted.sort(Comparator.comparing(Room::getCost));
                return getRooms(sorted);

            case "stars":
                sorted.sort(Comparator.comparing(Room::getStars));
                return getRooms(sorted);

            default:
                return "Incorrect sorting value";
        }
    }

    public Room getRoom(int number) {
        return rooms.get(number-1);
    }

    public String getGuests(List<Guest> guests) {
        String data = "";
        if (guests.isEmpty()) {
            return "В отеле нет посетителей";
        } else {
            for (Guest guest : guests) {
                data += guest.getName() + " из комнаты №" + guest.getRoomNumber() +"\n";
            }
            return data;
        }
    }

    public String getGuests() {
        return getGuests(guests);
    }


    public String getGuests(String value /* name, moveOutDay */) {
        List<Guest> sorted = new ArrayList<>(guests);
        switch (value) {
            case "name":
                sorted.sort(Comparator.comparing(Guest::getName));

            case "moveOutDay":
                sorted.sort(Comparator.comparing(Guest::getMoveOutDay));
        }
        return getGuests(sorted);
    }

    //прописать возможные ошибки
    public void setRoomRepair(int number) {
        try {
            rooms.get(number-1).setStatusRepair();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Этой комнаты не существует");
        }
    }

    public void setRoomService(int number) {
        try {
            rooms.get(number-1).setStatusService();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Этой комнаты не существует");
        }

    }

    // === Работа с услугами ===
    private void moveIntoRoom(Room room, int time) {
        if (room.getStatus().equals("empty")) {
            room.setStatusOccupied(time + DAY);
            room.setGuest(new Guest(time + DAY));
            System.out.println("Заселение в комнату №" + (rooms.indexOf(room)+1) + ".");
        } else {
            System.out.println("Заселение в комнату №" + (rooms.indexOf(room)+1) + " не возможно.");
        }
    }

    public void moveIntoRoom(int roomNumber, int time) {
        moveIntoRoom(rooms.get(roomNumber-1), time);
    }

    private void moveOutRoom(Room room) {
        if (room.getStatus().equals("occupied")) {
            room.setStatusEmpty();
            System.out.println("Выселение из комнаты №" + (rooms.indexOf(room)+1) + ".");
        } else {
            System.out.println("Выселение из комнаты №" + (rooms.indexOf(room)+1) + " не возможно.");
        }
    }

    public void moveOutRoom(int roomNumber) {
        moveOutRoom(rooms.get(roomNumber-1));
    }

    public void addService(String name, int cost) {
        services.add(new Service(name, cost));
    }

    public String getServices() {
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
        try {
            rooms.get(roomNumber).setCost(cost);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Комната №" + roomNumber + " не существует.");
        }
    }


    // === Синхронизация данных ===

    public void update(int day) {
        for (int i = DAY; i <= day; i++) {
            roomsUpdate(i);
            guestsUpdate();
            emptyUpdate();
            costsUpdate();
        }
    }

    private void emptyUpdate() {
        List<Room> empty = new ArrayList<>();
        int amount = 0;
        for (Room room : rooms) {
            if (room.getStatus().equals("empty")) {
                empty.add(room);
                amount++;
            }
        }
        emptyRooms = empty;
        emptyRoomsAmount = amount;
    }

    private void guestsUpdate() {
        List<Guest> guests = new ArrayList<>();
        int amount = 0;
        for (Room room : rooms) {
            Guest guest = room.getGuest();
            if (!guests.contains(guest) && guest != null) {
                guests.add(guest);
                amount++;
            }
        }
        this.guests = guests;
        guestsAmount = amount;
    }

    private void roomsUpdate(int day) {
        for (Room room : rooms) {
            if (room.getUpdateDay() == day) {
                room.update();
            }
        }
    }

    private void costsUpdate() {
        costs.clear();
        for (Room room : rooms) {
            costs.add(new Cost(room));
        }
        for (Service service : services){
            costs.add(new Cost(service));
        }
    }


    public String getEmptyRooms() {
        return getRooms(emptyRooms);
    }

    public String getEmptyRooms(String value /* number, capacity, cost, stars */) {
        List<Room> sorted = new ArrayList<>(emptyRooms);
        switch (value) {
            case "number":
                return getRooms();

            case "capacity":
                sorted.sort(Comparator.comparing(Room::getCapacity));
                return getRooms(sorted);

            case "cost":
                sorted.sort(Comparator.comparing(Room::getCost));
                return getRooms(sorted);

            case "stars":
                sorted.sort(Comparator.comparing(Room::getStars));
                return getRooms(sorted);

            default:
                return "Incorrect sorting value";
        }
    }

    public int getEmptyRoomsAmount() {
        return emptyRoomsAmount;
    }

    public int getGuestsAmount() {
        return guestsAmount;
    }

    @Override
    public Hotel clone() {
        try {
            return (Hotel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String getRoomHistory(int number) {
        return rooms.get(number-1).getGuestsHistory();
    }

    public Guest getGuest(int number) {
        return guests.get(number-1);
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
}
