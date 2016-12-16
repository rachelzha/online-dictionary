package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SentenceDB {
	private Connection connection=null;
	private Statement stmt;
	
	private Lock lock=new ReentrantLock();

	
    SentenceDB(Connection con){
    	connection=con;
    }
    
	public String getSentence(){
	    int n=(int) (Math.random()*6)+1;
	    while(n<=0||n>7){
	    	n=(int) (Math.random()*6)+1;
	    }
	    String queryString="select sentence from sentences where id="+n+";";
	    String sentence=null;
	    
	    lock.lock();
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
	    finally{
	    	lock.unlock();
	    }
	    
	    return sentence;
	}

}
