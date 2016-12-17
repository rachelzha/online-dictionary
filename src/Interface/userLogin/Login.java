package src.Interface.userLogin;

import javax.swing.*;

import src.Interface.MainWindow;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Login extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int type;
	JPanel jPanel1=new JPanel();
	JPanel jPanel2=new JPanel();
	JPanel jPanel3=new JPanel();
	JPanel jPanel4=new JPanel();
	
	private JButton jbtLogin=new JButton("Log in");
	private JButton Register= new JButton("Sign in");
	private JTextField jtfUsername=new JTextField(9);
	private JPasswordField jtfPassword=new JPasswordField(9);
	
	private JLabel exch = new JLabel("confirm");
	private JPasswordField security = new JPasswordField(9);
	
	private JButton confirm  = new JButton("OK");
	//io streams

	
	private Lock lock=new ReentrantLock();
	
	public Login(){
	
		type=2;
		
		jPanel1.add(jbtLogin);
		jPanel1.add(Register);
		jPanel2.add(new JLabel("username"));
		jPanel2.add(jtfUsername);
		jPanel3.add(new JLabel("password"));
		jPanel3.add(jtfPassword);
		jPanel4.add(exch);
		jPanel4.add(security);
		
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER,80,20);
		setLayout(flow);
		add(jPanel1);
		add(jPanel2);
		add(jPanel3);
	//	add(jPanel4);
		add(confirm);
		
		setTitle("Login");
		setSize(350,300);//width,height
		
		jbtLogin.addActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent e){
					//	remove(confirm);
						remove(jPanel4);
					//	add(confirm);
						revalidate();
						repaint();
						type=2;
					}
				});	
		
		Register.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				remove(confirm);
				add(jPanel4);
				add(confirm);
				revalidate();
				repaint();
				type=1;
			}
		});	
		
		confirm.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(type==1)	
					Register_actionPerformed(e);
				else if(type==2)
					jbtLogin_actionPerformed(e);
			}
		});	
		
	}
	
	private void Register_actionPerformed(ActionEvent e){
		
		String username=jtfUsername.getText();
		String password=String.valueOf(jtfPassword.getPassword());		
		String sec=String.valueOf(security.getPassword());	
	//	String email=null;//jtfEmail.getText();
		
		if(username.length()==0||password.length()==0){
			JOptionPane.showMessageDialog(null, "用户名或密码不能为空！");
			return;
		}
		if(!password.equals(sec)){
			JOptionPane.showMessageDialog(null, "请重新输入密码！");
			jtfPassword.setText("");
			security.setText("");
			return;
		}
		

		try{
			//send
			lock.lock();
			MainWindow.toServer.writeObject((int)2);
			MainWindow.toServer.writeObject(username);
			MainWindow.toServer.writeObject(password);
		//	Testwindow.toServer.writeObject(email);
			
			//receive
			String name=(String)MainWindow.fromServer.readObject();
			lock.unlock();
			
			if(name!=null&&name.length()!=0){
				MainWindow.user.setUsername(username);
				JOptionPane.showMessageDialog(null, "注册成功！");
				
				//refresh mainwindow
				MainWindow.loginpanel.Right.add(MainWindow.loginpanel.message);
				MainWindow.loginpanel.Right.add(MainWindow.loginpanel.Logout);
				MainWindow.loginpanel.Right.revalidate();
				MainWindow.loginpanel.Right.repaint();
				MainWindow.textpanel.Above.add(MainWindow.textpanel.like);
				MainWindow.textpanel.Above.add(MainWindow.textpanel.share);
				MainWindow.textpanel.Above.revalidate();
				MainWindow.textpanel.Above.repaint();

				dispose();///close Register window
			}
			else {
				JOptionPane.showMessageDialog(null,"用户名已被占用！");
			}
		}
		catch(IOException ex){
			lock.unlock();
			System.err.println(ex);
		}
	//	finally{
		//	lock.unlock();
	//	}
 catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void jbtLogin_actionPerformed(ActionEvent e){
		
		String username=jtfUsername.getText();
		String password=String.valueOf(jtfPassword.getPassword());		
		
		try{
			//send
			lock.lock();
			MainWindow.toServer.writeObject((int)1);
			MainWindow.toServer.writeObject(username);
			MainWindow.toServer.writeObject(password);
			
			//receive
			String name=(String)MainWindow.fromServer.readObject();
			lock.unlock();
			
			if(name!=null&&name.length()!=0){
				MainWindow.user.setUsername(username);
				JOptionPane.showMessageDialog(null, "登陆成功！");
				
				//refresh mainwindow
				MainWindow.loginpanel.Right.add(MainWindow.loginpanel.message);
				MainWindow.loginpanel.Right.add(MainWindow.loginpanel.Logout);
				MainWindow.loginpanel.Right.revalidate();
				MainWindow.loginpanel.Right.repaint();
				MainWindow.textpanel.Above.add(MainWindow.textpanel.like);
				MainWindow.textpanel.Above.add(MainWindow.textpanel.share);
				MainWindow.textpanel.Above.revalidate();
				MainWindow.textpanel.Above.repaint();

				dispose();///close login window
			}
			else {
				JOptionPane.showMessageDialog(null,"请重新登陆！");
			}
		}
		catch(IOException ex){
			lock.unlock();
			System.err.println(ex);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			lock.unlock();
			e1.printStackTrace();
		}
	}

}