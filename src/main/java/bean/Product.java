package bean;

public class Product {
	private int id;
	private String name;
	private String description;
	private int stock;
	private double price;
	private double commission;
	
	public Product(int id, String name, String description, int stock, double price, double commission) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.price = price;
		this.commission = commission;	
	}
	public Product(int id, String name, int stock, double price) {
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getStock() {
		return stock;
	}
	public double getPrice() {
		return price;
	}
	public double getCommission() {
		return commission;
	}
	
	
}
