package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SentenceDB {
	private Connection connection=null;
	private Statement stmt;
	
    SentenceDB(Connection con){
    	connection=con;
    }
    
	public String getSentence(){
	    int n=(int) (Math.random()*7);
	    String queryString="select sentence from sentences where id="+n+";";
	    String sentence=null;
	    
	    try {
	    	stmt=connection.createStatement();
	    	ResultSet rset=stmt.executeQuery(queryString);
	    	
			if(rset.next())sentence=rset.getString(1);
			System.out.println("stmt:"+stmt);
			System.out.println("sentence:"+sentence);
		    stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	    
	    return sentence;
	}

}
