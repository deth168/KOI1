package koi.core;

public class DrinkItem {

	private int drinkCode;
	private String drinkName;
	private String drinkCategory;
	private float price;
	private int quantity;
	
	public DrinkItem(int drinkCode, String drinkName, String DrinkCategory, float price, int qty) {
		this.drinkCode = drinkCode;
		this.drinkName = drinkName;
		this.drinkCategory = DrinkCategory;
		this.price = price;
		this.quantity = qty;
	}
	
	public int getDrinkCode() {
		return drinkCode;
	}
	
	public String getDrinkName() {
		return this.drinkName;
	}
	
	public void setDrinkCode(int drinkCode) {
		this.drinkCode = drinkCode;
	}
	
	public String getDrinkCategory() {
		return this.drinkCategory;
	}
	
	public void setDrinkCategory(String drinkCategory) {
		this.drinkCategory = drinkCategory;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int qty) {
		this.quantity = qty;
	}
	
	
	public String toString() {
		return String.format("DrinkItem [DrinkCode = %s, DrinkName = %s, DrinkCategory = %s, Price = %s, Quanity = %s]"
				, drinkCode,drinkName,drinkCategory,price, quantity);
	}
}
