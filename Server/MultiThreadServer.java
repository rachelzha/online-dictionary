package Server;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class MultiThreadServer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Text area for displaying contents
	private JTextArea jta=new JTextArea();
	private UserDB userdb=new UserDB();
	private LikeDB likedb=new LikeDB();
	
	//io stream
	DataInputStream inputFromClient;
	DataOutputStream outputToClient;
	
	public static void main(String[] args){
		new MultiThreadServer();
	}
	
	public MultiThreadServer(){
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
				
				//find the client's host name, and ip address
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
	
	//inner class
	//define the thread class for handling new connection
	class HandleAClient implements Runnable {
		private Socket socket;//a connected socket
			
		//construct a thread
		public HandleAClient(Socket socket){
			this.socket=socket;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				//create data input and output streams
				inputFromClient=new DataInputStream(
						socket.getInputStream());
				outputToClient=new DataOutputStream(
						socket.getOutputStream());
					
				//continuously serve the client
				while(true){
					//receive type from the client
					int type=inputFromClient.readInt();
					
					switch(type){
					case 1:{//log in
						String username=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();
						
						boolean found=userdb.findUser(username, password);
						
						outputToClient.writeBoolean(found);
						break;
					}
					case 2:{//register
						String username=inputFromClient.readUTF();
						String password=inputFromClient.readUTF();
						String email=inputFromClient.readUTF();
						
						boolean success=userdb.addUser(username, password, email);
						outputToClient.writeBoolean(success);
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
					}
					
				}
			}
			catch(IOException e){
				System.err.println(e);
			}
		}
	}
}

