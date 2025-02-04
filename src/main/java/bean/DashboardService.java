package bean;

public class DashboardService {
	private int id;
	private String name;
	private long noOfBooks;
	private double rating;
	
	public DashboardService(int id, String name,  long noOfBooks, double rating) {
		this.id = id;
		this.name = name;
		this.noOfBooks = noOfBooks;
		this.rating = rating;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public long getNoOfBooks() {
		return noOfBooks;
	}
	public double getRating() {
		return rating;
	}
	
	
}
