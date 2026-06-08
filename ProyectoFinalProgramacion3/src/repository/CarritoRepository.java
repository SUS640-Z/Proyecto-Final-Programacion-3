package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import models.Address;
import models.OrderDetails;
import models.ProductMandado;
import models.User;
import utils.Mandado;

public class CarritoRepository {
	
	public void saveOrder(double total, String status, int userId, int idAddress) throws SQLException {
		LocalDateTime fechaEquipo = LocalDateTime.now();
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fechaFormateada = fechaEquipo.format(formateador);

		String sqlOrden = "INSERT INTO Orders (order_date, total_amount, status_order, user_id, client_address_id) VALUES (?, ?, ?, ?, ?)";
		String sqlDetalle = "INSERT INTO Order_details (quantity, order_id, product_id) VALUES (?, ?, ?)";
		
		Connection connection = null;
		PreparedStatement psOrden = null;
		PreparedStatement psDetalle = null;
		ResultSet rsKeys = null;

		try {
			connection = DataBaseConnection.getConnection();
			connection.setAutoCommit(false);


			psOrden = connection.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS);
			psOrden.setString(1, fechaFormateada);
			psOrden.setDouble(2, total);
			psOrden.setString(3, status);
			psOrden.setInt(4, userId);
			psOrden.setInt(5, idAddress);
			psOrden.executeUpdate();

			
			int idOrdenGenerada = -1;
			rsKeys = psOrden.getGeneratedKeys();
			if (rsKeys.next()) {
				idOrdenGenerada = rsKeys.getInt(1);
			} else {
				throw new SQLException("Error: No se pudo recuperar el ID autoincrementable de la orden.");
			}

			
			psDetalle = connection.prepareStatement(sqlDetalle);
			List<ProductMandado> carritoItems = Mandado.getItems();

			for (ProductMandado item : carritoItems) {
				psDetalle.setInt(1, item.getCantidad());           
				psDetalle.setInt(2, idOrdenGenerada);              
				psDetalle.setInt(3, item.getProducto().getId());    
				
				psDetalle.addBatch(); 
			}

		
			psDetalle.executeBatch();

			
			connection.commit();

		} catch (SQLException ex) {
			
			if (connection != null) {
				try { connection.rollback(); } catch (SQLException e) { e.printStackTrace(); }
			}
			throw ex; 
		} finally {
			try { if (rsKeys != null) rsKeys.close(); } catch (Exception e) {}
			try { if (psOrden != null) psOrden.close(); } catch (Exception e) {}
			try { if (psDetalle != null) psDetalle.close(); } catch (Exception e) {}
			try { if (connection != null) connection.close(); } catch (Exception e) {}
		}
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
	                
	              
	                String direccionCompleta = barrio + ", " + calle + " (Ref: " + referencia + ")";
	                address.add(direccionCompleta);
	            }
	        }
	    } 
	    return address;
	}
	

	public int findAddressIdByDetails(int userId, String neighborhood, String street, String reference) throws SQLException {
	    String sql = "SELECT client_address_id FROM Client_address " +
	                 "WHERE user_id = ? AND neighborhood = ? AND street = ? AND reference_address = ?";
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        
	        ps.setInt(1, userId);
	        ps.setString(2, neighborhood);
	        ps.setString(3, street);
	        ps.setString(4, reference);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("client_address_id");
	            }
	        }
	    }
	    throw new SQLException("No se encontró la ID de la dirección especificada en la base de datos.");
	}
}


