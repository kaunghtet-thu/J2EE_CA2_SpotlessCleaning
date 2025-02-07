package bean;
import java.util.List;

public class Booking {
	private int id;
	private int memberId;
	private List<Service> bookedServices;
	
	public Booking(int id, int memberId, List<Service> bookedServices) {
		this.id = id;
		this.memberId = memberId;
		this.bookedServices = bookedServices;
	}
	public int getId() {
		return id;
	}
	public int getMemberId() {
		return memberId;
	}
	public List<Service> getBookingItems() {
		return bookedServices;
	}	
}
