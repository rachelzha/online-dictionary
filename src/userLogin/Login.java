package src.userLogin;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Login extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfUsername=new JTextField(9);
	private JTextField jtfPassword=new JTextField(9);
	private JButton jbtLogin=new JButton("Log in");
	private JButton Register= new JButton("Register");
	//io streams
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	private Lock lock=new ReentrantLock();

	public Login(Socket socket){
		JPanel jPanel1=new JPanel();
		JPanel jPanel2=new JPanel();
		JPanel jPanel3=new JPanel();
		
		jPanel1.add(new JLabel("Username"));
		jPanel1.add(jtfUsername);
		jPanel2.add(new JLabel("Password"));
		jPanel2.add(jtfPassword);
		jPanel3.add(jbtLogin);
		jPanel3.add(Register);
		
		setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
		add(jPanel1);
		add(jPanel2);
		add(jPanel3);
		
		setTitle("Login");
		setSize(300,250);//width,height
		
		jbtLogin.addActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent e){
						jbtLogin_actionPerformed(e);
					}
				});	
		
		Register.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				Register_actionPerformed(e);
				//jbtLogin_actionPerformed(e);
			}
		});	
		
		try{
			//Create an input stream to receive data from the server
		//	fromServer = new DataInputStream(socket.getInputStream());
			
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex){
			System.err.println(ex);
		}
		
	}
	
	private void Register_actionPerformed(ActionEvent e){
		String username=jtfUsername.getText();
		String password=jtfPassword.getText();
	//	String email=jtfEmail.getText();
		
		if(username.length()==0||password.length()==0){
			JOptionPane.showMessageDialog(null, "用户名或密码不能为空！");
			return;
		}
		
		lock.lock();
		try{
			//send
			toServer.writeInt(2);
			toServer.writeUTF(username);
			toServer.writeUTF(password);
		//	toServer.writeUTF(email);
			
			//receive
		/*	boolean success=fromServer.readBoolean();
			
			if(success==true){
				JOptionPane.showMessageDialog(null, "注册成功！");
			//	user.setUsername("username");
			}
			else {
				JOptionPane.showMessageDialog(null,"用户名已被占用！");
			}*/
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		finally{
			lock.unlock();
		}
	}
	
	private void jbtLogin_actionPerformed(ActionEvent e){
		String username=jtfUsername.getText();
		String password=jtfPassword.getText();
		
		lock.lock();
		try{
			//send
			toServer.writeInt(1);
			toServer.writeUTF(username);
			toServer.writeUTF(password);
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		finally{
			lock.unlock();
		}
	}

}
