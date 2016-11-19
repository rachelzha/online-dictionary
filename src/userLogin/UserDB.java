package userLogin;

import java.sql.*;

public class UserDB {	
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
	
	public void addUser(User user){
	    String sql = "insert into users (name,password,email) values(?,?,?)";
	    try {
	        pstmt = (PreparedStatement) connection.prepareStatement(sql);
	        
	        pstmt.setString(1, user.getUsername());
	        pstmt.setString(2, user.getPassword());
	        pstmt.setString(3, user.getEmail());
	        
	        pstmt.executeUpdate();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void findUser(String username, String password){
		String queryString="select name,password from users "+
		"where users.name= ? and users.password= ?";
		
		try{
			pstmt=(PreparedStatement)connection.prepareStatement(queryString);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rset=pstmt.executeQuery();
			
			if(rset.next()){
				String username_real=rset.getString(1);
				
				//display
				System.out.println("hello "+ username_real);
			}
			else{
				System.out.println("not found");
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		UserDB userdb=new UserDB();
		
		User user1=new User();
		user1.setId(0);
		user1.setUsername("Rachel");
		user1.setPassword("rr");
		user1.setEmail("aa");
		
		//userdb.addUser(user1);
		userdb.findUser("Rachel", "rr");
	}
}
