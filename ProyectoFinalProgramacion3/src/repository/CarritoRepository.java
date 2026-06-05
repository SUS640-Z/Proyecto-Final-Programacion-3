package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import models.Address;
import models.OrderDetails;
import models.User;

public class CarritoRepository {
	
	public void saveOrder(String fecha,double total,String status,int user_id,int id_address) throws SQLException {
		
	}
	
	public List<String> getAdressForCombo(int userId) throws SQLException {
	    List<String> address = new ArrayList<>();
	    String sql = "SELECT neighborhood, street, reference_address FROM Client_address WHERE user_id = ?";
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        
	        ps.setInt(1, userId); 
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String barrio = rs.getString("neighborhood");
	                String calle = rs.getString("street");
	                String referencia = rs.getString("reference_address");
	                
	               
	                String direccionCompleta = barrio + " - " + calle + " (Ref: " + referencia + ")";
	                
	                
	                address.add(direccionCompleta);
	            }
	        }
	    } 
	    
	    return address;
	}

}
