package models;

public class Address {
	private int id;
	private String neighborhood; 
	private String street; 
	private String reference; 
	private String instructions; 
	private int userId; 
	private String userName; 

	public Address() {}

	public Address(String neighborhood, String street, String reference, String instructions, int userId) {
		this.neighborhood = neighborhood;
		this.street = street;
		this.reference = reference;
		this.instructions = instructions;
		this.userId = userId;
	}

	public Address(int id, String neighborhood, String street, String reference, String instructions, int userId, String userName) {
		this.id = id;
		this.neighborhood = neighborhood;
		this.street = street;
		this.reference = reference;
		this.instructions = instructions;
		this.userId = userId;
		this.userName = userName;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNeighborhood() { return neighborhood; }
	public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }

	public String getReference() { return reference; }
	public void setReference(String reference) { this.reference = reference; }

	public String getInstructions() { return instructions; }
	public void setInstructions(String instructions) { this.instructions = instructions; }

	public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
}