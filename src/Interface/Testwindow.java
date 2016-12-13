package src.Interface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import Server.Card;
import src.Interface.listener.ButtonListener;
import src.Interface.listener.CheckBoxListener;
import src.Interface.panel.ChoosePanel;
import src.Interface.panel.LoginPanel;
import src.Interface.panel.SearchPanel;
import src.Interface.panel.TextPanel;
import src.Translate.BaiduTranslate;
import src.userLogin.UserState;


public class Testwindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UserState user=new UserState();
	public Socket socket=null;
	
	Vector<Card>cards=new Vector<Card>();
	
	int binglike;
	int youdaolike;
	int jinshanlike;
	
	LoginPanel loginpanel = new LoginPanel();
	SearchPanel searchpanel = new SearchPanel();
	ChoosePanel choosepanel = new ChoosePanel();
	TextPanel textpanel = new TextPanel();
	Object obj1[]={searchpanel,textpanel};
    Object obj2[]={textpanel};
    Object obj3[]={loginpanel,searchpanel,choosepanel,textpanel};
    Object []BoxObj1={binglike,youdaolike,jinshanlike,textpanel,choosepanel};
    Object []BoxObj2={searchpanel,textpanel};
    Object []BoxObj3={searchpanel,textpanel};
    
    //create a new lock
  	private Lock lock=new ReentrantLock();
   
    
	public static void main(String[] args){
		Testwindow win=new Testwindow();
		win.setLocation(200,100);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(700, 500);
		win.setVisible(true);
	}
	
	Testwindow(){	
		
		this.connect();
		
		lock.lock();
		DataOutputStream toServer;
		try {
			toServer = new DataOutputStream(socket.getOutputStream());
			toServer.writeInt(7);
			toServer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
		
		
		JPanel panel1=loginpanel.getPanel();
        JPanel panel2=searchpanel.getPanel();
        JPanel panel3=choosepanel.getPanel();
        JPanel panel4=textpanel.getPanel();
        
        JPanel Pan1 = new JPanel();
		GridLayout gridLayout = new GridLayout(3, 1);  
        Pan1.setLayout(gridLayout); 
        Pan1.add(panel1);
        Pan1.add(panel2);
        Pan1.add(panel3);

        add(panel4,BorderLayout.CENTER);
        add(Pan1,BorderLayout.NORTH);
        
        //buttonlistener
        searchpanel.Search.addActionListener(new ButtonListener(1,user,socket,obj1));
     //   searchpanel.Prev.addActionListener(new ButtonListener(2,user,socket,obj));
     //   searchpanel.Next.addActionListener(new ButtonListener(3,user,socket,obj));
        loginpanel.Login.addActionListener(new ButtonListener(5,user,socket,obj2));
     //   loginpanel.message.addActionListener(new ButtonListener(7,user,socket,obj));
        textpanel.share.addActionListener(new ButtonListener(8,user,socket,obj1));
       
        //change color
        loginpanel.colorgreen.addActionListener(new ButtonListener(10,user,socket,obj3));
        loginpanel.coloryellow.addActionListener(new ButtonListener(11,user,socket,obj3));
        loginpanel.colorblue.addActionListener(new ButtonListener(12,user,socket,obj3));
        loginpanel.colordarkblue.addActionListener(new ButtonListener(13,user,socket,obj3));
        loginpanel.colorpink.addActionListener(new ButtonListener(14,user,socket,obj3));
        loginpanel.colorblack.addActionListener(new ButtonListener(15,user,socket,obj3));
       

        //checkboxlistener
        choosepanel.bing.addItemListener(new CheckBoxListener(socket,1,user,BoxObj1));
        choosepanel.youdao.addItemListener(new CheckBoxListener(socket,1,user,BoxObj1));
        choosepanel.jinshan.addItemListener(new CheckBoxListener(socket,1,user,BoxObj1));
        textpanel.bing.addItemListener(new CheckBoxListener(socket,4,user,BoxObj2));
        textpanel.youdao.addItemListener(new CheckBoxListener(socket,4,user,BoxObj2));
        textpanel.jinshan.addItemListener(new CheckBoxListener(socket,4,user,BoxObj2));
        textpanel.like.addItemListener(new CheckBoxListener(socket,7,user,BoxObj3));
  //      textpanel.takeword.addItemListener(new CheckBoxListener(8,));
     
     //   JTextField x=searchpanel.input.getTextField();
     //   x.getDocument().addDocumentListener(new documentListener(searchpanel,textpanel));

		
       Thread receiveTask=new Thread(new ReceiveTask());
		receiveTask.start();
		
		Thread fetchMessageTask=new Thread(new FetchMessageTask());
		fetchMessageTask.start();
	}
	
	
	public void connect(){
		try{
			//create a socket to connect to the server
			socket = new Socket("114.212.135.139",8080);
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	class FetchMessageTask implements Runnable{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try{
					lock.lock();
					DataOutputStream toServer=new DataOutputStream(socket.getOutputStream());
					toServer.writeInt(0);
					toServer.flush();
					lock.unlock();
					Thread.sleep(10000);
				}
				catch (IOException ex){
					ex.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
	}
	
	class ReceiveTask implements Runnable{
		private Lock lock=new ReentrantLock();

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{			   
			    while(true){
			    	lock.lock();
			    	
					DataInputStream fromServer=new DataInputStream(socket.getInputStream());
					
					int type=fromServer.readInt();
					
					switch(type){
					case 0:{//get message
						ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
						Vector<Card> temp=(Vector<Card>)ois.readObject();
						
						for(int i=0;i<temp.size();i++){
							cards.add(temp.get(i));
						}
						break;
					}
					case 1:{//log in
						if(fromServer.readBoolean())user.setUsername("Zarah");////?????
						break;
					}
					case 2:{//register
						if(fromServer.readBoolean())textpanel.Out.append("Register SUCCESS\n");
						break;
					}
					case 3:{
						binglike=fromServer.readInt();
						youdaolike=fromServer.readInt();
						jinshanlike=fromServer.readInt();
						
						break;
					}
					case 4:{
						String message=fromServer.readUTF();
						textpanel.Out.append("The message is : " + message+"\n");
						break;
					}
					case 5:{//online users
						ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
						
						Vector<String> onlineUsers=(Vector<String>)ois.readObject();
						textpanel.Out.append("The online Clients: \n");
						for(int i=0;i<onlineUsers.size();i++){
							textpanel.Out.append(onlineUsers.get(i)+"\n");
						}
						break;
					}
					case 6:{//all users 
						ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
						
						Vector<String> allUsers=(Vector<String>)ois.readObject();
						textpanel.Out.append("All Clients: \n");
						for(int i=0;i<allUsers.size();i++){
							textpanel.Out.append(allUsers.get(i)+"\n");
						}
						break;
					}
					case 7:{//get everyday sentence
						String sentence=fromServer.readUTF();
						System.out.println(sentence);
						textpanel.sen.setText(sentence);
						break;
					}
					default:break;
					}
					
					lock.unlock();
			    }
			}
			catch (IOException ex){
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}
}
