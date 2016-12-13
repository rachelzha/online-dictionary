package src.Interface;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;

import Server.Picture;
import src.Translate.BaiduTranslate;
import src.Translate.JinshanTranslate;
import src.Translate.YoudaoTranslate;
import src.userLogin.Login;
import src.userLogin.UserState;

public class ButtonListener implements ActionListener{
	Object []obj;
	private int type;
	private UserState user=new UserState();
	private Socket socket;
	private DataOutputStream toServer;
	Login login;
	
	private Lock lock=new ReentrantLock();

	
	public ButtonListener(int type,UserState user, Socket soct,Object []obj){
		this.obj=obj;
		this.type=type;
		this.user=user;
		this.socket=soct;
		try{
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (type){
		case 1:handleSearch();break;//search button
		case 2:handlePrev();break;//prev button
		case 3:handleNext();break;//next button
		case 5:handleLogin();break;//login button
		case 6:handleIndividuation();break;//individuation button
		case 7:handleMessage();break;//message button
		case 8:handleShare();break;//share button
		}
	}

	public void handleSearch(){
		SearchPanel searchpanel=(SearchPanel)obj[0];
		TextPanel textpanel=(TextPanel)obj[1];
	//	String key = searchpanel.input.getSelectedItem().toString();
		String key=searchpanel.input.getText();
		
		lock.lock();
		try{
			//send
			toServer.writeInt(3);
			
			toServer.writeUTF(key);		
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		finally{
			lock.unlock();
		}
		
		if(textpanel.baidu.isSelected()){
			BaiduTranslate B = new BaiduTranslate();
			String text = B.Translation(key);
			textpanel.Out.setText(text);
		}
		if(textpanel.youdao.isSelected()){
			YoudaoTranslate Y = new YoudaoTranslate();
			String text = Y.Translation(key);
			textpanel.Out.setText(text);
		}
		if(textpanel.jinshan.isSelected()){
			JinshanTranslate J = new JinshanTranslate();
			String text = J.Translate(key);
			textpanel.Out.setText(text);
		}	
	}
	
	public void handlePrev(){
		
	}
	
	public void handleNext(){
		
	}
	
	public void handleLogin(){
		TextPanel textpanel = (TextPanel)obj[0];
		if(!user.Logged()){	
			login = new Login(socket);		
			login.setLocation(200,100);	
			login.setVisible(true);
		}
		login.addWindowListener(new WindowAdapter(){
			public void windowClosing(java.awt.event.WindowEvent e){
				super.windowClosing(e);
				System.out.println("closed");
				//user=login.getUser();
				if(user.Logged()){	
					textpanel.Above.add(textpanel.like);
					textpanel.Above.add(textpanel.share);
					textpanel.Above.revalidate();
					textpanel.Above.repaint();
				}
			}
		});
	}
	
	public void handleIndividuation(){
		
	}
	
	public void handleMessage(){
		
	}
	
	public void handleShare(){
		SearchPanel searchpanel = (SearchPanel)obj[0];
		TextPanel textpanel = (TextPanel)obj[1];
		String key = searchpanel.input.getText();
		if(textpanel.youdao.isSelected()){
			YoudaoTranslate Y = new YoudaoTranslate();
			String text = Y.Translation(key);
			Picture pic=new Picture(text,socket);
		//	textpanel.Out.setText(text);
		}
		if(textpanel.jinshan.isSelected()){
			JinshanTranslate J = new JinshanTranslate();
			String text = J.Translate(key);
			Picture pic=new Picture(text,socket);
		//	textpanel.Out.setText(text);
		}	}
}
