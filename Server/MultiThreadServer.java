package Server;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
		
		ServerSocket serverSocket=null;
		try{
			//create a server socket
			serverSocket=new ServerSocket(8080);
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
		finally{
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public static void setPool(){

		pool=new BasicDataSource();
		
		pool.setUsername(userName);
		pool.setPassword(userPwd);
		pool.setDriverClassName(driverName);
		pool.setUrl(dbURL);
		

		pool.setMaxTotal(1000);
	
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
	
	
	// listen
	//define the thread class for handling new connection
	class HandleAClient implements Runnable {
		private Socket socket=null;//a connected socket
		private ObjectInputStream fromClient=null;
		private ObjectOutputStream toClient=null;
		
		private String username=null;
		
		//private Lock lock=new ReentrantLock();

				
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;	
			try {
				fromClient=new ObjectInputStream(socket.getInputStream());
				toClient=new ObjectOutputStream(socket.getOutputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{		
				//continuously serve the client
				while(true){			
					//receive type from the client
					
					int type=(int)fromClient.readObject();
					
					switch(type){
					case 0:{//message required
						jta.append(username);
						if(username==null)break;

						Connection dbConn=connectUsingPool();
						MessageDB messagedb=new MessageDB(dbConn);
						Vector<Message> messages=messagedb.getMessages(username);
						dbConn.close();
						
						jta.append("send!\t"+messages.size()+"\n");
						
						toClient.writeObject(messages);
						
						break;
					}
					case 1:{//log in
						//input
						//String usernametmp=data(String)fromClient.readObject();
						//String password=data(String)fromClient.readObject();
						String usernametmp=(String)fromClient.readObject();
						String password=(String)fromClient.readObject();
						
						jta.append(usernametmp+"\t"+password+"\n");
						
						Connection dbConn=connectUsingPool();
						UserDB userdb=new UserDB(dbConn);
						boolean found=userdb.findUser(usernametmp, password);
						dbConn.close();
						
						if(found){
							username=usernametmp;
							jta.append("Login: " + username + '\n');

							clients.put(username, this);
						}
						else username=null;
						
						//output
						//datatoClient.writeObject(1);
						//datatoClient.wirteObject(username);
						//dataToClient.flush();
						//ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						toClient.writeObject(username);
						
						break;
					}
					case 2:{//register
						//input
						//String usernametmp=data(String)fromClient.readObject();
						//String password=data(String)fromClient.readObject();
						//String email=data(String)fromClient.readObject();
						String usernametmp=(String)fromClient.readObject();
						String password=(String)fromClient.readObject();
						
						Connection dbConn=connectUsingPool();
						UserDB userdb=new UserDB(dbConn);
						boolean success=userdb.addUser(usernametmp, password, "a");
						dbConn.close();
						
						if(success){
							username=usernametmp;
							jta.append("Register: "+username+'\n');
							clients.put(username, this);
						}
						else username=null;
						
						//output
						//datatoClient.writeObject(2);
						//datatoClient.wirteObject(username);
						//dataToClient.flush();
						//ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						toClient.writeObject(username);

						break;
					}
					case 3://search words
					{	//input
						//String word=data(String)fromClient.readObject();
						String word=(String)fromClient.readObject();
						
						Connection dbConn=connectUsingPool();
						LikeDB likedb=new LikeDB(dbConn);
						Vector<Integer>vec=likedb.getLikes(word);
						
						
						int baiduLikes=vec.get(0);
						int youdaoLikes=vec.get(1);
						int jinshanLikes=vec.get(2);
						
						//output
						toClient.writeObject(baiduLikes);
						toClient.writeObject(youdaoLikes);
						toClient.writeObject(jinshanLikes);
						
						if(username!=null){
							likedb.add(username, word);
							
							Vector<Integer>vec2=likedb.getPersonalLikes(username, word);
							toClient.writeObject(vec2.get(0));//baidu
							toClient.writeObject(vec2.get(1));//youdao
							toClient.writeObject(vec2.get(2));//jinshan
						}
						
						dbConn.close();
						break;
					}
					case 4:{
						jta.append("get\n");
						//get everyday sentence
						Connection dbConn=connectUsingPool();
						SentenceDB sentencedb=new SentenceDB(dbConn);
						String sentence=sentencedb.getSentence();
						dbConn.close();
						
						System.out.println(sentence+"!!!!");
						//String sent="happy";
						
						//ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						toClient.writeObject(sentence);
						break;
					}
					case 5:{//get online users
						Vector<String> online=new Vector<String>();
						for(Map.Entry<String, HandleAClient> entry : clients.entrySet()) {
						    String key = entry.getKey();
						    
						    online.add(key);
						}
						
						//output
						//ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						
						toClient.writeObject(online);
						
						break;
					}
					case 6:{//get all users
						Connection dbConn=connectUsingPool();
						UserDB userdb=new UserDB(dbConn);
						Vector<String> allUsers=userdb.getAllUsers();
						dbConn.close();
						
						//ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						
						toClient.writeObject(allUsers);
						
						break;
					}
					case 7:{
						//退出
						if(username!=null)clients.remove(username);
						username=null;
						break;
					}
					case 8:{//likes
						//input
						String word=(String)fromClient.readObject();
						String aDict=(String)fromClient.readObject();
						
						Connection dbConn=connectUsingPool();
						LikeDB likedb=new LikeDB(dbConn);
						likedb.changeLikes(username, word, aDict);
						dbConn.close();
						
						break;
					}
					
					case 9:{
						//send message
						if(username==null)break;
						
						String receiver=(String)fromClient.readObject();//the user to send message
						Card card=(Card)fromClient.readObject();
						
						//get receivers
						String[] receivers=receiver.split(";");
						
						//save to server directory
						String filename=card.saveCard("wordcards");
						
						System.out.println(filename);
						
						Connection dbConn=connectUsingPool();
						MessageDB messagedb=new MessageDB(dbConn);
						for(int i=0;i<receivers.length;i++){
							messagedb.add(receivers[i], username, filename);
						}
						dbConn.close();
						
						
						break;
					}
					default:break;
					}
				}
			}
			catch(Exception ex){
				//ex.printStackTrace();
				if(username!=null)clients.remove(username);
				jta.append("用户断开连接\n");
				//ex.printStackTrace();
				try {
					socket.close();
					System.out.println("socket closed");
					socket=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	
	}
}

