package task_4;

import task_4.T1.Hotel;

public class test {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Hotel newHotel = new Hotel();

        newHotel.addRoom(10000);
        System.out.println(hotel.getRooms());
        System.out.println();
        System.out.println(newHotel.getRooms());
    }
}
