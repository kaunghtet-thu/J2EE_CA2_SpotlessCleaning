package bean;

public class RetailerSales {
	private int retailerId;
	private int productId;
	private double accountReceivable;
	private int paymentReferencingId;
	private int qty;
	
	public RetailerSales(int retailerId, int productId, double accountReceivable, int paymentReferencingId, int qty) {

		this.retailerId = retailerId;
		this.productId = productId;
		this.accountReceivable = accountReceivable;
		this.paymentReferencingId = paymentReferencingId;
		this.qty = qty;
	}
	
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getRetailerId() {
		return retailerId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public double getAccountReceivable() {
		return accountReceivable;
	}
	
	public int getPaymentReferencingId() {
		return paymentReferencingId;
	}
	public void setPaymentReferencingId(int id) {
		this.paymentReferencingId = id;
	}
}
