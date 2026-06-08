package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBaseConnection {

	private static Connection connection;
	
	public static Connection getConnection() {
		
		try {
			
			if(connection == null || connection.isClosed()) {
				Properties props = new Properties();
				
				InputStream input = DataBaseConnection.class.getClassLoader().getResourceAsStream("config/database.properties");
				props.load(input);
				
				String url = props.getProperty("db.url");
				String user = props.getProperty("db.user");
				String driver = props.getProperty("db.driver");
				String password = props.getProperty("db.password");
				Class.forName(driver);

				//String passwordForzada = "xXSap@2005Xx"; 
				
				connection = DriverManager.getConnection(url, user, password);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}