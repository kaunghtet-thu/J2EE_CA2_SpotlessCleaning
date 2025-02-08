package bean;

public class ProductSales extends Product {
	
	
	private double accountPayable;
	private boolean transferred;
	private boolean received;
	public ProductSales(int id, String name, String description, int stock, double price, double commission, double accountPayable, boolean transferred, boolean received) {
		super(id, name, description, stock, price, commission);
//		this.accountPayable= this.getPrice()-(this.getPrice()*this.getCommission());
		this.accountPayable = accountPayable;
		this.transferred = transferred;
		this.received = received;
	}
	public ProductSales(int id, String name, int stock, double price, double accountPayable, boolean transferred, boolean received) {
		super(id, name, stock, price);
//		this.accountPayable= this.getPrice()-(this.getPrice()*this.getCommission());
		this.accountPayable = accountPayable;
		this.transferred = transferred;
		this.received = received;
	}
	public double getAccountPayable() {
		return accountPayable;
	}
	public boolean isTransferred() {
		return transferred;
	}
	public boolean isReceived() {
		return received;
	}
	
	
}
