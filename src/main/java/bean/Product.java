package bean;

import java.io.Serializable;

public class Product implements Serializable{
	
    private static final long serialVersionUID = 1L;


	private int id;
	private String name;
	private String description;
	private int stock;
	private double price;
	private double commission;
	
	// ✅ No-argument constructor (Important for JSON deserialization)
    public Product() {}

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
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}
	
		

}
