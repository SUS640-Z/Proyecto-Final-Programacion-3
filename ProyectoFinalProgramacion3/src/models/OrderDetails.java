package models;

public class OrderDetails {
	
	private int id;
	private int order_id;
	private String client_name;
	private String product_name;
	private int quantity;
	
	public OrderDetails() {
	}
	
	public OrderDetails(int id, int order_id, String client_name, String product_name, int quantity) {
		this.id = id;
		this.order_id = order_id;
		this.client_name = client_name;
		this.product_name = product_name;
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	

}
