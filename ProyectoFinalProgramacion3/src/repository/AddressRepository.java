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
import models.User;

public class AddressRepository {

	public void save(Address address) throws SQLException {
		String sql = "INSERT INTO client_address (neighborhood, street, reference_address, delivery_instructions, user_id) VALUES (?, ?, ?, ?, ?)";
		
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pst.setString(1, address.getNeighborhood());
			pst.setString(2, address.getStreet());
			pst.setString(3, address.getReference());
			pst.setString(4, address.getInstructions());
			pst.setInt(5, address.getUserId());
			
			pst.executeUpdate();

			try(ResultSet rs = pst.getGeneratedKeys()) {
				if(rs.next()) {
					address.setId(rs.getInt(1));
				}
			}
		}
	}

	public List<Address> getAddresses() throws SQLException {
		List<Address> addresses = new ArrayList<>();
		String sql = "SELECT a.*, u.user_name, u.last_name FROM client_address a INNER JOIN users u ON a.user_id = u.user_id";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				String fullName = rs.getString("user_name") + " " + rs.getString("last_name");
				Address addr = new Address(
					rs.getInt("client_address_id"),
					rs.getString("neighborhood"),
					rs.getString("street"),
					rs.getString("reference_address"),
					rs.getString("delivery_instructions"),
					rs.getInt("user_id"),
					fullName
				);
				addresses.add(addr);
			}
		}
		return addresses;
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM client_address WHERE client_address_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean update(int id, Address address) {
		String sql = "UPDATE client_address SET neighborhood = ?, street = ?, reference_address = ?, delivery_instructions = ?, user_id = ? WHERE client_address_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setString(1, address.getNeighborhood());
			pst.setString(2, address.getStreet());
			pst.setString(3, address.getReference());
			pst.setString(4, address.getInstructions());
			pst.setInt(5, address.getUserId());
			pst.setInt(6, id);
			
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public List<User> getUsersForCombo() throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "SELECT user_id, user_name, last_name FROM users";
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			while(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("user_id"));
				u.setName(rs.getString("user_name"));
				u.setLastName(rs.getString("last_name"));
				users.add(u);
			}
		}
		return users;
	}
	public List<Address> getAddressesByUserId(int userId) throws SQLException {
		List<Address> addresses = new ArrayList<>();
		String sql = "SELECT a.*, u.user_name, u.last_name FROM client_address a INNER JOIN users u ON a.user_id = u.user_id WHERE a.user_id = ?";
		
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setInt(1, userId);
			try(ResultSet rs = pst.executeQuery()) {
				while(rs.next()) {
					String fullName = rs.getString("user_name") + " " + rs.getString("last_name");
					Address addr = new Address(
						rs.getInt("client_address_id"),
						rs.getString("neighborhood"),
						rs.getString("street"),
						rs.getString("reference_address"),
						rs.getString("delivery_instructions"),
						rs.getInt("user_id"),
						fullName
					);
					addresses.add(addr);
				}
			}
		}
		return addresses;
	}
}