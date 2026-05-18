package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection; 
import models.User;

public class UserRepository {

	public void save(User user) throws SQLException {

		String sql = "INSERT INTO cliente (nombres, apellidos, correo, password, imagePath) VALUES (?, ?, ?, ?, ?)";
		
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pst.setString(1, user.getName());
			pst.setString(2, user.getLastName());
			pst.setString(3, user.getEmail());
			pst.setString(4, user.getPassword()); 
			pst.setString(5, user.getImagePath());
			
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			if(rs.next()) {
				user.setId(rs.getInt(1)); 
			}
		}
	}

	public List<User> getUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM cliente";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				User user = new User(
					rs.getInt("id_cliente"), 
					rs.getString("nombres"), 
					rs.getString("apellidos"),
					rs.getString("correo"),
					rs.getString("password"),
					rs.getString("imagePath") 
				);
				users.add(user);
			}
		}
		return users;		
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM cliente WHERE id_cliente = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setInt(1, id);
			int affectedRows = pst.executeUpdate();
			return affectedRows > 0;
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean update(int index, User updatedUser) {
		String sql = "UPDATE cliente SET nombres = ?, apellidos = ?, correo = ?, password = ?, imagePath = ? WHERE id_cliente = ?";
		
		try (Connection connection = DataBaseConnection.getConnection();
			 PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setString(1, updatedUser.getName());
			pst.setString(2, updatedUser.getLastName());
			pst.setString(3, updatedUser.getEmail());
			pst.setString(4, updatedUser.getPassword());
			pst.setString(5, updatedUser.getImagePath());
			pst.setInt(6, updatedUser.getId());
			
			int affectedRows = pst.executeUpdate();
			return affectedRows > 0;
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}