package models;

public class Product {
	private int id;
	private String name;
	private double price;
	private String season;
	private boolean is_active;
	private String product_type;
	
	public Product() {

	}

	public Product(int id, String name, double price, String season, boolean is_active, String product_type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.season = season;
		this.is_active = is_active;
		this.product_type = product_type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	
	

}
