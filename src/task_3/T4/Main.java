package T4;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        /* Электронный администратор гостиницы */

        Random random = new Random();
        T4.Hotel hotel = new T4.Hotel();

        hotel.addRoom(random.nextInt(100, 300));
        hotel.addRoom(random.nextInt(100, 300));
        hotel.addRoom(random.nextInt(100, 300));
        hotel.addRoom(random.nextInt(100, 300));

        hotel.setRoomOccupied(2);
        hotel.setRoomRepair(3);
        hotel.setRoomService(4);

        hotel.moveOutRoom(1);

        System.out.println();

        hotel.addService("еда в номер",10);
        hotel.addService("срочная уборка",20);
        hotel.addService("такси",30);

        System.out.println(hotel.getRooms());
        System.out.println(hotel.getServices());

        hotel.setCost("1", 10);
        hotel.setCost("еда в номер", 9);
        hotel.setCost("20", 10);
        hotel.setCost("купить банан", 10);
        hotel.setCost(20, 10);

        System.out.println();

        System.out.println(hotel.getRooms());
        System.out.println(hotel.getServices());
    }
}
