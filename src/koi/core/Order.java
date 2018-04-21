package koi.core;

public class Order {
	
	private int orderId;
	private int staffId;
	private float orderTotal;
	
	public Order(int orderId, int staffId, float orderTotal) {
		this.orderId = orderId;
		this.staffId = staffId;
		this.orderId = orderId;
	}
	
	public int getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getStaffId() {
		return this.staffId;
	}
	
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	
	public float getOrderTotal() {
		return this.orderTotal;
	}
	
	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}
	
	public String toString() {
		return String.format("Order = [OrderId = %s, StaffId = %s, OrderTotal = %s]", 
				orderId, staffId, orderTotal);
	}
}
