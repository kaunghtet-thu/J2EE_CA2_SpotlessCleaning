package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import DB.DatabaseUtil;

public class BookedService {
    private int id;
    private int bookingId;
    private int serviceId;
    private int addressId;
    private int statusId;
    private int staffId;
    private int code;
    private String preferredGender;
    private String serviceName;
    private LocalDate bookingDate;
    private LocalTime bookingTime;

	public BookedService(int id, int bookingId, int serviceId, LocalDate bookingDate, LocalTime bookingTime, int staffId, int addressId) {
        this.id = id;
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.staffId = staffId;
        this.addressId = addressId;
    }


	public BookedService(int serviceId2, Integer addressId2, LocalDate serviceDate, LocalTime serviceTime, int code, String gender) {
		this.serviceId = serviceId2;
		this.addressId = addressId2;
		this.bookingDate = serviceDate;
		this.bookingTime = serviceTime;
		this.code = code;
		this.preferredGender = gender;
	}

	public BookedService(int id, int bookingId, int serviceId, LocalDate date, LocalTime time, int staffId, int addressId, int statusId) {
		this.id = id;
		this.bookingId = bookingId;
		this.serviceId = serviceId;
		this.bookingDate = date;
		this.bookingTime = time;
		this.staffId = staffId;
		this.addressId = addressId;
		this.statusId = statusId;
	}


	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }



    public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getPreferredGender() {
		return preferredGender;
	}

	public void setPreferredGender(String preferredGender) {
		this.preferredGender = preferredGender;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}
	public String getStatus() {
    	String sql = "SELECT * FROM status WHERE id = ?";
    	try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

               stmt.setInt(1, this.serviceId);

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