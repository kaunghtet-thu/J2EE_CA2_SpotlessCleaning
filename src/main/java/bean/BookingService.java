package bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingService {
    private int id;
    private int bookingId;
    private int serviceId;
    private int quantity;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private int staffId; // Nullable field
    private int addressId;

    public BookingService() {
      
    }

	public BookingService(int id, int bookingId, int serviceId, int quantity, LocalDate bookingDate, LocalTime bookingTime, int staffId, int addressId) {
        this.id = id;
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.staffId = staffId;
        this.addressId = addressId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}