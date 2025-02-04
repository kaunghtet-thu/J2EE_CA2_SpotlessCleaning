package DAO;

import DB.DatabaseUtil;
import bean.BookingService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceDAO {

	// Create: Add new booking services
	public boolean createBookingServices(int bookingId, List<BookingService> services) {
	    String sql = "INSERT INTO booking_service (booking_id, service_id, quantity, booking_date, booking_time, staff_id, address_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {

	        connection.setAutoCommit(false);

	        for (BookingService service : services) {
	            stmt.setInt(1, bookingId);
	            stmt.setInt(2, service.getServiceId());
	            stmt.setInt(3, service.getQuantity());
	            stmt.setDate(4, Date.valueOf(service.getBookingDate()));
	            stmt.setTime(5, Time.valueOf(service.getBookingTime()));
	            stmt.setObject(6, service.getStaffId(), Types.INTEGER); // Handle optional field
	            stmt.setInt(7, service.getAddressId());
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
    public List<BookingService> getBookingServicesByBookingId(int bookingId) {
        List<BookingService> bookingServices = new ArrayList<>();
        String sql = "SELECT * FROM booking_service WHERE booking_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookingService service = new BookingService(
                            rs.getInt("id"),
                            rs.getInt("booking_id"),
                            rs.getInt("service_id"),
                            rs.getInt("quantity"),
                            rs.getDate("booking_date").toLocalDate(),
                            rs.getTime("booking_time").toLocalTime(),
                            rs.getObject("staff_id", Integer.class), // Handle optional field
                            rs.getInt("address_id")
                    );
                    bookingServices.add(service);
                }
            }
        } catch (SQLException e) {
            // Log the exception (Optional)
            System.err.println("Error while fetching booking services for booking ID: " + bookingId);
            e.printStackTrace();
        }
        return bookingServices;
    }

    // Update: Update booking service status
    public boolean updateBookingServiceStatus(int id, int statusId) {
        // This method is not needed as the booking table does not have a status column
        // You may need to update the status in the booking_service table instead
        throw new UnsupportedOperationException("Updating booking service status is not supported in the current database schema.");
    }
}