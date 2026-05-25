package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import config.DataBaseConnection;
import models.Rol;

public class RolRepository {

	public void save(Rol rol) throws SQLException {
		String sql = "INSERT INTO rol (rol_name, rol_description) VALUES (?, ?)";
		
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pst.setString(1, rol.getName());
			pst.setString(2, rol.getDescription());
			pst.executeUpdate();

			try(ResultSet rs = pst.getGeneratedKeys()) {
				if(rs.next()) {
					rol.setId(rs.getInt(1));
				}
			}
		}
	}

	public List<Rol> getRoles() throws SQLException {
		List<Rol> roles = new ArrayList<>();
		String sql = "SELECT * FROM rol";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				Rol rol = new Rol(
					rs.getInt("rol_id"),
					rs.getString("rol_name"),
					rs.getString("rol_description")
				);
				roles.add(rol);
			}
		}
		return roles;
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM rol WHERE rol_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean update(int id, Rol updatedRol) {
		String sql = "UPDATE rol SET rol_name = ?, rol_description = ? WHERE rol_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setString(1, updatedRol.getName());
			pst.setString(2, updatedRol.getDescription());
			pst.setInt(3, id);
			
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}