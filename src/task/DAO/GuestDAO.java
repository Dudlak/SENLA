package task.DAO;

import task.Hotel.Guest;
import task.Hotel.Hotel;

import java.sql.*;

import static task.Main.hotel;

public class GuestDAO extends DAO<Guest>{

    @Override
    protected boolean save(Guest guest) {
        String sql = """
            INSERT INTO guests (id, name, move_in_day, move_out_day, pay, room_pay)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, hotel.getId(guest));
            stmt.setString(2, guest.getName());
            stmt.setInt(3, guest.getMoveInDay());
            stmt.setInt(4, guest.getMoveOutDay());
            stmt.setInt(5, guest.getPay());
            stmt.setInt(6, guest.getRoomPay());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return false;
        }
    }

}
