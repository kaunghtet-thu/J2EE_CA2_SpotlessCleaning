package bean;

public abstract class InvoiceItem {
	String name;
	double price;
	
	public InvoiceItem(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	public abstract double getPrice();
	
}
