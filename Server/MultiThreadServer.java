package Server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.imageio.ImageIO;
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
		//数据库连接池
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
	
	//配置数据库连接池
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
	
	//连接数据库
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
		
		private String username=null;
				
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;
			
			dbConn=connectUsingPool();
			userdb=new UserDB(dbConn);
			likedb=new LikeDB(dbConn);
			messagedb=new MessageDB(dbConn);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{		
				//continuously serve the client
				while(true){
					DataInputStream inputFromClient=new DataInputStream(socket.getInputStream());
					
					//receive type from the client
					int type=inputFromClient.readInt();
						
					switch(type){
					case 0:{//message required
						if(username==null)break;
						
						Vector<String> filenames=messagedb.getMessages(username);
						
						int n=filenames.size();
						
						//output
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
						username=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();

						boolean found=userdb.findUser(username, password);
						
						//output
						DataOutputStream outputToClient=new DataOutputStream(socket.getOutputStream());
						outputToClient.writeInt(1);
						outputToClient.writeBoolean(found);
						outputToClient.flush();
						
						jta.append("Login: " + username + '\n');
						//加入在线用户
						if(found)clients.put(username, this);
						
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
						//加入在线用户
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
					case 7:{//likes
						//input
						String word=inputFromClient.readUTF();
						String aDict=inputFromClient.readUTF();
						
						likedb.changeLikes(username, word, aDict);
						break;
					}
					
					case 8:{//退出登录
						if(username!=null)clients.remove(username);
						username=null;
						break;
					}
					default:break;
					}
					
				}
			}
			catch(Exception ex){
				if(username!=null)clients.remove(username);
				jta.append("客户端断开连接\n");
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
		
		public void sendMsg(String content){
			try{
				DataOutputStream outputToClient=new DataOutputStream(this.socket.getOutputStream());
				outputToClient.writeInt(4);
				outputToClient.writeUTF(content);
				outputToClient.flush();
				jta.append("send to " + this.socket + " contents: "+content+'\n');
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}
}

