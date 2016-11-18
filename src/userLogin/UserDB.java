package userLogin;

import java.sql.*;

public class UserDB {
	private Statement stmt;
	
	//contributer
	UserDB(){
		initDB();
	}
	
	private void initDB(){
		try {
			//load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			
			//establish a connection
			Connection connection=DriverManager.getConnection("jdbc:mysql://@localhost/userInfo","testuser","testtoday");
			System.out.println("Database connected");
			
			//create a statement
			stmt=connection.createStatement();
			
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fail!");
		}
	}
	
	public void addUser(User user){
		
	}
	
	public boolean findUser(String username, String password){
		return true;
	}
	
	public static void main(String[] args){
		UserDB userdb=new UserDB();
	}
}
