package src.Interface.listener;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

import src.Interface.Testwindow;
import src.Interface.share.RecievePicture;
import src.Interface.share.SendPicture;
import src.Translate.BingTranslate;
import src.Translate.JinshanTranslate;
import src.Translate.Translation;
import src.Translate.YoudaoTranslate;
import src.userLogin.Login;

public class ButtonListener implements ActionListener{

	int type;
	Login login;
	
	//private Lock lock=new ReentrantLock();
	
	public ButtonListener(int type){
		this.type=type;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (type){
		case 1:handleSearch();break;//search button
		case 2:handleSearch();break;//prev button
		case 3:handleSearch();break;//next button
		case 5:handleLogin();break;//login button
		case 7:handleMessage();break;//message button
		case 8:handleShare();break;//share button
		case 9:handleLogout();break;//logout
		case 10:case 11:case 12:case 13:case 14:case 15:handleColor();break;
		}
	}

	public void handleSearch(){
		String key=null;
		if(type==2){
			Vector<String>H=Testwindow.history.Read();
			Testwindow.history.prevpointer();
			key = H.elementAt(Testwindow.history.getpointer());
			Testwindow.searchpanel.input.setText(key);
		}
		if(type==3){
			Vector<String>H=Testwindow.history.Read();
			Testwindow.history.prevpointer();
			key = H.elementAt(Testwindow.history.getpointer());
			Testwindow.searchpanel.input.setText(key);
		}
		if(type==1)
			key=Testwindow.searchpanel.input.getText();
		//	String key = searchpanel.input.getSelectedItem().toString();
		
		Testwindow.textpanel.tranword.setText(key);
		
	//	lock.lock();
		try{
			//send
			Testwindow.dataToServer.writeInt(3);
			Testwindow.dataToServer.writeUTF(key);
			Testwindow.dataToServer.flush();
			
			Testwindow.info.setbinglikes(Testwindow.dataFromServer.readInt());
			Testwindow.info.setyoudaolikes(Testwindow.dataFromServer.readInt());
			Testwindow.info.setjinshanlikes(Testwindow.dataFromServer.readInt());
			
			if(Testwindow.user.Logged()){
				Testwindow.info.setjudgebing(Testwindow.dataFromServer.readInt());
				Testwindow.info.setjudgeyoudao(Testwindow.dataFromServer.readInt());
				Testwindow.info.setjudgejinshan(Testwindow.dataFromServer.readInt());
				//System.out.println(judgebing+":"+judgeyoudao+":"+judgejinshan);
			}
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	//	finally{
	//		lock.unlock();
	//	}
		
		CheckBoxListener.resetBookMark();//sort
		
		if(Testwindow.textpanel.bing.isSelected()){
			BingTranslate B = new BingTranslate();
			Translation trans = B.Translation(key);
			trans.print(Testwindow.textpanel.Out);
		}
		if(Testwindow.textpanel.youdao.isSelected()){//////////////////
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation trans = Y.Translation(key);
			trans.print(Testwindow.textpanel.Out);
		}
		if(Testwindow.textpanel.jinshan.isSelected()){//////////////////
			JinshanTranslate J = new JinshanTranslate();
			Translation trans = J.Translate(key);
			trans.print(Testwindow.textpanel.Out);
		}	
		
		if(type==1){
			Vector<String>H=Testwindow.history.Read();
			H.addElement(key);
			Testwindow.history.Write(H);
		}
	}
	
	public void handleLogin(){///unhandle??????????
		if(!Testwindow.user.Logged()){	
			login = new Login(Testwindow.socket);		
			login.setLocation(200,100);	
			login.setVisible(true);
		}
		login.addWindowListener(new WindowAdapter(){
			public void windowClosing(java.awt.event.WindowEvent e){
				super.windowClosing(e);
				System.out.println("closed");
				//user=login.getUser();
				if(Testwindow.user.Logged()){	
					Testwindow.loginpanel.Right.add(Testwindow.loginpanel.message);
					Testwindow.loginpanel.Right.add(Testwindow.loginpanel.Logout);
					Testwindow.loginpanel.Right.revalidate();
					Testwindow.loginpanel.Right.repaint();
					Testwindow.textpanel.Above.add(Testwindow.textpanel.like);
					Testwindow.textpanel.Above.add(Testwindow.textpanel.share);
					Testwindow.textpanel.Above.revalidate();
					Testwindow.textpanel.Above.repaint();
					
				}
			}
		});
	
	}
	
	
	public void handleMessage(){
		
		//Vector<Message>messages=(Vector<Message>)obj[1];
		new RecievePicture();
		
		String messagefile="image/message/2.png";
		ImageIcon icon = new ImageIcon(messagefile);  
        icon.setImage(icon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        Testwindow.loginpanel.message.setIcon(icon);
        Testwindow.loginpanel.Right.revalidate();
        Testwindow.loginpanel.Right.repaint();
	}
	
	public void handleShare(){
//		lock.lock();
			try{
				//send
				Testwindow.dataToServer.writeInt(5);
				Testwindow.dataToServer.flush();
				//get onlineusers
				Vector<String> onlineUsers=(Vector<String>)Testwindow.objectFromServer.readObject();
				//	textpanel.Out.append("The online Clients: \n");
				Testwindow.info.setonlineuserlist(onlineUsers);	
					
				//send
				Testwindow.dataToServer.writeInt(6);
				Testwindow.dataToServer.flush();
				//get allusers
				Vector<String> allUsers=(Vector<String>)Testwindow.objectFromServer.readObject();
				Testwindow.info.setuserlist(allUsers);
				
			}
			catch(IOException ex){
				System.err.println(ex);
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	finally{
		//		lock.unlock();
		//	}

			
		String key = Testwindow.searchpanel.input.getText();
	//	for(int i=0;i<onlineuserlist.length;i++)
	//		System.out.println(onlineuserlist[i])
		if(Testwindow.textpanel.bing.isSelected()){
			BingTranslate B = new BingTranslate();
			Translation t = B.Translation(key);
			new SendPicture(t);
		}
		if(Testwindow.textpanel.youdao.isSelected()){
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation t = Y.Translation(key);
			new SendPicture(t);
		//	textpanel.Out.setText(text);
		}
		if(Testwindow.textpanel.jinshan.isSelected()){
			JinshanTranslate J = new JinshanTranslate();
			Translation t = J.Translate(key);
			new SendPicture(t);
		//	textpanel.Out.setText(text);
		}	
	}
	
	public void handleLogout(){
		
		//logout.......
//		lock.lock();
			try{
				//send
				Testwindow.dataToServer.writeInt(7);
				Testwindow.dataToServer.flush();
			}
			catch(IOException ex){
				System.err.println(ex);
			}
		//	finally{
		//		lock.unlock();
		//	}
		
		Testwindow.loginpanel.Right.remove(Testwindow.loginpanel.message);
		Testwindow.loginpanel.Right.remove(Testwindow.loginpanel.Logout);
		Testwindow.loginpanel.Right.revalidate();
		Testwindow.loginpanel.Right.repaint();
		Testwindow.textpanel.Above.remove(Testwindow.textpanel.like);
		Testwindow.textpanel.Above.remove(Testwindow.textpanel.share);
		Testwindow.textpanel.Above.revalidate();
		Testwindow.textpanel.Above.repaint();
	}
	
	public void handleColor(){
		String color="";
		switch(type){
		case 10:color="green";break;
		case 11:color="yellow";break;
		case 12:color="blue";break;
		case 13:color="darkblue";break;
		case 14:color="pink";break;
		case 15:color="black";break;
		}
		
		Testwindow.loginpanel.setColor(color);
		Testwindow.searchpanel.setColor(color);
		Testwindow.choosepanel.setColor(color);
		Testwindow.textpanel.setColor(color);
	}
	
	
}
