package T4;

import java.util.ArrayList;

public class Hotel {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Service> services = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addService(Service service) {
        services.add(service);
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
}
