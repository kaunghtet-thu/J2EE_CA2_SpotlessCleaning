package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DatabaseUtil;

public class StatusDAO {
	public String getStatusName(int id) {
        String sql = "SELECT * FROM status WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
