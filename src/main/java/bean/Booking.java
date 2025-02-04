package bean;

import java.util.ArrayList;

public class Booking {
	private int id;
	private int memberId;
	private ArrayList<BookingItems> bookingItems;
	
	public Booking(int id, int memberId, ArrayList<BookingItems> bookingItems) {
		this.id = id;
		this.memberId = memberId;
		this.bookingItems = bookingItems;
	}
	public int getId() {
		return id;
	}
	public int getMemberId() {
		return memberId;
	}
	public ArrayList<BookingItems> getBookingItems() {
		return bookingItems;
	}
	
	
}
