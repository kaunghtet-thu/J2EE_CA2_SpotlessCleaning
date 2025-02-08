package bean;

public class RetailerSales {
	private int retailerId;
	private int productId;
	private double accountReceivable;
	private int paymentReferencingId;
	public RetailerSales(int retailerId, int productId, double accountReceivable, int paymentReferencingId) {

		this.retailerId = retailerId;
		this.productId = productId;
		this.accountReceivable = accountReceivable;
		this.paymentReferencingId = paymentReferencingId;
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
	
	
	
}
