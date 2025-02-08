package DAO;
import DB.DatabaseUtil;

import bean.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MerchandizeSalesDAO {
	public List<ProductSales> getMerchandizeSales() {
	    String sql = "SELECT * FROM merchandizeSales";
	    List<ProductSales> sales = new ArrayList<>();

	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            ProductSales product = new ProductSales(
	                rs.getInt("id"),
	                rs.getString("productname"),
	                rs.getInt("quantity"), 
	                rs.getDouble("price"),
	                
	                rs.getDouble("accountPayable"), 
	                rs.getBoolean("transferred"),
	                rs.getBoolean("received")
	            );
	            sales.add(product);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error while fetching merchandise sales: ");
	        e.printStackTrace();
	    }

	    return sales; // Ensure a return statement exists
	}
	
	public boolean setTransferredStatusTrue(int id) {
	    String sql = "UPDATE merchandizesales SET transferred = true WHERE id = ?";
	    int rowsAffected = 0;

	    try (Connection connection = DatabaseUtil.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {

	        stmt.setInt(1, id);  

	        rowsAffected = stmt.executeUpdate(); 

	    } catch (SQLException e) {
	        System.err.println("Error updating transferred status: ");
	        e.printStackTrace();
	    }

	    return rowsAffected > 0; 
	}


}
