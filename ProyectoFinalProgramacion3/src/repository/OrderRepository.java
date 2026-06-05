package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import config.DataBaseConnection;
import models.Order;

public class OrderRepository {

	public List<Order> getOrders() throws SQLException {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT o.order_id, o.user_id, o.order_date, o.total_amount, o.status_order, u.user_name, u.last_name " +
		             "FROM orders o INNER JOIN users u ON o.user_id = u.user_id";

		try (Connection connection = DataBaseConnection.getConnection();
			 Statement st = connection.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				Order order = new Order(
					rs.getInt("order_id"), rs.getInt("user_id"),
					rs.getString("user_name") + " " + rs.getString("last_name"), 
					rs.getString("order_date"), rs.getDouble("total_amount"), rs.getString("status_order")
				);
				orders.add(order);
			}
		}
		return orders;
	}

	public void save(Order order) throws SQLException {
		int addressId = -1;

		String sqlAddress = "SELECT client_address_id FROM client_address WHERE user_id = ? LIMIT 1";
		try (Connection conn = DataBaseConnection.getConnection();
			 PreparedStatement psAddr = conn.prepareStatement(sqlAddress)) {
			
			psAddr.setInt(1, order.getUserId());
			try (ResultSet rs = psAddr.executeQuery()) {
				if (rs.next()) {
					addressId = rs.getInt("client_address_id");
				} else {

					throw new SQLException("Este cliente no tiene una dirección registrada. Agrega una dirección primero en el módulo correspondiente.");
				}
			}
		}

		String sql = "INSERT INTO orders (user_id, order_date, total_amount, status_order, client_address_id) VALUES (?, NOW(), ?, ?, ?)";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			
			pst.setInt(1, order.getUserId());         
			pst.setDouble(2, order.getTotal());       
			pst.setString(3, order.getStatus());
			pst.setInt(4, addressId);          
			pst.executeUpdate();

			try (ResultSet rs = pst.getGeneratedKeys()) { 
				if(rs.next()) { order.setId(rs.getInt(1)); }
			}
		}
	}
	public boolean update(int id, Order order) {
		String sql = "UPDATE orders SET user_id = ?, total_amount = ?, status_order = ? WHERE order_id = ?";
		try (Connection connection = DataBaseConnection.getConnection();
			 PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setInt(1, order.getUserId());
			pst.setDouble(2, order.getTotal());
			pst.setString(3, order.getStatus());
			pst.setInt(4, id);                              
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) { ex.printStackTrace(); }
		return false;
	}

	public boolean delete(int id) {
		String sqlDetalles = "DELETE FROM order_details WHERE order_id = ?";
		String sqlOrden = "DELETE FROM orders WHERE order_id = ?";
		
		try(Connection connection = DataBaseConnection.getConnection()) {
			try(PreparedStatement pst1 = connection.prepareStatement(sqlDetalles)) {
				pst1.setInt(1, id); pst1.executeUpdate();
			}
			try(PreparedStatement pst2 = connection.prepareStatement(sqlOrden)) {
				pst2.setInt(1, id); return pst2.executeUpdate() > 0;
			}
		} catch(SQLException ex) { ex.printStackTrace(); }
		return false;
	}
	public List<Order> getOrdersByUserId(int userId) throws SQLException {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT o.order_id, o.user_id, o.order_date, o.total_amount, o.status_order, u.user_name, u.last_name " +
		             "FROM orders o INNER JOIN users u ON o.user_id = u.user_id WHERE o.user_id = ?";

		try (Connection connection = DataBaseConnection.getConnection();
			 PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setInt(1, userId);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Order order = new Order(
						rs.getInt("order_id"), rs.getInt("user_id"),
						rs.getString("user_name") + " " + rs.getString("last_name"), 
						rs.getString("order_date"), rs.getDouble("total_amount"), rs.getString("status_order")
					);
					orders.add(order);
				}
			}
		}
		return orders;
	}
}