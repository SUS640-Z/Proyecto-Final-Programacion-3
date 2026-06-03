package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import models.OrderDetails;

public class OrderDetailsRepository {
	
	public void save(OrderDetails orderDetails) throws SQLException {
		String sql = "INSERT INTO Order_details (quantity, order_id, product_id) VALUES (?, ?, ?)";
		
		int productId = obtenerIdPorNombreProducto(orderDetails.getProduct_name());
	    
	    try(Connection connection = DataBaseConnection.getConnection();
	        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	    	pst.setInt(1, orderDetails.getQuantity());         
	        pst.setInt(2, orderDetails.getOrder_id());       
	        pst.setInt(3, productId);
	        
	        pst.executeUpdate();

	        try (ResultSet rs = pst.getGeneratedKeys()) { 
	            if(rs.next()) {
	                orderDetails.setId(rs.getInt(1)); 
	            }
	        }
	    }
	}

	public List<OrderDetails> getOrdersDetails() throws SQLException {List<OrderDetails> list = new ArrayList<>();
		String sql = "SELECT od.order_details_id, od.order_id, od.quantity, p.product_name,p.price, " +
		             "CONCAT(u.user_name, ' ', u.last_name) AS client_name " +
		             "FROM Order_details od " +
		             "INNER JOIN Product p ON od.product_id = p.product_id " +
		             "INNER JOIN Orders o ON od.order_id = o.order_id " +
		             "INNER JOIN Users u ON o.user_id = u.user_id";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				OrderDetails o = new OrderDetails();
				o.setId(rs.getInt("order_details_id"));
				o.setOrder_id(rs.getInt("order_id"));
				int cantidad = rs.getInt("quantity");
	            o.setQuantity(cantidad);
	            
				o.setProduct_name(rs.getString("product_name"));
				o.setClient_name(rs.getString("client_name")); 
				double precio = rs.getDouble("price");
				
				double total = cantidad * precio;
	            o.setPrice(total);
				
				list.add(o);
			}
		}
		return list;		
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM Order_details WHERE order_details_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean update(int id, OrderDetails updatedOrderDetails) {
	    String sql = "UPDATE Order_details SET quantity = ?, order_id = ?, product_id = ? WHERE order_details_id = ?";
	    

	    int productId = obtenerIdPorNombreProducto(updatedOrderDetails.getProduct_name());
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	        pst.setInt(1, updatedOrderDetails.getQuantity());
	        pst.setInt(2, updatedOrderDetails.getOrder_id());
	        pst.setInt(3, productId); 
	        pst.setInt(4, id);                              
	        
	        return pst.executeUpdate() > 0;
	    } catch(SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	
	public boolean updateOrder(int orderId) throws SQLException {
	    String sql = "UPDATE Orders o " +
	                 "SET " +
	                 "    o.total_amount = COALESCE((" +
	                 "        SELECT SUM(od.quantity * p.price) " +
	                 "        FROM Order_details od " +
	                 "        INNER JOIN Product p ON od.product_id = p.product_id " +
	                 "        WHERE od.order_id = o.order_id" +
	                 "    ), 0.00) " +
	                 "WHERE o.order_id = ?"; 

	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        
	        ps.setInt(1, orderId);
	      
	        int rowsAffected = ps.executeUpdate();
	        
	        return rowsAffected > 0;
	    }
	}
	
	public List<String> obtenerNombresUsuarios() throws SQLException {
	    List<String> usuarios = new ArrayList<>();
	    String sql = "SELECT CONCAT(user_name, ' ',last_name) AS client_name FROM Users"; 
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         Statement st = connection.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            usuarios.add(rs.getString("client_name"));
	        }
	    }
	    return usuarios;
	}
	
	public List<String> obtenerOrdenesPorUsuario(String nombreUsuario) throws SQLException {
	    List<String> ordenes = new ArrayList<>();
	    String sql = "SELECT o.order_id, o.order_date " +
	                 "FROM Orders o " +
	                 "INNER JOIN Users u ON o.user_id = u.user_id " +
	                 "WHERE CONCAT(u.user_name, ' ', u.last_name) = ?";

	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        
	        ps.setString(1, nombreUsuario);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                int idOrden = rs.getInt("order_id");
	                String fecha = rs.getString("order_date");
	                ordenes.add("Orden N. " + idOrden + " - Fecha: " + fecha);
	            }
	        }
	    }
	    return ordenes;
	}

	public List<String> obtenerNombresProductos() throws SQLException {
		List<String> productos = new ArrayList<>();
		String sql = "SELECT product_name FROM Product"; 
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				productos.add(rs.getString("product_name"));
			}
		}
		return productos;
	}
	

	private int obtenerIdPorNombreProducto(String nombreProducto) {
	    if (nombreProducto == null || nombreProducto.equals("Seleccionar")) return -1;
	    String sql = "SELECT product_id FROM Product WHERE product_name = ?";
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	        pst.setString(1, nombreProducto);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("product_id"); 
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return -1;
	}
}
