package koi.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import koi.core.Order;
import koi.core.Staff;

public class OrderDAO {
private Connection myConn;
	
	public OrderDAO() throws Exception{
		
		//get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("koi.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user, password);
		
		System.out.println("Order DAO - DB connection succesful to " + dburl);
		
	}
	
	private Order convertRowToOrder(ResultSet myRs) throws SQLException{
		int id = myRs.getInt("id");
		int staffId = myRs.getInt("staff_id");
		int orderTotal = myRs.getInt("order_total");
		Order tempOrder = new Order(id, staffId, orderTotal);
		return tempOrder;
		
	}
	
	public List<Order> getOrderHistory(Staff staff) throws SQLException{
		List<Order> list = new ArrayList<Order>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try{
			String query = "select * from orders where orders.staff_id=?";	
			myStmt = myConn.prepareStatement(query);
			
			myStmt.setInt(1, staff.getId());
			myRs = myStmt.executeQuery();
			while(myRs.next()){
				Order temp = convertRowToOrder(myRs);
				list.add(temp);
			}
			return list;			
		}
		finally{
			if(myRs != null)
				myRs.close();
			if(myStmt != null)
				myStmt.close();
		}
	}
}
