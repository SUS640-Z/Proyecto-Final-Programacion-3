package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import models.Product;



public class ProductRepository {
	
	public void save(Product product) throws SQLException {
		String sql = "INSERT INTO Product (product_name, price, season, is_active, product_type_id) VALUES (?, ?, ?, ?, ?)";
	    
	    int idTipo = obtenerIdPorNombreTipo(product.getProduct_type());
	    
	    if (idTipo == -1) {
	    	idTipo = 1; 
	    }
		
	    try(Connection connection = DataBaseConnection.getConnection();
	        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    	pst.setString(1, product.getName());         
	        pst.setDouble(2, product.getPrice()); 
	        pst.setString(3, normalizarTemporadaAIngles(product.getSeason()));
	        pst.setBoolean(4, product.isIs_active());
	        pst.setInt(5, idTipo); 
	        pst.executeUpdate();

	    }
	}

	public List<Product> getProducts() throws SQLException {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM Product INNER JOIN Product_type ON Product_type.product_type_id = Product.product_type_id";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("product_name"));
				product.setPrice(rs.getDouble("price"));
				String seasonBD = rs.getString("season");
				product.setSeason(traducirTemporadaAEspanol(seasonBD));
				product.setIs_active(rs.getBoolean("is_active"));
				product.setProduct_type(rs.getString("name_product_type"));
				
				products.add(product);

			}
		}
		return products;		
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM Product WHERE product_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	

	public boolean update(int id, Product updatedProduct) {
		String sql = "UPDATE Product SET product_name = ?, price = ?, season = ?, is_active = ?, product_type_id = ? WHERE product_id = ?";
	    
	    int idTipo = obtenerIdPorNombreTipo(updatedProduct.getProduct_type());
	    
	    if (idTipo == -1) {
	    	idTipo = 1; 
	    }
		
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	    	pst.setString(1, updatedProduct.getName());
	        pst.setDouble(2, updatedProduct.getPrice());
	        pst.setString(3, normalizarTemporadaAIngles(updatedProduct.getSeason()));
	        pst.setBoolean(4, updatedProduct.isIs_active());
	        pst.setInt(5, idTipo);
	        pst.setInt(6, id); 

	        return pst.executeUpdate() > 0;
	    } catch(SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public List<String> obtenerTipos() throws SQLException {
		List<String> tipos = new ArrayList<>();
		String sql = "SELECT name_product_type FROM Product_type"; 
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				tipos.add(rs.getString("name_product_type"));
			}
		}
		return tipos;
	}
	
	private int obtenerIdPorNombreTipo(String nombreTipo) {
	    if (nombreTipo == null || nombreTipo.equals("Seleccionar")) return -1;
	    String sql = "SELECT product_type_id FROM Product_type WHERE name_product_type = ?";
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	        pst.setString(1, nombreTipo);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("product_type_id"); 
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return -1;
	}
	
	private String normalizarTemporadaAIngles(String temporada) {
		if (temporada == null) return "spring";
		switch (temporada.toLowerCase().trim()) {
			case "primavera": return "spring";
			case "verano":    return "summer";
			case "otoño":     return "autumn";
			case "invierno":  return "winter";
			default:          return temporada.toLowerCase().trim(); 
		}
	}

	private String traducirTemporadaAEspanol(String season) {
		if (season == null) return "No especificada";
		switch (season.toLowerCase().trim()) {
			case "spring": return "Primavera";
			case "summer": return "Verano";
			case "autumn":
			case "fall":   return "Otoño";
			case "winter": return "Invierno";
			default:       return season;
		}
	}

}