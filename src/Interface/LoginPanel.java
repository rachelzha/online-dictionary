package src.Interface;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

import src.userLogin.Login;

public class LoginPanel {
	drawComponent draw=new drawComponent();
	public JPanel MyPanel = new JPanel();
	
	JPanel Right = new JPanel();
	public JButton Login = new JButton();//login
	public JButton individuation = new JButton(); //choose background color
	public JButton message = new JButton(); //get message
	
	public JList<String> loginlist = new JList<String>();//click button Login, appear login and register options
	
	public JLabel dict = new JLabel("Online Dictionary");//no edit
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public LoginPanel(){
		drawRight("blue"); 
		dict.setFont(new java.awt.Font("Times new Roman",Font.BOLD+Font.ITALIC,20));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING ,200, 5);  
		MyPanel.setLayout(flowLayout);
		MyPanel.add(dict);
		MyPanel.add(Right);
	}
	
	public void drawRight(String color){
		String loginfile1="image/"+color+"/login.png";
		String loginfile2="image/grey/login.png";
		String indvfile1="image/"+color+"/color.png";
		String indvfile2="image/grey/color.png";
		
		//message???
		
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,15, 5);  
		Right.setLayout(flowLayout);
		
		draw.drawButton(loginfile1, loginfile2, 20, 20, Login);
        draw.drawButton(indvfile1, indvfile2, 20, 20, individuation);
		Right.add(individuation);
		Right.add(Login);
		
	}
}
