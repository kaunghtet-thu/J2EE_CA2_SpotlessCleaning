package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.DatabaseUtil;


public class IndexDAO {
	private static final String TABLENAME = "index";
	
	
	
	public ArrayList<String> getContent (){
		String sql = String.format("Select * from %s ORDER BY id ASC;", TABLENAME);
		
		 try (Connection connection = DatabaseUtil.getConnection();
	            PreparedStatement stmt = connection.prepareStatement(sql)) {
			 
	            ResultSet rs = stmt.executeQuery();
	           ArrayList<String> contents = new ArrayList<String>();
	            while (rs.next()) {
	            	String content = rs.getString("content");
	            	contents.add(content);
	            	
	            }
	            return contents;   
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 return null;
	}
	
	public static boolean editContentById (int id, String content, boolean isAdmin) {
		String sql = String.format("UPDATE %s set content = ? WHERE id = ?", TABLENAME);
		if (isAdmin) {
			 try (Connection connection = DatabaseUtil.getConnection();
			            PreparedStatement stmt = connection.prepareStatement(sql)) {
					 	stmt.setString(1, content);
					   	stmt.setInt(2, id);
			            int rows = stmt.executeUpdate();
			            
			            if (rows>0) {
			                return true;
			            }
			            
			        } catch (SQLException e) {
			            e.printStackTrace();
			            return false;
			        }
		}
		 return false;
	}
}
