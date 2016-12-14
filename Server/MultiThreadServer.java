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
		

		pool.setMaxTotal(2);
	
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
		private Socket socket;//a connected socket
		
		private Connection dbConn=null;
		private UserDB userdb;
		private LikeDB likedb;
		private MessageDB messagedb;
		private SentenceDB sentencedb;
		
		private String username=null;
		
		private Lock lock=new ReentrantLock();

				
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;
			
			dbConn=connectUsingPool();
			System.out.println(dbConn);
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
					lock.lock();
					
					DataInputStream inputFromClient=new DataInputStream(socket.getInputStream());
					
					//receive type from the client
					int type=inputFromClient.readInt();
						
					switch(type){
					case 0:{//message required
						if(username==null)break;

						Vector<String> filenames=messagedb.getMessages(username);
						if(filenames.size()!=0){
							jta.append("Send to "+username+":\n");
							for(int i=0;i<filenames.size();i++){
								jta.append(filenames.get(i)+'\n');
							}
						}
						//output
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(0);
						outputToClient.flush();
						
						Vector<Card> cards=new Vector<Card>();
						
						for(int i=0;i<filenames.size();i++){
							cards.add(new Card(filenames.get(i)));
						}
						
						ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
						objectOutputStream.writeObject(cards);
						objectOutputStream.flush();
						
						break;
					}
					case 1:{//log in
						//input
						String usernametmp=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();
						jta.append(usernametmp+"\t"+password+"\n");
						
						boolean found=userdb.findUser(usernametmp, password);
						
						//output
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(1);
						outputToClient.writeBoolean(found);
						outputToClient.flush();
						
						if(found){
							username=usernametmp;
							jta.append("Login: " + username + '\n');

							clients.put(username, this);
						}
						break;
					}
					case 2:{//register
						//input
						username=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();
						String email=inputFromClient.readUTF();
							
						boolean success=userdb.addUser(username, password, email);
						
						//output
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(2);
						outputToClient.writeBoolean(success);
						outputToClient.flush();
						
						jta.append("Register: " + username + '\n');

						if(success)clients.put(username, this);

						break;
					}
					case 3://search words
					{	//input
						String word=inputFromClient.readUTF();
							
						if(username!=null)likedb.add(username, word);
						
						Vector<Integer>vec=likedb.getLikes(word);
						int baiduLikes=vec.get(0);
						int youdaoLikes=vec.get(1);
						int jinshanLikes=vec.get(2);
						
						//output
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(3);
						outputToClient.writeInt(baiduLikes);
						outputToClient.writeInt(youdaoLikes);
						outputToClient.writeInt(jinshanLikes);
						outputToClient.flush();
						
						break;
					}
					case 4:{//send message
						String receiver=inputFromClient.readUTF();//the user to send message
						
						ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());			
						Card card=(Card)ois.readObject();
						
						//save to server directory
						String filename=card.saveCard("wordcards");
						
						messagedb.add(receiver, "Rachel", filename);
						
						break;
					}
					case 5:{//get online users
						Vector<String> online=new Vector<String>();
						for(Map.Entry<String, HandleAClient> entry : clients.entrySet()) {
						    String key = entry.getKey();
						    
						    online.add(key);
						}
						
						//output
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(5);
						outputToClient.flush();
						
						ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						toClient.writeObject(online);
						toClient.flush();
						
						break;
					}
					case 6:{//get all users
						Vector<String> allUsers=userdb.getAllUsers();
						
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(6);
						outputToClient.flush();
						
						ObjectOutputStream toClient=new ObjectOutputStream(socket.getOutputStream());
						toClient.writeObject(allUsers);
						toClient.flush();
						
						break;
					}
					case 7:{//get everyday sentence
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						String sentence=sentencedb.getSentence();
						System.out.println(sentence+"!!!!");
						//String sent="happy";
						
						outputToClient.writeInt(7);
						outputToClient.writeUTF(sentence);
						outputToClient.flush();
						break;
					}
					case 8:{//likes
						//input
						String word=inputFromClient.readUTF();
						String aDict=inputFromClient.readUTF();
						
						likedb.changeLikes(username, word, aDict);
						break;
					}
					
					case 9:{//退出
						if(username!=null)clients.remove(username);
						username=null;
						break;
					}
					default:break;
					}
					lock.unlock();
				}
			}
			catch(Exception ex){
				//ex.printStackTrace();
				if(username!=null)clients.remove(username);
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jta.append("用户断开连接\n");
			}
			finally {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
		}
	
	}
}

