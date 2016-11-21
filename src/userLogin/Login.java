package src.userLogin;

import javax.swing.*;

import Server.UserDB;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Login extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfUsername=new JTextField(9);
	private JTextField jtfPassword=new JTextField(9);
	private JButton jbtLogin=new JButton("Log in");
	
	//io streams
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	public Login(Socket socket){
		JPanel jPanel1=new JPanel();
		JPanel jPanel2=new JPanel();

		jPanel1.add(new JLabel("Username"));
		jPanel1.add(jtfUsername);
		jPanel2.add(new JLabel("Password"));
		jPanel2.add(jtfPassword);
		
		setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
		add(jPanel1);
		add(jPanel2);
		add(jbtLogin);
		
		setTitle("Login");
		setSize(300,250);//width,height
		setVisible(true);
		
		jbtLogin.addActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent e){
						jbtLogin_actionPerformed(e);
					}
				});	
		
		try{
			//Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex){
			System.err.println(ex);
		}
	}
	
	private void jbtLogin_actionPerformed(ActionEvent e){
		String username=jtfUsername.getText();
		String password=jtfPassword.getText();
		
		try{
			//send
			toServer.writeInt(1);
			toServer.writeUTF(username);
			toServer.writeUTF(password);
			
			//receive
			boolean found=fromServer.readBoolean();
			
			if(found==true){
				JOptionPane.showMessageDialog(null, "Hello! "+ username);
			}
			else {
				JOptionPane.showMessageDialog(null, "Username or Password WRONG!");
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	}

}
