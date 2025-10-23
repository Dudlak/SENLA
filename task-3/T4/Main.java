package T4;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Hotel hotel = new Hotel();

        Room room1 = new Room(random.nextInt(100, 300));
        Room room2 = new Room(random.nextInt(100, 300));
        Room room3 = new Room(random.nextInt(100, 300));
        Room room4 = new Room(random.nextInt(100, 300));

        room2.setStatusRepair();
        room3.setStatusOccupied();
        room4.setStatusService();

        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);
        hotel.addRoom(room4);

        hotel.addService(new Service("еда в номер",10));
        hotel.addService(new Service("срочная уборка",20));
        hotel.addService(new Service("такси",30));

        System.out.println(hotel.getRooms());
        System.out.println(hotel.getServices());
    }
}
