package bean;

public class MerchandizeInvoiceItem extends InvoiceItem {
	private int quantity;

	public MerchandizeInvoiceItem(String name, double price, int quantity) {
		super(name, price);
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public double getUnitPrice() {
		return this.price;
	}

	@Override
	public double getPrice() {
		return this.price*this.quantity;
	}
	
}
