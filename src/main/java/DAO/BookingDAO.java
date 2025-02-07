package DAO;

import DB.DatabaseUtil;
import bean.BookingItems;
import bean.BookedService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDAO {

	// Create: Add a new booking
    public int createBooking(int memberId) {
        String sql = "INSERT INTO booking (member_id) VALUES (?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, memberId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }
            

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating booking failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Create: Add feedback to a booking

    public boolean addFeedback(int bookingId, int rating, String comments) {
        String sql = "SELECT add_feedback(?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        		) {

            stmt.setInt(1, bookingId);
            stmt.setInt(2, rating);
            stmt.setString(3, comments);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1); // Get the returned boolean value from the function
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteFeedback(int bookingId) {
        String sql = "DELETE FROM feedback WHERE booking_id = ?";
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            
            // Execute the delete and check if any rows were affected
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            // Log the error with a logging framework (e.g., log4j, SLF4J) instead of printing stack trace
            System.err.println("Error deleting feedback for booking ID " + bookingId);
            e.printStackTrace();
            return false;
        }
    }

    // Update: Update booking status
    public boolean updateBookingStatus(int id, int statusId) {
        String sql = "UPDATE booking SET status_id = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, statusId);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete: Remove a booking by ID
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM booking WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public boolean addBooking(BookingItems bookingItems) {
		// TODO Auto-generated method stub
		return false;
	}
}



// Read: Get all bookings
//public List<BookedService> getAllBookings() {
//    List<BookedService> bookings = new ArrayList<>();
//    String sql = "SELECT * FROM booking";
//
//    try (Connection connection = DatabaseUtil.getConnection();
//         PreparedStatement stmt = connection.prepareStatement(sql);
//         ResultSet rs = stmt.executeQuery()) {
//
//        while (rs.next()) {
//            BookedService booking = new BookedService(
//                    rs.getInt("id"),
//                    rs.getInt("member_id"),
//                    rs.getInt("service_id"),
//                    rs.getInt("status_id"),
//                    rs.getObject("staff_id", Integer.class),
//                    rs.getDate("booking_date").toLocalDate(),
//                    rs.getTime("booking_time").toLocalTime(),
//                    rs.getTimestamp("booked_at").toLocalDateTime()
//            );
//            bookings.add(booking);
//        }
//        return bookings;
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return null;
//}

//List<BookedService> getBookingsByMemberId(int memberId) {
//    List<BookedService> bookings = new ArrayList<>();
//    String sql = "SELECT * FROM booking WHERE member_id = ?";
//
//    try (Connection connection = DatabaseUtil.getConnection();
//         PreparedStatement stmt = connection.prepareStatement(sql)) {
//
//        // Set the parameter for member_id
//        stmt.setInt(1, memberId);
//
//        try (ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                bookings.add(new BookedService(
//                        rs.getInt("id"),
//                        rs.getInt("booking_id"),
//                        rs.getInt("service_id"),
//                        Integer.parseInt("1"),  // Handles null values
//                        rs.getDate("booking_date").toLocalDate(),
//                        rs.getTime("booking_time").toLocalTime(),
//                        rs.getTimestamp("booked_at").toLocalDateTime(),
//                        Integer.parseInt("1"),  // Handles null values
//                		rs.getInt("address_id") ))
//            }    public
//        }
//    } catch (SQLException e) {
//        // Log the exception (Optional)
//        System.err.println("Error while fetching bookings for member ID: " + memberId);
//        e.printStackTrace();
//    }
//    return bookings;
//}