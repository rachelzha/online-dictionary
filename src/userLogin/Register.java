package src.userLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfUsername=new JTextField(9);
	private JTextField jtfPassword=new JTextField(9);
	private JTextField jtfEmail=new JTextField(9);
	private JButton jbtRegister=new JButton("Register");
	
	UserDB userdb=null;

	/*initialize the applet*/
	public void init(){
		//create a UserDB object
		userdb=new UserDB();
		
		jbtRegister.addActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent e){
						jbtLogin_actionPerformed(e);
					}
				});
		
		JPanel jPanel1=new JPanel();
		JPanel jPanel2=new JPanel();
		JPanel jPanel3=new JPanel();

		jPanel1.add(new JLabel("Username"));
		jPanel1.add(jtfUsername);
		jPanel2.add(new JLabel("Password"));
		jPanel2.add(jtfPassword);
		jPanel3.add(new JLabel("Email"));
		jPanel3.add(jtfEmail);
		
		setSize(300,300);//宽，高
		setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
		add(jPanel1);
		add(jPanel2);
		add(jPanel3);
		add(jbtRegister);
	}
	
	private void jbtLogin_actionPerformed(ActionEvent e){
		String username=jtfUsername.getText();
		String password=jtfPassword.getText();
		String email=jtfEmail.getText();
		
		if(username.length()==0||password.length()==0){
			JOptionPane.showMessageDialog(null, "用户名或密码不能为空！");
			return;
		}
		
		try{
			userdb.addUser(username,password,email);
			
			JOptionPane.showMessageDialog(null, "注册成功！");
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null,"用户名已被占用！");
		}
	}
}
