package DAO;

import DB.DatabaseUtil;
import bean.BookedService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookedServiceDAO {
	
	public static List<Map<String, Object>> getBookingsWithServicesByMember(int memberId) {
        List<Map<String, Object>> bookings = new ArrayList<>();
        String query = "SELECT * FROM get_bookings_with_services_by_member(?)";

        try (Connection connection = DatabaseUtil.getConnection();     
        	PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> booking = new HashMap<>();
                    booking.put("booking_id", rs.getInt("booking_id"));
                    booking.put("booked_at", rs.getTimestamp("booked_at"));
                    booking.put("service_id", rs.getInt("service_id"));
                    booking.put("service_name", rs.getString("service_name"));
                    booking.put("booking_date", rs.getDate("booking_date"));
                    booking.put("booking_time", rs.getTime("booking_time"));
                    booking.put("staff_id", rs.getInt("staff_id"));
                    booking.put("address_id", rs.getInt("address_id"));
                    booking.put("status_id", rs.getInt("status_id"));                   
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
	
	// Create: Add new booking services
	public boolean createBookingServices(int bookingId, List<BookedService> services) {
	    String sql = "INSERT INTO booking_service (booking_id, service_id, booking_date, booking_time, staff_id, address_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {

	        connection.setAutoCommit(false);

	        for (BookedService service : services) {
	            stmt.setInt(1, bookingId);
	            stmt.setInt(2, service.getServiceId());
	            stmt.setDate(3, Date.valueOf(service.getBookingDate()));
	            stmt.setTime(4, Time.valueOf(service.getBookingTime()));
	            stmt.setObject(5, service.getStaffId(), Types.INTEGER); // Handle optional field
	            stmt.setInt(6, service.getAddressId());
	            stmt.addBatch();
	        }

	        int[] results = stmt.executeBatch();
	        connection.commit();

	        for (int result : results) {
	            if (result == 0) {
	                connection.rollback();
	                return false;
	            }
	        }

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    // Read: Get all booking services for a booking
    public List<BookedService> getBookingServicesByBookingId(int bookingId) {
        List<BookedService> bookedServices = new ArrayList<>();
        String sql = "SELECT * FROM booking_service WHERE booking_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookedService service = new BookedService(
                            rs.getInt("id"),
                            rs.getInt("booking_id"),
                            rs.getInt("service_id"),
                            rs.getDate("booking_date").toLocalDate(),
                            rs.getTime("booking_time").toLocalTime(),
                            rs.getObject("staff_id", Integer.class), // Handle optional field
                            rs.getInt("address_id")
                    );
                    bookedServices.add(service);
                }
            }
        } catch (SQLException e) {
            // Log the exception (Optional)
            System.err.println("Error while fetching booking services for booking ID: " + bookingId);
            e.printStackTrace();
        }
        return bookedServices;
    }

    // Update: Update booking service status
    public boolean updateBookingServiceStatus(int id, int statusId) {
        // This method is not needed as the booking table does not have a status column
        // You may need to update the status in the booking_service table instead
        throw new UnsupportedOperationException("Updating booking service status is not supported in the current database schema.");
    }
    public String getStatus(int id) {
    	String sql = "SELECT * FROM status WHERE id = ?";
    	try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

               stmt.setInt(1, id);

               try (ResultSet rs = stmt.executeQuery()) {
                   if (rs.next()) {
                      String status = rs.getString("name");
                       return status;
                   }
               }
           } catch (SQLException e) {
               // Log the exception (Optional)
               System.err.println("Error while fetching status name " + id);
               e.printStackTrace();
           }
           return "N/A";
    }
}