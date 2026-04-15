package models;

public class User {
	private String email;
	private String password;
	private String name;
	private String lastName;
	
	public User(String email, String password,String name, String lastName) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
	}

	public User(String email, String password) {
		this.name = ""; 
		this.lastName = ""; 
		this.email = email;
		this.password = password;
	}

	public String toCsv() {
		return name + "," + lastName + "," + email + "," + password;
	}

	public static User fromCsv(String userData) {
		String[] data = userData.split(",");
		
		String name = data[0];
		String lastName = data[1];
		String email = data[2];
		String password = data[3];
		
		return new User(name, lastName, email, password);
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
}