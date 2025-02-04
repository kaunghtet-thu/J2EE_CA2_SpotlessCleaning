package DAO;

import java.sql.*;
import java.util.*;

import DB.DatabaseUtil;
import bean.Feedback;

public class FeedbackDAO {
    // Get feedback by booking ID
    public Feedback getFeedbackByBookingId(int bookingId) {
        Feedback feedback = null;
        String sql = "SELECT * FROM feedback WHERE booking_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                feedback = new Feedback(
                    rs.getInt("id"),
                    rs.getInt("booking_id"),
                    rs.getInt("rating"),
                    rs.getString("comments"),
                    rs.getBoolean("display")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }
    
    public ArrayList<Feedback> getFeedBackByDisplayTrue () {
    	ArrayList<Feedback> feedbacks= new ArrayList<>();
          String sql = "SELECT * FROM feedback WHERE display = TRUE ORDER BY rating desc";
          try (Connection connection = DatabaseUtil.getConnection();
          		PreparedStatement stmt = connection.prepareStatement(sql)) {
              
              ResultSet rs = stmt.executeQuery();
              while (rs.next()) {
                  feedbacks.add( new Feedback(
                      rs.getInt("id"),
                      rs.getInt("booking_id"),
                      rs.getInt("rating"),
                      rs.getString("comments"),
                      rs.getBoolean("display")
                  ));
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return feedbacks;
    }
    
    public String[] getServiceNameOfaFeedback(int id) {
        String sql = "SELECT * FROM get_feedback_service_member_name(?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            String[] result = new String[2];
            if (rs.next()) {
                result[0] = rs.getString("member_name");
                result[1] = rs.getString("service_name"); 
                return result;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  
    }


    // creat new  feedback
    public boolean addFeedback(int bookingId, int rating, String comments) {
        String sql = "INSERT INTO feedback (booking_id, rating, comments) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.setInt(2, rating);
            stmt.setString(3, comments);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update existing feedback
    public boolean updateFeedback(int bookingId, int rating, String comments) {
        String sql = "UPDATE feedback SET rating = ?, comments = ? WHERE booking_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rating);
            stmt.setString(2, comments);
            stmt.setInt(3, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete feedback
    public boolean deleteFeedback(int booking_id) {
        String sql = "DELETE FROM feedback WHERE booking_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking_id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<Feedback> getAllFeedback(boolean isAdmin) {
        // Create an empty list to hold feedbacks
        System.out.print("At DAO, isAdmin: " + isAdmin);  // Useful for debugging
        
        ArrayList<Feedback> allFeedbacks = new ArrayList<>();

        // Check if user is an admin
        if (isAdmin) {
            String sql = "SELECT * FROM feedback";  // SQL query to get all feedback

            try (Connection connection = DatabaseUtil.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                
                ResultSet rs = stmt.executeQuery();
                // Process the result set and populate the feedback list
                while (rs.next()) {
                    allFeedbacks.add(new Feedback(
                            rs.getInt("id"),
                            rs.getInt("booking_id"),
                            rs.getInt("rating"),
                            rs.getString("comments"),
                            rs.getBoolean("display")
                    ));
                }
                return allFeedbacks;  // Return the list of feedbacks
            } catch (SQLException e) {
                // Log the exception and rethrow as a runtime exception or handle as needed
                e.printStackTrace();
                throw new RuntimeException("Error fetching feedbacks from the database.", e);
            }
        } else {
            // If not an admin, return an empty list (instead of null) to avoid NullPointerException
            return new ArrayList<>(); 
        }
    }


}
