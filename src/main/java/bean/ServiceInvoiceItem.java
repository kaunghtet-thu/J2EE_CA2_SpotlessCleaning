package bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceInvoiceItem extends InvoiceItem {
	

	private String address;
    private LocalDate bookingDate;
    private LocalTime bookingTime;

	
	public ServiceInvoiceItem(String serviceName, String address, LocalDate bookingDate, LocalTime bookingTime, double price) {
		super (serviceName, price);
		this.address = address;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		
	}
	
	public String getAddress() {
		return address;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public LocalTime getBookingTime() {
		return bookingTime;
	}
	@Override
	public double getPrice() {
		return price;
	}
	
	
}
