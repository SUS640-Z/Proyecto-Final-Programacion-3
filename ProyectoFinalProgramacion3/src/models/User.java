package models;

public class User {
	private int id;
	private String email;
	private String password;
	private String name;
	private String lastName;
	private String imagePath; 
	private String telefono;
	private String genero;
	private String fechaNacimiento;
	private String rol;
	
	public User() {
		
	}

	public User(int id, String name, String lastName, String email, String password, String imagePath, String telefono, String genero, String fechaNacimiento, String role) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
		this.telefono = telefono;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.rol = role;
	}
	
	public User(int id, String name, String lastName, String email, String password, String imagePath, String telefono, String genero, String fechaNacimiento) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
		this.telefono = telefono;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public User(String name, String lastName, String email, String password, String imagePath, String telefono, String genero, String fechaNacimiento) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
		this.telefono = telefono;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
	}

	public User(String name, String lastName, String email, String password, String imagePath, String telefono, String genero, String fechaNacimiento,String role) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
		this.telefono = telefono;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.rol = role;
	}

	public User(int id,String email, String password) {
		this.id=id;
		this.name = ""; 
		this.lastName = ""; 
		this.email = email;
		this.password = password;
		this.imagePath = "";
	}
	
	public User(String email, String password) {
		this.name = ""; 
		this.lastName = ""; 
		this.email = email;
		this.password = password;
		this.imagePath = "";
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getImagePath() { return imagePath; }
	public void setImagePath(String imagePath) { this.imagePath = imagePath; }
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }

	public String getGenero() { return genero; }
	public void setGenero(String genero) { this.genero = genero; }

	public String getFechaNacimiento() { return fechaNacimiento; }
	public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

	public String getRol() {return rol;}
	public void setRol(String role) {this.rol = role;}
	
}