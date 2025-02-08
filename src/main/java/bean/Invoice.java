package bean;

import java.time.LocalDateTime;
import java.util.List;


public class Invoice {
	private int bookingId;
	private String customerName;
	private LocalDateTime bookedAt;
	private List<InvoiceItem> invoiceItem;
	private double discount = 1;

	public Invoice(int bookingid, String customerName, LocalDateTime bookedAt, List<InvoiceItem> invoiceItem) {
		this.bookingId = bookingid;
		this.customerName = customerName;
		this.bookedAt = bookedAt;
		this.invoiceItem = invoiceItem;
	}

	public Invoice(int bookingid, String customerName, LocalDateTime bookedAt, List<InvoiceItem> invoiceItem, double discount) {
		this.bookingId = bookingid;
		this.customerName = customerName;
		this.bookedAt = bookedAt;
		this.invoiceItem = invoiceItem;
		this.discount = discount;
	}
	
	public Invoice(String customerName, LocalDateTime now, List<InvoiceItem> invoiceItems) {
		this.customerName = customerName;
		this.bookedAt = now;
		this.invoiceItem = invoiceItems;
	}

	public int getBookingid() {
		return bookingId;
	}

	public void setInvoiceItem(List<InvoiceItem> invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	public String getCustomerName() {
		return customerName;
	}
	public LocalDateTime getBookedAt() {
		return bookedAt;
	}
	public List<InvoiceItem> getInvoiceItem() {
		return invoiceItem;
	} 
	public double getDiscount () {
		return this.discount;
	}
	public String getDiscountString () {
		return (int)(this.discount*100) +"% Off";
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;		
	}
}
