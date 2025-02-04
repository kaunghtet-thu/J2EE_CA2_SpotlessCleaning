package bean;

public class CartItem {
    private int bookingId;
    private int memberId;
    private int serviceId;
    private String bookingDate;
    private String bookingTime;
    private int quantity;

    public CartItem(int bookingId, int memberId, int serviceId, String bookingDate, String bookingTime, int quantity) {
        this.bookingId = bookingId;
        this.memberId = memberId;
        this.serviceId = serviceId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Increment quantity
    public void incrementQuantity() {
        this.quantity++;
    }
}
