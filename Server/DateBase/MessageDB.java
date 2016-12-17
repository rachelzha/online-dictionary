package Server.DateBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import src.information.Card;
import src.information.Message;

public class MessageDB {
	private Connection connection=null;
	private PreparedStatement pstmt;

	
    public MessageDB(Connection con){
    	connection=con;
    }
	
	public void add(String receiver,String sender, String photoname){
		// query are receiver and sender exist
		String queryString="select * from users where users.username=?";
				
		try{
			pstmt=(PreparedStatement)connection.prepareStatement(queryString);	
			pstmt.setString(1, receiver);	
			ResultSet rset=pstmt.executeQuery();	
			if(!rset.next())return;
			
			pstmt.setString(1, sender);
			rset=pstmt.executeQuery();	
			if(!rset.next())return;
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
				
		// add message
		String addString="insert into all_message (receiver,sender,photoname) values (?,?,?)";
		
		try{
			pstmt=(PreparedStatement)connection.prepareStatement(addString);
			pstmt.setString(1, receiver);
			pstmt.setString(2, sender);
			pstmt.setString(3, photoname);
			
			
			pstmt.executeUpdate();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public Vector<Message> getMessages(String receiver){
		String queryString="select sender,photoname,time from all_message where receiver=?";
		String deleteString="delete from all_message where receiver=?";
		Vector<Message> vec=new Vector<Message>();

		try{
			// find the messages
			pstmt=(PreparedStatement)connection.prepareStatement(queryString);
			pstmt.setString(1, receiver);
			
			ResultSet rset=pstmt.executeQuery();
			
			while(rset.next()){			
				String sender=rset.getString(1);
				String photoname=rset.getString(2);
				String time=rset.getString(3);
				
				Message message=new Message(sender,new Card(photoname),time);
				vec.add(message);
			}
		
			//delete the messages
			pstmt=(PreparedStatement)connection.prepareStatement(deleteString);
			pstmt.setString(1, receiver);
			
			pstmt.executeUpdate();
			
			pstmt.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return vec;
	}
}
