package task.DAO;

import task.Hotel.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ServiceDAO extends DAO<Service>{

    @Override
    protected boolean save(Service service) {
        String sql = """
            INSERT INTO services (name, cost)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, service.getName());
            stmt.setInt(2, service.getCost());


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return false;
        }
    }

}
