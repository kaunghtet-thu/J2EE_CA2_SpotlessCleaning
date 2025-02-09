package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DatabaseUtil;

public class ProductSales extends Product {
	
	
	private double accountPayable;
	private boolean transferred;
	private boolean received;
	private int quantity;
	
	public ProductSales(int id, String name, String description, int stock, double price, double commission, double accountPayable, boolean transferred, boolean received) {
		super(id, name, description, stock, price, commission);
//		this.accountPayable= this.getPrice()-(this.getPrice()*this.getCommission());
		this.accountPayable = accountPayable;
		this.transferred = transferred;
		this.received = received;
		calculatePayable();

	}
	public ProductSales(int id, String name, int stock, double price, double accountPayable, boolean transferred, boolean received) {
		super(id, name, stock, price);
//		this.accountPayable= this.getPrice()-(this.getPrice()*this.getCommission());
		this.accountPayable = accountPayable;
		this.transferred = transferred;
		this.received = received;
		calculatePayable();

	}
	public ProductSales(int pid, String name, double price, double comm, int quantity2) {
		super(pid,name,price,comm);
		this.quantity = quantity2;
		calculatePayable();
		// TODO Auto-generated constructor stub
	}
//	public double getPayable() {
//		double lee = this.price*(1-this.commission);
//		return lee;
//	}
	private void calculatePayable() {
		this.accountPayable = this.getPrice()-(this.price*this.commission);
	}
	public int addToMerchandizeSales() {
	    String sql = "SELECT insert_merchandize_record(?,?,?,?,?)";

	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {
	        
	        stmt.setInt(1, this.id);
	        stmt.setDouble(2, this.price);
	        stmt.setDouble(3, this.accountPayable);
	        stmt.setString(4, this.name);
	        stmt.setInt(5, this.quantity);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1); // Return the newly inserted ID
	            }
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return -1; // Return -1 if insertion fails
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
