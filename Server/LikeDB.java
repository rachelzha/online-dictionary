package Server;

import java.sql.*;
import java.util.Vector;

public class LikeDB {
	private Connection connection=null;
	private PreparedStatement pstmt;
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
			
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();
			
			pstmt1.close();
			pstmt2.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void add(String username, String word){
		String queryString1="select * from likes where word=?";
		String queryString2="select * from personal_likes where pusername=? and pword=?";
		
		String addString1="insert into likes (word) values (?)";
		String addString2="insert into personal_likes (pusername, pword) values (?,?)";
		
		try{
			pstmt1=(PreparedStatement)connection.prepareStatement(queryString1);
			pstmt1.setString(1, word);
			
			pstmt2=(PreparedStatement)connection.prepareStatement(queryString2);
			pstmt2.setString(1, username);
			pstmt2.setString(2, word);
			
			ResultSet rset1=pstmt1.executeQuery();
			ResultSet rset2=pstmt2.executeQuery();
			
			if(rset1.next()){
			}
			else{
				pstmt1.close();
				
				pstmt1=(PreparedStatement)connection.prepareStatement(addString1);
				pstmt1.setString(1, word);
				
				//System.out.println(pstmt1);
				pstmt1.executeUpdate();
			}
			
			if(rset2.next()){	
			}
			else{
				pstmt2=(PreparedStatement)connection.prepareStatement(addString2);
				pstmt2.setString(1, username);
				pstmt2.setString(2, word);
				
				//System.out.println(pstmt2);
				pstmt2.executeUpdate();
			}
			
			pstmt1.close();
			pstmt2.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public Vector<Integer> getLikes(String word){
		String queryString="select youdao,baidu,jinshan from likes where word=?";
		
		try{
			pstmt=(PreparedStatement)connection.prepareStatement(queryString);
			pstmt.setString(1, word);
			
			ResultSet rset=pstmt.executeQuery();
			
			if(rset.next()){				
				Vector<Integer> vec=new Vector<Integer>();
				vec.add(rset.getInt(1));
				vec.add(rset.getInt(2));
				vec.add(rset.getInt(3));
				
				pstmt.close();
				return vec;
			}
			else{
				pstmt.close();
				return null;
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;//不会执行到
	}
	
	public int getYoudaoLikes(String word){
		return getLikes(word).get(0);
	}
	
	public int getBaiduLikes(String word){
		return getLikes(word).get(1);
	}
	
	public int getJinshanLikes(String word){
		return getLikes(word).get(2);
	}
/*
	public static void main(String[] args){
		LikeDB likedb=new LikeDB();
		
		likedb.changeLikes("Rachel", "apple", "youdao");
		
		System.out.println(likedb.getYoudaoLikes("apple"));
		System.out.println(likedb.getBaiduLikes("apple"));
		System.out.println(likedb.getJinshanLikes("apple"));

		likedb.add("Rachel", "apple");
		likedb.add("Rachel", "orange");
		likedb.add("Ted", "apple");
	}*/
}
