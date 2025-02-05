package DAO;

import bean.*;
import DB.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO {

    // Create a new discount
    public void createDiscount(int serviceId, String discountName, double discountPercent) {
        String sql = "INSERT INTO discount (service_id, discount_name, discount, is_active) VALUES (?, ?, ?, TRUE)";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
             
            stmt.setInt(1, serviceId);
            stmt.setString(2, discountName);
            stmt.setDouble(3, discountPercent / 100); // Convert percent to decimal
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all discounts (only if isAdmin is true)
    public List<Discount> getAllDiscounts(boolean isAdmin) {
        List<Discount> discountList = new ArrayList<>();
        if (!isAdmin) return discountList; 

        String sql = "SELECT * FROM discount";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Discount discount = new Discount(
                    rs.getInt("id"),
                    rs.getInt("service_id"),
                    rs.getString("discount_name"),
                    rs.getDouble("discount"),
                    rs.getBoolean("is_active")
                );
                discountList.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discountList;
    }

    // Toggle discount status (only if isAdmin is true)
    public void updateDiscountStatus(int discountId, boolean isAdmin) {
        if (!isAdmin) return; // Restrict access if not admin

        String sql = "UPDATE discount SET is_active = NOT is_active WHERE id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, discountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
