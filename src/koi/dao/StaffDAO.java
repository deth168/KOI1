package koi.dao;

import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

import koi.core.Staff;
import koi.util.PasswordUtils;


public class StaffDAO {
	
	private Connection myConn;
	
public StaffDAO() throws Exception{
		
		//get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("koi.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		//myConn = (Connection) DriverManager.getConnection(dburl, user, password);
		myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/koi","root","");
		System.out.println("Staff DAO - DB connection succesful to " + dburl);
		
	}
	
	public void addStaff(Staff staff) throws SQLException {
		PreparedStatement myStmt = null;
		try{
			
			myStmt = myConn.prepareStatement("insert into staff"
					+"(first_name, last_name, email, password)"
					+ " values (?, ?, ?, ?)");
			myStmt.setString(1, staff.getFirstName());
			myStmt.setString(2, staff.getLastName());
			myStmt.setString(3, staff.getEmail());
			
			String encryptedPassword = PasswordUtils.encryptPassword(staff.getPassword());
			myStmt.setString(4, encryptedPassword);
			
			myStmt.executeUpdate();			
		}
		finally{
			if(myStmt!= null)
				myStmt.close();
		}
	}
	
	public boolean authenticate(String plainTextPassword, Staff staff){
		String encryptedPassword = staff.getPassword();
		return PasswordUtils.checkPassword(plainTextPassword, encryptedPassword);
	}
	
	public Staff searchStaff(String email) throws SQLException{
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try{
	
			myStmt = myConn.prepareStatement("select * from staff where email=?");
			myStmt.setString(1, email);
			myRs = myStmt.executeQuery();
			
			//Statement.executeQuery() never returns null if resultset is empty.
			if(!myRs.next()){
				return null;
			}
			else{
				Staff staff = convertRowToStaff(myRs);
				return staff;
			}
		}
		finally{
			if(myRs!= null)
				myRs.close();
			if(myStmt!= null)
				myStmt.close();
		}
	}
	
	private Staff convertRowToStaff(ResultSet myRs) throws SQLException{
		int id = myRs.getInt("staff_id");
		String firstName = myRs.getString("first_name");
		String lastName = myRs.getString("last_name");
		String email = myRs.getString("email");
		String encryptedPassword = myRs.getString("password");
		Staff staff = new Staff(id, firstName, lastName, email, encryptedPassword);
		
		return staff;
	}
}
