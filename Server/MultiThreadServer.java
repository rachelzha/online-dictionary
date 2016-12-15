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
		public DataInputStream dataFromClient=null;
		public DataOutputStream dataToClient=null;
		public ObjectInputStream objectFromClient=null;
		public ObjectOutputStream objectToClient=null;
		
		private Connection dbConn=null;
		private UserDB userdb;
		private LikeDB likedb;
		private MessageDB messagedb;
		private SentenceDB sentencedb;
		
		private String username=null;
		
		//private Lock lock=new ReentrantLock();

				
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;
			try {
				dataFromClient=new DataInputStream(socket.getInputStream());
				dataToClient=new DataOutputStream(socket.getOutputStream());
				objectFromClient=new ObjectInputStream(socket.getInputStream());
				objectToClient=new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dbConn=connectUsingPool();
			//System.out.println(dbConn);
			userdb=new UserDB(dbConn);
			likedb=new LikeDB(dbConn);
			messagedb=new MessageDB(dbConn);
			sentencedb=new SentenceDB(dbConn);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{		
				//continuously serve the client
				while(true){
										
					//receive type from the client
					int type=dataFromClient.readInt();
					
					switch(type){
					case 0:{//message required
						jta.append(username);
						if(username==null)break;

						Vector<Message> messages=messagedb.getMessages(username);
						
						if(messages.size()==0)break;
						
						jta.append("send!\t"+messages.size()+"\n");
						//output
						dataToClient.writeInt(0);
						dataToClient.flush();
						
						objectToClient.writeObject(messages);
						objectToClient.flush();
						
						break;
					}
					case 1:{//log in
						//input
						String usernametmp=dataFromClient.readUTF();
						String password=dataFromClient.readUTF();
						
						jta.append(usernametmp+"\t"+password+"\n");
						
						boolean found=userdb.findUser(usernametmp, password);
						if(found){
							username=usernametmp;
							jta.append("Login: " + username + '\n');

							clients.put(username, this);
						}
						else username=null;
						
						//output
						dataToClient.writeInt(1);
						dataToClient.writeUTF(username);
						dataToClient.flush();
						
						break;
					}
					case 2:{//register
						//input
						String usernametmp=dataFromClient.readUTF();
						String password=dataFromClient.readUTF();
						String email=dataFromClient.readUTF();
							
						boolean success=userdb.addUser(usernametmp, password, email);
						if(success){
							username=usernametmp;
							jta.append("Register: "+username+'\n');
							clients.put(username, this);
						}
						else username=null;
						
						//output
						dataToClient.writeInt(2);
						dataToClient.writeUTF(username);
						dataToClient.flush();

						break;
					}
					case 3://search words
					{	//input
						String word=dataFromClient.readUTF();
				
						
						Vector<Integer>vec=likedb.getLikes(word);
						int baiduLikes=vec.get(0);
						int youdaoLikes=vec.get(1);
						int jinshanLikes=vec.get(2);
						
						//output
						dataToClient.writeInt(3);
						dataToClient.writeInt(baiduLikes);
						dataToClient.writeInt(youdaoLikes);
						dataToClient.writeInt(jinshanLikes);
						
						if(username!=null){
							likedb.add(username, word);
							
							Vector<Integer>vec2=likedb.getPersonalLikes(username, word);
							dataToClient.writeInt(vec2.get(0));//baidu
							dataToClient.writeInt(vec2.get(1));//youdao
							dataToClient.writeInt(vec2.get(2));//jinshan
						}
						
						dataToClient.flush();
						
						break;
					}
					case 4:{
						jta.append("get\n");
						//get everyday sentence
						String sentence=sentencedb.getSentence();
						System.out.println(sentence+"!!!!");
						//String sent="happy";
						
						dataToClient.writeInt(4);
						dataToClient.writeUTF(sentence);
						dataToClient.flush();
						break;
					}
					case 5:{//get online users
						Vector<String> online=new Vector<String>();
						for(Map.Entry<String, HandleAClient> entry : clients.entrySet()) {
						    String key = entry.getKey();
						    
						    online.add(key);
						}
						
						//output
						dataToClient.writeInt(5);
						dataToClient.flush();
						
						objectToClient.writeObject(online);
						objectToClient.flush();
						
						break;
					}
					case 6:{//get all users
						Vector<String> allUsers=userdb.getAllUsers();
						
						dataToClient.writeInt(6);
						dataToClient.flush();
						
						objectToClient.writeObject(allUsers);
						objectToClient.flush();
						
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
						String word=dataFromClient.readUTF();
						String aDict=dataFromClient.readUTF();
						
						likedb.changeLikes(username, word, aDict);
						break;
					}
					
					case 9:{
						//send message
						if(username==null)break;
						
						String receiver=dataFromClient.readUTF();//the user to send message
						
						Card card=(Card)objectFromClient.readObject();
						
						//save to server directory
						String filename=card.saveCard("wordcards");
						
						System.out.println(filename);
						
						messagedb.add(receiver, username, filename);
						
						break;
					}
					default:break;
					}
					//lock.unlock();
				}
			}
			catch(Exception ex){
				//ex.printStackTrace();
				if(username!=null)clients.remove(username);
				jta.append("用户断开连接\n");
				
				try {
					dbConn.close();
					dbConn=null;
					socket.close();
					socket=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}
}

