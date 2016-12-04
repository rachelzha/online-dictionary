package Server;

import javax.swing.*;

import Server.MultiThreadServer.HandleAClient;
import src.Interface.Window;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Trail extends JFrame{	
	private JTextField jtfNum = new JTextField(9);
	private JTextField jtfUsername=new JTextField(9);
	private JTextField jtfPassword=new JTextField(9);
	private JButton jbtLogin=new JButton("Log in");
	
	public Socket socket=null;
	public DataInputStream fromServer;
	public DataOutputStream toServer;
			
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

		jPanel3.add(new JLabel("Number"));
		jPanel3.add(jtfNum);
		jPanel1.add(new JLabel("Username"));
		jPanel1.add(jtfUsername);
		jPanel2.add(new JLabel("Message"));
		jPanel2.add(jtfPassword);
		
		setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
		add(jPanel3);
		add(jPanel1);
		add(jPanel2);
		add(jbtLogin);
		
		setTitle("Login");
		setSize(300,250);//width,height
		
		Thread buttonTask=new Thread(new ButtonTask());
		buttonTask.start();
		Thread receiveTask=new Thread(new ReceiveTask());
		receiveTask.start();
	}
	
	public void connect(){
		try{
			//create a socket to connect to the server
			socket = new Socket("127.0.0.1",8000);
			
			//Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
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
				//send
				toServer.writeInt(n);
				toServer.writeUTF(username);
				toServer.writeUTF(password);
			}
			catch(IOException ex){
				System.err.println(ex);
			}
		}
	}
	
	class ButtonTask implements Runnable {

		@Override
		public void run() {
			System.out.print("b");
			// TODO Auto-generated method stub
			jbtLogin.addActionListener(new ButtonListener());
			System.out.print("a");

		}
		
	}
	
	class ReceiveTask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
			    socket.setSoTimeout(1000);
			   
			    while(true){
					String str=null;
					str=fromServer.readUTF();
					System.out.println("ok");
					
					if(str!=null)JOptionPane.showMessageDialog(null, str);
			    }
			}
			catch (java.net.SocketTimeoutException e) {
		        // 1000 ms elapsed but nothing was read 
				this.run();
		    }
			catch (IOException ex){
			}
		}	
		
	}

}
