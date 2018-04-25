package koi.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import koi.core.DrinkItem;
import koi.core.Staff;

public class DrinkDAO {
	
private Connection myConn;
	
	public DrinkDAO() throws Exception{
		
		//get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("koi.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user, password);
		
		System.out.println("drink DAO - DB connection succesful to " + dburl);
		
	}
	
	//qantity column keep tracks of the list of items selected by the user. If not null, the corresponding
	//food item is selected by the staff.
	public void updateQuantity(DrinkItem temp, int val) throws SQLException{
		
		PreparedStatement myStmt = null;
		try{
			myStmt = myConn.prepareStatement("update drink set quantity=? where drink_code=?");
			myStmt.setInt(1, val);
			myStmt.setInt(2, temp.getDrinkCode());
			myStmt.executeUpdate();
		}
		finally{
			if(myStmt != null)
				myStmt.close();
		}
		
	}
	
	//will be used to delete the item from cart, i.e, set quantity to null & refresh the cart table view.
	public void setQuantityToNull(DrinkItem drinkItem) throws SQLException{
		PreparedStatement myStmt = null;
		try{
			myStmt = myConn.prepareStatement("update drink set quantity = null where drink_code =?");
			myStmt.setInt(1, drinkItem.getDrinkCode());
			myStmt.executeUpdate();
		}
		finally{
			if(myStmt != null)
				myStmt.close();
		}
	}
	
	public float getNetAmount() throws SQLException{
		Statement myStmt = null;
		ResultSet myRs = null;
		try{
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select sum(price*quantity) as sum from drink where quantity is not null");
			myRs.next();
			float sum = myRs.getFloat("sum");
			return sum;
		}
		finally{
			if(myRs != null)
				myRs.close();
			if(myStmt != null)
				myStmt.close();
		}
	}
	
	public float addOrder(Staff staff, float netAmount) throws SQLException{
		PreparedStatement myStmt = null;
		Statement myStmt1 = null;
		ResultSet myRs = null;
		try{
			myStmt = myConn.prepareStatement("insert into orders (staff_id, order_total) "
					+ "values(?, ?)");
			myStmt.setInt(1, staff.getId());
			myStmt.setFloat(2, netAmount);
			myStmt.executeUpdate();
			
			myStmt1 = myConn.createStatement();
			//get the last auto incremented value (i.e, order id).
			myRs = myStmt.executeQuery("select last_insert_id() as order_id");
			myRs.next();
			int orderId = myRs.getInt("order_id");
			System.out.println(orderId);
			return orderId;
		}
		finally{
			if(myStmt != null)
				myStmt.close();
			if(myStmt1 != null)
				myStmt1.close();
			if(myRs != null)
				myRs.close();
		}
		
	}
	
	public void vacateQuantityColumn(){
		
		Statement myStmt = null;
		try {
			myStmt = myConn.createStatement();
			myStmt.executeUpdate("update drink set quantity= null where quantity is not null");
		}
		catch (SQLException e) {
			System.out.println("Error while vacating Quantity column.");
			e.printStackTrace();
		}
	}
	
	public boolean isEmptyQuantityColumn() throws SQLException{
		ResultSet myRs = null;
		Statement myStmt = null;
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select count(quantity) as count from drink");
			myRs.next();
			int count = myRs.getInt("count");
			return count > 0 ? true : false ; 
		}
		finally{
			if(myRs != null)
				myRs.close();
			if(myStmt != null)
				myStmt.close();
		}
	}
	
	private DrinkItem convertRowToDrinkItem(ResultSet myRs) throws SQLException{
		int code = myRs.getInt("drink_code");
		String name = myRs.getString("drink_name");
		String category = myRs.getString("drink_category");
		float price = myRs.getFloat("price");
		int quantity = myRs.getInt("quantity");
		
		DrinkItem tempDrink = new DrinkItem(code, name, category, price, quantity);
		
		return tempDrink;
	}
	
	public List<DrinkItem> getAllDrinkItems() throws Exception{
		List<DrinkItem> list = new ArrayList<DrinkItem>();
		Statement myStmt = null;
		ResultSet myRes = null;
		try{		
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select * from drink order by drink_code");
			
			while(myRes.next()){
				DrinkItem temp = convertRowToDrinkItem(myRes);
				list.add(temp);
			}
			return list;
		}
		finally{
			if(myRes != null)
				myRes.close();
			if(myStmt != null)
				myStmt.close();
		}		
		
	}
	
	public List<DrinkItem> getCartItems() throws Exception{
		List<DrinkItem> list = new ArrayList<DrinkItem>();
		Statement myStmt = null;
		ResultSet myRes = null;
		try{
			myStmt = myConn.createStatement();
			myRes= myStmt.executeQuery("select * from drink where quantity is not null");
			
			while(myRes.next()){
				DrinkItem temp = convertRowToDrinkItem(myRes);
				list.add(temp);
			}
			return list;
		}
		finally{
			if(myRes != null)
				myRes.close();
			if(myStmt != null)
				myStmt.close();
		}
	}
	
	public static void main(String [] args){
		try {
			DrinkDAO drinkDAO = new DrinkDAO();
			List<DrinkItem> list = drinkDAO.getAllDrinkItems(); 
			for(DrinkItem temp : list){
				System.out.println(temp);
			}
			float totalAmt = drinkDAO.getNetAmount();
			System.out.println(totalAmt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
