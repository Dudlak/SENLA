package task.DAO;

import task.Hotel.Guest;
import task.Hotel.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static task.Main.hotel;

public class RoomDAO extends DAO<Room>{

    @Override
    protected boolean save(Room room) {
        String sql = """
            INSERT INTO rooms (id, cost, number, capacity, stars, status, update_day)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, hotel.getId(room));
            stmt.setInt(2, room.getCost());
            stmt.setInt(3, room.getNumber());
            stmt.setInt(4, room.getCapacity());
            stmt.setInt(5, room.getStars());
            stmt.setString(6, room.getStatus());
            stmt.setInt(7, room.getUpdateDay());


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return false;
        }
    }

}
