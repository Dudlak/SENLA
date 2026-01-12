package task;

import task.DAO.GuestDAO;
import task.DAO.RoomDAO;
import task.DAO.ServiceDAO;
import task.Hotel.Guest;
import task.Hotel.Room;
import task.Hotel.Service;

import java.sql.SQLException;

import static task.Main.hotel;

public class testMain {
    public static GuestDAO dao = new GuestDAO();

    public static void main(String[] args) throws SQLException {
        System.out.println("===TEST SAVING===");

        hotel.addRoom(100);
        hotel.moveIntoRoom(1, 3);
        Guest guest = hotel.getGuest(1);

        dao.saveTransaction(guest);

    }
}
