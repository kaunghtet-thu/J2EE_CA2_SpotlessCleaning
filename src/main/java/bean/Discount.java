package bean;

public class Discount {
	private int id;
	private int serviceId;
	private String discountName;
	private double discount;
	private boolean isAcitve;
	
	public Discount(int id, int serviceId, String discountName, double discount, boolean isAcitve) {
		this.id = id;
		this.serviceId = serviceId;
		this.discountName = discountName;
		this.discount = discount;
		this.isAcitve = isAcitve;
	}
	
	public String getDiscountName() {
		return discountName;
	}
	public int getId() {
		return id;
	}
	public int getServiceId() {
		return serviceId;
	}
	public double getDiscount() {
		return discount;
	}
	public boolean isAcitve() {
		return isAcitve;
	}
	
	
}
