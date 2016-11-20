package src.userLogin;

import java.sql.*;

public class LikeDB {
	private Connection connection=null;
    private PreparedStatement pstmt1;
    private PreparedStatement pstmt2;

    	
	//contributer
	public LikeDB(){
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
	
	public void changeLikes(String username, String word,String aDict){
		String sql1,sql2;

		try{
			sql1="update personal_likes set "+aDict + "=0-"+aDict + 
					" where pusername=? and pword=?;";
			sql2="update likes set "+aDict+"="+aDict+
					"+(select "+aDict+
					" from personal_likes where pusername = ? and pword=?) where word=?;";
			
			//personal_likes
			pstmt1=(PreparedStatement)connection.prepareStatement(sql1);
			pstmt1.setString(1, username);
			pstmt1.setString(2, word);
			
			//likes
			pstmt2=(PreparedStatement)connection.prepareStatement(sql2);
			pstmt2.setString(1, username);
			pstmt2.setString(2, word);
			pstmt2.setString(3, word);
			
			//System.out.println(pstmt1);
			//System.out.println(pstmt2);
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();

		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args){
		LikeDB likedb=new LikeDB();
		
		likedb.changeLikes("Rachel", "apple", "youdao");
		
	}
}
