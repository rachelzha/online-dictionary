package Server;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.swing.*;

import org.apache.commons.dbcp2.BasicDataSource;

public class MultiThreadServer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Text area for displaying contents
	private JTextArea jta=new JTextArea();
	
	private static BasicDataSource pool=null;
	
	static String driverName="com.mysql.jdbc.Driver";
	static String dbURL="jdbc:mysql://127.0.0.1:3306/userInfo";
	static String userName="testuser";		
	static String userPwd="testtoday";
	
	//在线用户
	HashMap<String,HandleAClient> clients=new HashMap<String,HandleAClient>();
		
	public static void main(String[] args){
		new MultiThreadServer();
	}
	
	public MultiThreadServer(){
		setPool();
		
		//place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("Server");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);//it is necessary to show the frame here!
		
		try{
			//create a server socket
			ServerSocket serverSocket=new ServerSocket(8000);
			jta.append("Server started at "+new Date()+'\n');
			
			//number a server socket
			int clientNo=1;
			
			while(true){
				//Listen for a new connection request
				Socket socket = serverSocket.accept();
				
				//Display the client number
				jta.append("Starting thread for client " + clientNo + 
						" at " + new Date() + '\n');
				
				//find the client's host name, and IP address
				InetAddress inetAddress = socket.getInetAddress();
				jta.append("Client " + clientNo + "'s host name is "
						+ inetAddress.getHostName() + "\n");
				jta.append("Client " + clientNo + "'s IP address is "
						+ inetAddress.getHostAddress() + "\n");
				
				//create a new task for the connection
				HandleAClient task = new HandleAClient(socket);
				
				//Start the new thread
				new Thread(task).start();
				
				//Increment clientNo
				clientNo++;
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}
	
	public static void setPool(){
		//连接池
		pool=new BasicDataSource();
		
		pool.setUsername(userName);
		pool.setPassword(userPwd);
		pool.setDriverClassName(driverName);
		pool.setUrl(dbURL);
		
		//设置整个连接池最大连接数
		pool.setMaxTotal(10);
	
		System.out.println("Succeed setting pool");
	}
	
	public static Connection connectUsingPool(){
		Connection con=null;
		try {
            con = pool.getConnection();
            System.out.println("Connected");
        } catch (SQLException e) {
                e.printStackTrace();
        }
		return con;
	}
	
	
	//inner class
	//define the thread class for handling new connection
	class HandleAClient implements Runnable {
		private Socket socket;//a connected socket
		private Connection dbConn=null;
			
		//IO stream
		DataInputStream inputFromClient;
		DataOutputStream outputToClient;
		
		private UserDB userdb;
		private LikeDB likedb;
				
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;
			
			dbConn=connectUsingPool();
			userdb=new UserDB(dbConn);
			likedb=new LikeDB(dbConn);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{		
				//continuously serve the client
				while(true){
					//create data input and output streams
					inputFromClient=new DataInputStream(socket.getInputStream());
					outputToClient=new DataOutputStream(socket.getOutputStream());
					
					//receive type from the client
					int type=inputFromClient.readInt();
						
					switch(type){
					case 1:{//log in
						String username=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();
							
						boolean found=userdb.findUser(username, password);
							
						outputToClient.writeBoolean(found);
						
						jta.append("Login: " + username + '\n');
						//加入在线用户
						if(found)clients.put(username, this);
						
						break;
					}
					case 2:{//register
						String username=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();
						String email=inputFromClient.readUTF();
							
						boolean success=userdb.addUser(username, password, email);
						outputToClient.writeBoolean(success);
						
						//加入在线用户
						if(success)clients.put(username, this);

						break;
					}
					case 3:{//search words with user name
						String username=inputFromClient.readUTF();
						String word=inputFromClient.readUTF();
							
						likedb.add(username, word);
						
						Vector<Integer>vec=likedb.getLikes(word);
						int baiduLikes=vec.get(0);
						int youdaoLikes=vec.get(1);
						int jinshanLikes=vec.get(2);
							
						outputToClient.writeInt(baiduLikes);
						outputToClient.writeInt(youdaoLikes);
						outputToClient.writeInt(jinshanLikes);
						break;
					}
					case 4:{//search words without user name
						String word=inputFromClient.readUTF();
						
						Vector<Integer>vec=likedb.getLikes(word);
						int baiduLikes=vec.get(0);
						int youdaoLikes=vec.get(1);
						int jinshanLikes=vec.get(2);
						
						outputToClient.writeInt(baiduLikes);
						outputToClient.writeInt(youdaoLikes);
						outputToClient.writeInt(jinshanLikes);
						
						break;
					}
					case 5:{//likes
						String username=inputFromClient.readUTF();
						String word=inputFromClient.readUTF();
						String aDict=inputFromClient.readUTF();
						
						likedb.changeLikes(username, word, aDict);
						break;
					}
					case 6:{//send message
						String username=inputFromClient.readUTF();
						String content=inputFromClient.readUTF();
						
						//转发消息
						HandleAClient c=clients.get(username);
						if(c!=null){
							c.sendMsg(content);
						}
					}
					}
						
				}
			}catch (IOException e) {
				System.err.println(e);
			}
			
			
			try {
				dbConn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sendMsg(String content){
			try{
				outputToClient=new DataOutputStream(this.socket.getOutputStream());
				outputToClient.writeUTF(content);
				jta.append("send to " + this.socket + " contents: "+content+'\n');
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}
}

