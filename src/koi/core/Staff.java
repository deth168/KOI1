package koi.core;

public class Staff {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	public Staff(String fname, String lname, String mail, String pwd) {
		firstName = fname;
		lastName = lname;
		email = mail;
		password = pwd;
	}
	
	public Staff(int id, String fname, String lname, String mail, String pwd) {
		this(fname, lname, mail, pwd);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lname) {
		this.lastName = lname;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String fname) {
		this.firstName = fname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String mail) {
		this.email = mail; 
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pwd) {
		this.password = pwd;
	}
	
	public String toString() {
		return String.format("Staff [Id = %s, FirstName = %s, Lastname = %s, Email = %s]"
				, id,firstName,lastName,email);
	}
}
