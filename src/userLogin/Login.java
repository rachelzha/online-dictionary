package src.userLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JApplet{
	private JTextField jtfUsername=new JTextField(9);
	private JTextField jtfPassword=new JTextField(9);
	private JButton jbtLogin=new JButton("Log in");
	
	UserDB userdb=null;

	/*initialize the applet*/
	public void init(){
		//create a UserDB object
		userdb=new UserDB();
		
		jbtLogin.addActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent e){
						jbtLogin_actionPerformed(e);
					}
				});
		
		JPanel jPanel1=new JPanel();
		JPanel jPanel2=new JPanel();

		jPanel1.add(new JLabel("Username"));
		jPanel1.add(jtfUsername);
		jPanel2.add(new JLabel("Password"));
		jPanel2.add(jtfPassword);
		
		setSize(300,250);//瀹斤紝楂�
		setLayout(new FlowLayout(FlowLayout.CENTER,80,20));
		add(jPanel1);
		add(jPanel2);
		add(jbtLogin);
	}
	
	private void jbtLogin_actionPerformed(ActionEvent e){
		String username=jtfUsername.getText();
		String password=jtfPassword.getText();
		
		boolean found=userdb.findUser(username, password);
		
		if(found==true){
			JOptionPane.showMessageDialog(null, "Hello! "+ username);
		}
		else {
			JOptionPane.showMessageDialog(null, "Username or Password WRONG!");
		}
	}
}
