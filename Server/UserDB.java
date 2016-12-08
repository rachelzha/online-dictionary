package Server;

import java.sql.*;

public class UserDB{	
	private Connection connection=null;
    private PreparedStatement pstmt;
	
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
}
