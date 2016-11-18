package userLogin;

import java.sql.*;

public class UserDB {	
	Connection connection=null;
	
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
			connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userInfo","testuser","testtoday");
			System.out.println("Database connected");
			
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fail!");
		}
	}
	
	public void addUser(User user){
	  //  int i = 0;
	    String sql = "insert into users (name,password,email) values(?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) connection.prepareStatement(sql);
	        pstmt.setString(1, user.getUsername());
	        pstmt.setString(2, user.getPassword());
	        pstmt.setString(3, user.getEmail());
	        //i=
	        pstmt.executeUpdate();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	  //  return i;
	}
	
	public boolean findUser(String username, String password){
		return true;
	}
	
	public static void main(String[] args){
		UserDB userdb=new UserDB();
		
		User user1=new User();
		user1.setId(0);
		user1.setUsername("Rachel");
		user1.setPassword("rr");
		user1.setEmail("aa");
		
		userdb.addUser(user1);
	}
}
