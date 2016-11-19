package userLogin;

import java.sql.*;

public class UserDB{	
	private Connection connection=null;
    private PreparedStatement pstmt;
	
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
	
	public void addUser(String username, String password, String email) throws SQLException{
	    String sql = "insert into users (name,password,email) values(?,?,?)";
	 
	       pstmt = (PreparedStatement) connection.prepareStatement(sql);
	        
	       pstmt.setString(1, username);
	       pstmt.setString(2, password);
	       pstmt.setString(3, email);
	        
	       pstmt.executeUpdate();
	       pstmt.close();
	}
	
	public boolean findUser(String username, String password){
		String queryString="select name,password from users "+
		"where users.name= ? and users.password= ?";
		
		try{
			pstmt=(PreparedStatement)connection.prepareStatement(queryString);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rset=pstmt.executeQuery();
			
			if(rset.next()){
				String username_real=rset.getString(1);
				
				return true;
			}
			else{
				return false;			
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
}
