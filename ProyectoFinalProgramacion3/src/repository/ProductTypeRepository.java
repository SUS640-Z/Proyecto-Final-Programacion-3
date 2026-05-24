package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import models.ProductType;


public class ProductTypeRepository {
	
	public void save(ProductType productType) throws SQLException {
	    String sql = "INSERT INTO Product_type (name_product_type) VALUES (?)";
	    
	    try(Connection connection = DataBaseConnection.getConnection();
	        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        pst.setString(1, productType.getName());         
	        pst.executeUpdate();

	    }
	}

	public List<ProductType> getProductsType() throws SQLException {
		List<ProductType> productsType = new ArrayList<>();
		String sql = "SELECT * FROM Product_type";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				ProductType productType = new ProductType(
					rs.getInt("product_type_id"), 
					rs.getString("name_product_type") 
				);
				productsType.add(productType);
			}
		}
		return productsType;		
	}

	public boolean delete(int id) {
		String sql="DELETE FROM Product_type WHERE product_type_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	

	public boolean update(int id, ProductType updatedProductType) {
	    String sql = "UPDATE Product_type SET name_product_type = ? WHERE product_type_id = ?";

	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	        pst.setString(1, updatedProductType.getName());
	        pst.setInt(2, id);

	        return pst.executeUpdate() > 0;
	    } catch(SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	


}
