package Server;

import javax.swing.*;

import Server.MultiThreadServer.HandleAClient;
import src.Interface.Window;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Trail extends JFrame{	
	private JTextField jtfNum = new JTextField(9);
	private JTextField jtfUsername=new JTextField(9);
	private JTextField jtfPassword=new JTextField(9);
	private JButton jbtLogin=new JButton("Start");
	
	//Text area for displaying contents
	private JTextArea jta=new JTextArea();
	
	public Socket socket=null;
			
	public static void main(String[] args){
		Trail t=new Trail();
		t.setLocation(200,100);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setSize(700, 500);
		t.setVisible(true);
	}
	
	Trail(){
		//连接
		this.connect();
				
		// 界面
		JPanel jPanel3=new JPanel();
		JPanel jPanel1=new JPanel();
		JPanel jPanel2=new JPanel();
		JPanel jPanel4=new JPanel();

		jPanel3.add(new JLabel("Number"));
		jPanel3.add(jtfNum);
		jPanel1.add(new JLabel("Username"));
		jPanel1.add(jtfUsername);
		jPanel2.add(new JLabel("Message"));
		jPanel2.add(jtfPassword);
		
		jPanel4.setLayout(new FlowLayout());
		jPanel4.add(jPanel3);
		jPanel4.add(jPanel1);
		jPanel4.add(jPanel2);
		jPanel4.add(jbtLogin);
		add(jPanel4,BorderLayout.NORTH);
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("Login");
		setSize(300,250);//width,height
		
		jbtLogin.addActionListener(new ButtonListener());
		
		Thread receiveTask=new Thread(new ReceiveTask());
		receiveTask.start();
	}
	
	public void connect(){
		try{
			//create a socket to connect to the server
			socket = new Socket("172.26.218.88",8080);
			//socket = new Socket("172.26.218.88",8000);
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int n=Integer.parseInt(jtfNum.getText());
			String username=jtfUsername.getText();
			String password=jtfPassword.getText();
			
			try{
				DataOutputStream toServer=new DataOutputStream(socket.getOutputStream());
				//send
				toServer.writeInt(n);
				toServer.writeUTF(username);
				toServer.writeUTF(password);
				toServer.flush();
			}
			catch(IOException ex){
				System.err.println(ex);
			}
		}
	}
	
	class ReceiveTask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{			   
			    while(true){
					DataInputStream fromServer=new DataInputStream(socket.getInputStream());
					
					int type=fromServer.readInt();
					
					switch(type){
					case 1:{//log in
						if(fromServer.readBoolean())jta.append("Log in SUCCESS!\n");
						break;
					}
					case 2:{//register
						if(fromServer.readBoolean())jta.append("Register SUCCESS\n");
						break;
					}
					case 3:{
						int n1=fromServer.readInt();
						int n2=fromServer.readInt();
						int n3=fromServer.readInt();
						
						jta.append("likes: "+n1+"\t"+n2+"\t"+n3+"\n");
						break;
					}
					case 4:{
						String message=fromServer.readUTF();
						jta.append("The message is : " + message+"\n");
						break;
					}
					case 5:{//online users
						ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
						
						Vector<String> onlineUsers=(Vector<String>)ois.readObject();
						jta.append("The online Clients: \n");
						for(int i=0;i<onlineUsers.size();i++){
							jta.append(onlineUsers.get(i)+"\n");
						}
						break;
					}
					case 6:{//all users 
						ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
						
						Vector<String> allUsers=(Vector<String>)ois.readObject();
						jta.append("All Clients: \n");
						for(int i=0;i<allUsers.size();i++){
							jta.append(allUsers.get(i)+"\n");
						}
						break;
					}
					default:break;
					}
			    }
			}
			catch (IOException ex){
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}

}
