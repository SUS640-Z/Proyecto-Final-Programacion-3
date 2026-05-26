package models;

public class Order {
	private int id;
	private int userId;
	private String userName; 
	private String orderDate;
	private double total;
	private String status;

	public Order() {
	}

	public Order(int id, int userId, String userName, String orderDate, double total, String status) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.orderDate = orderDate;
		this.total = total;
		this.status = status;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getUserId() { return userId; }
	public void setUserId(int userId) { this.userId = userId; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getOrderDate() { return orderDate; }
	public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

	public double getTotal() { return total; }
	public void setTotal(double total) { this.total = total; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}