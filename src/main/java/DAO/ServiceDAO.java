package DAO;

import DB.DatabaseUtil;
import bean.Service;
import bean.DashboardService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    // Create: Insert a new service
    public boolean addService(Service service) {
        String sql = "INSERT INTO service (name, description, category_id, price, image) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setInt(3, service.getCategoryId());
            stmt.setDouble(4, service.getPrice());
            stmt.setString(5, "cleaning.png");
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read: Get a service by ID
    public Service getServiceById(int id) {
        String sql = "SELECT * FROM service WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Service(
                		rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
 // Read: Get a List of services by category ID
    public List<Service> getServicesByCategory(int id) {
        List<Service> result = new ArrayList<>();  // Initialize the list to store services
        String sql = "SELECT * FROM service WHERE category_id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);  // Set the category_id parameter in the query
            ResultSet rs = stmt.executeQuery();
            
            // Iterate over the ResultSet and add each service to the list
            while (rs.next()) {
                Service service = new Service(
                	rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("category_id"),
                    rs.getDouble("price"),
                    rs.getString("image")
                );
                result.add(service);  // Add the service to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;  // Return the list of services
    }

    // Read: Get all services
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service";
        try (Connection connection = DatabaseUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                services.add(new Service(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    // Update: Update an existing service
    public boolean updateService(int serviceId, String name, String description, double price) {
        String sql = "UPDATE service SET name = ?, description = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, serviceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public boolean updateService(Service service, int id) {
//        String sql = "UPDATE service SET name = ?, description = ?, category_id = ?, price = ?, image = ? WHERE id = ?";
//
//        try (Connection connection = DatabaseUtil.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            
//            stmt.setString(1, service.getName());
//            stmt.setString(2, service.getDescription());
//            stmt.setInt(3, service.getCategoryId());
//            stmt.setDouble(4, service.getPrice());
//            stmt.setString(5, service.getImage());
//            stmt.setInt(6, id);
//            int rowsUpdated = stmt.executeUpdate();
//            return rowsUpdated > 0;
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    // Delete: Delete a service by ID
    public boolean deleteService(int id) {
        String sql = "DELETE FROM service WHERE id = ?";

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

    public ArrayList<DashboardService> getAllServicesForDashboard(String filter) {
        // Correct SQL query with a placeholder for the filter
        String sql = "SELECT * FROM get_service_summary(?);";
        ArrayList<DashboardService> services = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, filter);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    services.add(new DashboardService(
                            rs.getInt("service_id"),        
                            rs.getString("service_name"),  
                            rs.getLong("booking_count"),    
                            rs.getDouble("average_rating")   
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of services
        return services;
    }
    
    public ArrayList<DashboardService> getServicesForDashboardByCategory (String filter, Integer categoryId) {
        // Correct SQL query with a placeholder for the filter
        String sql = "SELECT * FROM get_service_summary(?,?);";
        ArrayList<DashboardService> services = new ArrayList<>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, filter);
            pstmt.setInt(2, categoryId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    services.add(new DashboardService(
                            rs.getInt("service_id"),        
                            rs.getString("service_name"),  
                            rs.getLong("booking_count"),    
                            rs.getDouble("average_rating")   
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
}
}
