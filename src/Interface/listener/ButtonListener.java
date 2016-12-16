package src.Interface.listener;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.*;

import src.Interface.Testwindow;
import src.Interface.share.ReceivePicture;
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
		case 4:handleLike();break;
		}
	}

	public void handleLike(){
		String key=Testwindow.searchpanel.input.getText();
		
		try{
			//create an output stream to send data to the server
			//ObjectOutputStream toServer=new ObjectOutputStream(Testwindow.socket.getOutputStream());

			Testwindow.toServer.writeObject(8);
			Testwindow.toServer.writeObject(key);
			if(Testwindow.textpanel.bing.isSelected()){
				Testwindow.toServer.writeObject("baidu");
			}
			else if(Testwindow.textpanel.youdao.isSelected()){
				Testwindow.toServer.writeObject("youdao");
			}
			else if(Testwindow.textpanel.jinshan.isSelected()){
				Testwindow.toServer.writeObject("jinshan");
			}
			
			
			Testwindow.textpanel.Center.revalidate();
			Testwindow.textpanel.Center.repaint();
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
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
			//ObjectOutputStream toServer=new ObjectOutputStream(Testwindow.socket.getOutputStream());
			Testwindow.toServer.writeObject(3);
			Testwindow.toServer.writeObject(key);
			//Testwindow.dataToServer.flush();
			
			//ObjectInputStream fromServer=new ObjectInputStream(Testwindow.socket.getInputStream());
			Testwindow.info.setbinglikes((int)Testwindow.fromServer.readObject());
			Testwindow.info.setyoudaolikes((int)Testwindow.fromServer.readObject());
			Testwindow.info.setjinshanlikes((int)Testwindow.fromServer.readObject());
			
			if(Testwindow.user.Logged()){
				Testwindow.info.setjudgebing((int)Testwindow.fromServer.readObject());
				Testwindow.info.setjudgeyoudao((int)Testwindow.fromServer.readObject());
				Testwindow.info.setjudgejinshan((int)Testwindow.fromServer.readObject());
				//System.out.println(judgebing+":"+judgeyoudao+":"+judgejinshan);
			}
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
	//	finally{
	//		lock.unlock();
	//	}
 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CheckBoxListener.resetBookMark();//sort
		
		if(Testwindow.textpanel.bing.isSelected()){
			if(Testwindow.user.Logged()){
				if(Testwindow.info.getjudgebing()==1)
					Testwindow.textpanel.like.setSelected(true);
				else if(Testwindow.info.getjudgebing()==-1)
					Testwindow.textpanel.like.setSelected(false);
			}
			BingTranslate B = new BingTranslate();
			Translation trans = B.Translation(key);
			trans.print(Testwindow.textpanel.Out);
		}
		if(Testwindow.textpanel.youdao.isSelected()){//////////////////
			if(Testwindow.user.Logged()){
				if(Testwindow.info.getjudgeyoudao()==1)
					Testwindow.textpanel.like.setSelected(true);
				else if(Testwindow.info.getjudgeyoudao()==-1)
					Testwindow.textpanel.like.setSelected(false);
			}

			YoudaoTranslate Y = new YoudaoTranslate();
			Translation trans = Y.Translation(key);
			trans.print(Testwindow.textpanel.Out);
		}
		if(Testwindow.textpanel.jinshan.isSelected()){//////////////////
			if(Testwindow.user.Logged()){
				if(Testwindow.info.getjudgejinshan()==1)
					Testwindow.textpanel.like.setSelected(true);
				else if(Testwindow.info.getjudgejinshan()==-1)
					Testwindow.textpanel.like.setSelected(false);
			}

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
		new ReceivePicture();
		
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
				//ObjectOutputStream toServer=new ObjectOutputStream(Testwindow.socket.getOutputStream());

				Testwindow.toServer.writeObject(5);
				//Testwindow.dataToServer.flush();
				//get onlineusers
				//ObjectInputStream fromServer=new ObjectInputStream(Testwindow.socket.getInputStream());
				Vector<String> onlineUsers=(Vector<String>)Testwindow.fromServer.readObject();
				//	textpanel.Out.append("The online Clients: \n");
				Testwindow.info.setonlineuserlist(onlineUsers);	
					
				//send
				Testwindow.toServer.writeObject(6);
				//Testwindow.dataToServer.flush();
				//get allusers
				Vector<String> allUsers=(Vector<String>)Testwindow.fromServer.readObject();
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
				//ObjectOutputStream toServer=new ObjectOutputStream(Testwindow.socket.getOutputStream());

				Testwindow.toServer.writeObject(7);
				//Testwindow.dataToServer.flush();
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
