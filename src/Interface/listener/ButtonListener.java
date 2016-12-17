package src.Interface.listener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;

import src.Interface.MainWindow;
import src.Interface.share.ReceivePicture;
import src.Interface.share.SendPicture;
import src.Interface.tool.drawComponent;
import src.Interface.userLogin.Login;
import src.Translate.BingTranslate;
import src.Translate.JinshanTranslate;
import src.Translate.Translation;
import src.Translate.YoudaoTranslate;

public class ButtonListener implements ActionListener{

	int type;//处理类型
	Login login;
	
	private Lock lock=new ReentrantLock();
	
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
		case 10:case 11:case 12:case 13:case 14:case 15:handleColor();break; //change color
		case 4:handleLike();break;//like checkbox
		}
	}

	//like checkbox in textpanel
	public void handleLike(){
		//getword
		String key=MainWindow.searchpanel.input.getText();
		
		try{
			lock.lock();
			//send
			MainWindow.toServer.writeObject(8);
			MainWindow.toServer.writeObject(key);
			
			if(MainWindow.textpanel.bing.isSelected()){
				MainWindow.toServer.writeObject("baidu");
			}
			else if(MainWindow.textpanel.youdao.isSelected()){
				MainWindow.toServer.writeObject("youdao");
			}
			else if(MainWindow.textpanel.jinshan.isSelected()){
				MainWindow.toServer.writeObject("jinshan");
			}
			lock.unlock();
			
			//refresh
			MainWindow.textpanel.Center.revalidate();
			MainWindow.textpanel.Center.repaint();
		}
		catch (IOException ex){
			lock.unlock();
			System.err.println(ex);
			System.err.println("Fail!");
		}

	}
	
	//search button in searchpanel
	public void handleSearch(){
		String key=null;
		if(type==2){//get prev word
			Vector<String>H=MainWindow.history.Read();
			MainWindow.history.prevpointer();
			key = H.elementAt(MainWindow.history.getpointer());
			MainWindow.searchpanel.input.setText(key);
		}
		if(type==3){//get next word
			Vector<String>H=MainWindow.history.Read();
			MainWindow.history.nextpointer();
			key = H.elementAt(MainWindow.history.getpointer());
			MainWindow.searchpanel.input.setText(key);
		}

		String temp=MainWindow.searchpanel.input.getText();
		key=deleteExtraSpace(temp);
		if(key.length()==0)return;
		
		//判断输入是否为中文，如果含有中文，取消bing翻译
		for(int i =0 ;i < key.length() ; i ++){
			if(key.substring(i, i+1).matches("[\\u4e00-\\u9fa5]+"))
				MainWindow.choosepanel.bing.setSelected(false);
		}
		
		MainWindow.textpanel.tranword.setText(key);
		
		try{
			//send
			lock.lock();
			MainWindow.toServer.writeObject((int)3);
			MainWindow.toServer.writeObject(key);
			
			//receive
			MainWindow.info.setbinglikes((int)MainWindow.fromServer.readObject());
			MainWindow.info.setyoudaolikes((int)MainWindow.fromServer.readObject());
			MainWindow.info.setjinshanlikes((int)MainWindow.fromServer.readObject());
			
			if(MainWindow.user.Logged()){
				MainWindow.info.setjudgebing((int)MainWindow.fromServer.readObject());
				MainWindow.info.setjudgeyoudao((int)MainWindow.fromServer.readObject());
				MainWindow.info.setjudgejinshan((int)MainWindow.fromServer.readObject());
			}
			lock.unlock();
		}
		catch(IOException ex){
			lock.unlock();
			System.err.println(ex);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CheckBoxListener.resetBookMark();//sort bookmark by likes
		
		if(MainWindow.textpanel.bing.isSelected()){
			//show whether like
			if(MainWindow.user.Logged()){
				if(MainWindow.info.getjudgebing()==1)
					MainWindow.textpanel.like.setSelected(true);
				else if(MainWindow.info.getjudgebing()==-1)
					MainWindow.textpanel.like.setSelected(false);
			}
			//show translation
			BingTranslate B = new BingTranslate();
			Translation trans = B.Translation(key);
			trans.print(MainWindow.textpanel.Out);
		}
		if(MainWindow.textpanel.youdao.isSelected()){
			//show whether like
			if(MainWindow.user.Logged()){
				if(MainWindow.info.getjudgeyoudao()==1)
					MainWindow.textpanel.like.setSelected(true);
				else if(MainWindow.info.getjudgeyoudao()==-1)
					MainWindow.textpanel.like.setSelected(false);
			}
			//show translation
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation trans = Y.Translate(key);
			trans.print(MainWindow.textpanel.Out);
		}
		if(MainWindow.textpanel.jinshan.isSelected()){
			//show whether like
			if(MainWindow.user.Logged()){
				if(MainWindow.info.getjudgejinshan()==1)
					MainWindow.textpanel.like.setSelected(true);
				else if(MainWindow.info.getjudgejinshan()==-1)
					MainWindow.textpanel.like.setSelected(false);
			}
			//show translation
			JinshanTranslate J = new JinshanTranslate();
			Translation trans = J.Translate(key);
			trans.print(MainWindow.textpanel.Out);
		}	
		
		if(type==1){//add history word
			Vector<String>H=MainWindow.history.Read();
			H.addElement(key);
			MainWindow.history.Write(H);
		}
	}
	
	//login button in loginpanel
	public void handleLogin(){
		//logged
		if(MainWindow.user.Logged()){
			String S = "Hello,"+MainWindow.user.getUsername();
			JOptionPane.showMessageDialog(null, S);
		}
		//unlogin
		if(!MainWindow.user.Logged()){	
			login = new Login();		
			login.setLocation(200,100);	
			login.setVisible(true);
		}
		
		//refresh mainwindow
		login.addWindowListener(new WindowAdapter(){
			public void windowClosing(java.awt.event.WindowEvent e){
				super.windowClosing(e);
				System.out.println("closed");
				//user=login.getUser();
				if(MainWindow.user.Logged()){	
					MainWindow.loginpanel.Right.add(MainWindow.loginpanel.message);
					MainWindow.loginpanel.Right.add(MainWindow.loginpanel.Logout);
					MainWindow.loginpanel.Right.revalidate();
					MainWindow.loginpanel.Right.repaint();
					MainWindow.textpanel.Above.add(MainWindow.textpanel.like);
					MainWindow.textpanel.Above.add(MainWindow.textpanel.share);
					MainWindow.textpanel.Above.revalidate();
					MainWindow.textpanel.Above.repaint();
					
				}
			}
		});
	
	}
	
	//message button in textpanel
	public void handleMessage(){
		//open receivepicture window
		new ReceivePicture();
		
		//refresh message button
		String messagefile1="image/message/1.png";
		String messagefile2="image/message/4.png";
		drawComponent.drawButton(messagefile1, messagefile2, 20, 20,  MainWindow.loginpanel.message);
        MainWindow.loginpanel.Right.revalidate();
        MainWindow.loginpanel.Right.repaint();
	}
	
	//share button in textpanel
	public void handleShare(){
		try{
			lock.lock();
			//send 
			MainWindow.toServer.writeObject(5);
			//get onlineusers
			Vector<String> onlineUsers=(Vector<String>)MainWindow.fromServer.readObject();
			lock.unlock();
			MainWindow.info.setonlineuserlist(onlineUsers);	
						
			//send
			lock.lock();
			MainWindow.toServer.writeObject(6);
			//get allusers
			Vector<String> allUsers=(Vector<String>)MainWindow.fromServer.readObject();
			lock.unlock();
			MainWindow.info.setuserlist(allUsers);
					
		}
		catch(IOException ex){
			lock.unlock();
			System.err.println(ex);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			lock.unlock();
			e.printStackTrace();
		}
			
		String temp = MainWindow.searchpanel.input.getText();
		String key=deleteExtraSpace(temp);
		if(key.length()==0)return;
		
		//open sendpicture window
		if(MainWindow.textpanel.bing.isSelected()){
			BingTranslate B = new BingTranslate();
			Translation t = B.Translation(key);
			new SendPicture(t);
		}
		if(MainWindow.textpanel.youdao.isSelected()){
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation t = Y.Translate(key);
			new SendPicture(t);
		}
		if(MainWindow.textpanel.jinshan.isSelected()){
			JinshanTranslate J = new JinshanTranslate();
			Translation t = J.Translate(key);
			new SendPicture(t);
		}	
	}
	
	public void handleLogout(){
		try{
			//send
			MainWindow.toServer.writeObject(7);
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		
		//init info and refresh mainwindow
		MainWindow.user.setUsername(null);
		MainWindow.info.init();
		MainWindow.loginpanel.Right.remove(MainWindow.loginpanel.message);
		MainWindow.loginpanel.Right.remove(MainWindow.loginpanel.Logout);
		MainWindow.loginpanel.Right.revalidate();
		MainWindow.loginpanel.Right.repaint();
		MainWindow.textpanel.Above.remove(MainWindow.textpanel.like);
		MainWindow.textpanel.Above.remove(MainWindow.textpanel.share);
		MainWindow.textpanel.Above.revalidate();
		MainWindow.textpanel.Above.repaint();
	}
	
	//color button in loginpanel
	public void handleColor(){
		//get color
		String color="";
		switch(type){
		case 10:color="green";break;
		case 11:color="yellow";break;
		case 12:color="blue";break;
		case 13:color="darkblue";break;
		case 14:color="pink";break;
		case 15:color="black";break;
		}
		//setcolor
		MainWindow.loginpanel.setColor(color);
		MainWindow.searchpanel.setColor(color);
		MainWindow.choosepanel.setColor(color);
		MainWindow.textpanel.setColor(color);
	}
	
	//删除单词多余的空格
	public static String deleteExtraSpace(String word){
		if(word==null||word.length()==0)return "";
			
		String[] subWords=word.split("\\s+");
		if(subWords.length==0)return "";
			
		word=subWords[0];
		for(int i=1;i<subWords.length;i++){
			word=word+" "+subWords[i];
		}
			
		return word;
	}
}
