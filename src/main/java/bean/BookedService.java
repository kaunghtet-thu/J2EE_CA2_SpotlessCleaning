package bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookedService {
    private int id;
    private int bookingId;
    private int serviceId;
    private int addressId;
    private int statusId;
    private int staffId;
    private int code;
    private char preferredGender;
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


	public BookedService(int serviceId2, Integer addressId2, LocalDate serviceDate, LocalTime serviceTime, int code) {
		this.serviceId = serviceId2;
		this.addressId = addressId2;
		this.bookingDate = serviceDate;
		this.bookingTime = serviceTime;
		this.code = code;
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

	public char getPreferredGender() {
		return preferredGender;
	}

	public void setPreferredGender(char preferredGender) {
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
}