package T4;

import java.util.ArrayList;

public class Hotel {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Service> services = new ArrayList<>();

    // === Работа с комнатами ===

    public void addRoom(int cost) {
        rooms.add(new Room(cost));
    }

    public String getRooms() {
        String data = "";
        if (rooms.isEmpty()) {
            return "Отель пуст.";
        }
        for (Room el : rooms){
            data += String.format("Комната №%-2d — Цена = $ %-3d | Status = %s\n",rooms.indexOf(el) + 1,el.getCost(), el.getStatus());
        }
        return data;
    }

    //прописать возможные ошибки
    public void setRoomRepair(int number) {
        rooms.get(number+1).setStatusRepair();
    }

    public void setRoomOccupied(int number) {
        rooms.get(number+1).setStatusOccupied();
    }

    public void setRoomService(int number) {
        rooms.get(number+1).setStatusService();
    }

    // === Работа с услугами ===

    private void moveIntoRoom(Room room) {
        if (room.getStatus().equals("empty")) {
            room.setStatusOccupied();
            System.out.println("Заселение в комнату №" + (rooms.indexOf(room)+1) + ".");
        } else {
            System.out.println("Заселение в комнату №" + (rooms.indexOf(room)+1) + " не возможно.");
        }
    }

    public void moveIntoRoom(int roomNumber) {
        moveOutRoom(rooms.get(roomNumber-1));
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
                rooms.get(number).setCost(cost);
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
}
