package DAO;

import bean.*;
import DB.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO {

    // Create a new discount

    public boolean createDiscount(int serviceId, String discountName, double discountPercentage) {
        String sql = "INSERT INTO discount (service_id, discount_name, discount_percentage) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.setString(2, discountName);
            stmt.setDouble(3, discountPercentage);
           

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurred
        }
    }

    // Get all discounts (only if isAdmin is true)
    public List<Discount> getAllDiscounts(boolean isAdmin) {
        List<Discount> discountList = new ArrayList<>();
        if (!isAdmin) return discountList; 

        String sql = "SELECT * FROM discount WHERE isActive = TRUE";

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
    public void updateDiscountStatus(int serviceId, boolean isAdmin) {
        if (!isAdmin) return; // Restrict access if not admin

        String sql = "UPDATE discount SET is_active = False where service_id = ? and is_active = True";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean getDiscountStatusByServiceId(int discountId) {

    	 String sql = "SELECT d.is_active " +
                 "FROM service s " +
                 "LEFT JOIN discount d ON d.service_id = s.id AND d.is_active = true " +
                 "WHERE s.id = ?";

    	 
    	// SELECT * 
    	 //FROM "service" s
    	 //LEFT JOIN "discount" d 
    	   //ON d.service_id = s.id AND d.is_true = true;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, discountId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_active");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
