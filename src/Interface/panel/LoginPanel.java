package src.Interface.panel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

import src.userLogin.Login;

public class LoginPanel {
	drawComponent draw=new drawComponent();
	public JPanel MyPanel = new JPanel();
	
	JPanel Right = new JPanel();
	public JButton Login = new JButton();//login
	public JButton Logout = new JButton();//logout
	
	public JButton colorgreen = new JButton();
	public JButton coloryellow = new JButton();
	public JButton colorblue = new JButton();
	public JButton colordarkblue = new JButton();
	public JButton colorpink = new JButton();
	public JButton colorblack = new JButton();
	
	public JButton message = new JButton(); //get message
	
	public JLabel dict = new JLabel("Online Dictionary");//no edit
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public LoginPanel(){
		drawRight(); 
		dict.setFont(new java.awt.Font("Times new Roman",Font.BOLD+Font.ITALIC,20));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,100, 5);  
		MyPanel.setLayout(flowLayout);
		MyPanel.add(dict);
		MyPanel.add(Right);
	}
	
	public void drawRight(){
		String loginfile1="image/log/login1.png";
		String loginfile2="image/log/login2.png";
		String logoutfile1="image/log/logout1.png";
		String logoutfile2="image/log/logout2.png";
	//	String messagefile1="image/message/1.png";
		String messagefile2="image/message/2.png";
	//	String messagefile3="image/message/3.png";
		String messagefile4="image/message/4.png";
		
		String green="image/green/color.png";
		String yellow="image/yellow/color.png";
		String blue="image/blue/color.png";
		String darkblue="image/darkblue/color.png";
		String pink="image/pink/color.png";
		String black="image/black/color.png";
		String colorfile="image/grey/color.png";
		
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,15, 5);  
		Right.setLayout(flowLayout);
		
		draw.drawButton(loginfile1, loginfile2, 20, 20, Login);
		draw.drawButton(logoutfile1, logoutfile2, 20, 20, Logout);
		draw.drawButton(messagefile2, messagefile4, 20, 20, message);
		draw.drawButton(green,colorfile, 20, 20, colorgreen);
		draw.drawButton(yellow,colorfile, 20, 20, coloryellow);
		draw.drawButton(blue,colorfile, 20, 20, colorblue);
		draw.drawButton(darkblue,colorfile, 20, 20, colordarkblue);
		draw.drawButton(pink,colorfile, 20, 20, colorpink);
		draw.drawButton(black,colorfile, 20, 20, colorblack);
		
		
		Right.add(colorgreen);
		Right.add(coloryellow);
		Right.add(colorblue);
		Right.add(colordarkblue);
		Right.add(colorpink);
		Right.add(colorblack);
		Right.add(Login);
	//	Right.add(message);
	//	Right.add(Logout);
		
	}
	
	public void setColor(String color){
		Color bg=null;
		switch(color){
		case "green":bg=Color.green;break;
		case "yellow":bg=Color.yellow;break;
		case "blue":bg=new Color(135,206,235,255);break;
		case "darkblue":bg=new Color(0,0,139,255);break;
		case "pink":bg=new Color(218,112,214,255);break;
		case "black":bg=Color.darkGray;break;
		}
		Right.setBackground(bg);
		MyPanel.setBackground(bg);
		MyPanel.revalidate();
		MyPanel.repaint();
	}
}
