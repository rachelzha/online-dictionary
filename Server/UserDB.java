package Server;

import java.sql.*;
import java.util.Vector;

public class UserDB{	
	private Connection connection=null;
    private PreparedStatement pstmt;
    private Statement stmt;
	
    UserDB(Connection con){
    	connection=con;
    }
    
	public boolean addUser(String username, String password, String email){
	    String sql = "insert into users (username,password,email) values(?,?,?)";
	 
	       try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			
			pstmt.setString(1, username);
		    pstmt.setString(2, password);
		    pstmt.setString(3, email);
		        
		    pstmt.executeUpdate();
		    pstmt.close();
		    
		    return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public boolean findUser(String username, String password){
		String queryString="select * from users "+
		"where users.username= ? and users.password= ?";
		
		try{
			pstmt=(PreparedStatement)connection.prepareStatement(queryString);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rset=pstmt.executeQuery();
			
			if(rset.next()){				
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
	
	public Vector<String> getAllUsers(){
		Vector<String> allUsers=new Vector<String>();
		try {
			stmt=connection.createStatement();
			ResultSet reset=stmt.executeQuery("select username from users;");
			
			while(reset.next()){
				allUsers.add(reset.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allUsers;
	}
}
