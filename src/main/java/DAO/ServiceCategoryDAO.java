package DAO;

import DB.DatabaseUtil;
import bean.ServiceCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryDAO {

    // Create: Insert a new service category
    public boolean addServiceCategory(ServiceCategory serviceCategory) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, serviceCategory.getName());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read: Get a service category by ID
    public ServiceCategory getServiceCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                String image = rs.getString("image");
                return new ServiceCategory(id, name, image);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read: Get all service categories
    public List<ServiceCategory> getAllServiceCategories() {
        List<ServiceCategory> serviceCategories = new ArrayList<>();
        String sql = "SELECT * FROM category";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                serviceCategories.add(new ServiceCategory(id, name, image));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceCategories;
    }

    // Update: Update a service category's name by ID
    public boolean updateServiceCategory(int id, String newName) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete: Delete a service category by ID
    public boolean deleteServiceCategory(int id) {
        String sql = "DELETE FROM category WHERE id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
