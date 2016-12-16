package src.Interface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import Server.Card;
import Server.Message;
import src.Interface.listener.ButtonListener;
import src.Interface.listener.CheckBoxListener;
import src.Interface.listener.ComboBoxListener;
import src.Interface.listener.Info;
import src.Interface.panel.ChoosePanel;
import src.Interface.panel.LoginPanel;
import src.Interface.panel.SearchPanel;
import src.Interface.panel.TextPanel;
import src.Interface.panel.drawComponent;
import src.Translate.BaiduTranslate;
import src.Translate.History;
import src.userLogin.UserState;


public class Testwindow extends JFrame{
	
	public static Socket socket=null;
	//public static DataInputStream dataFromServer=null;
	//public static DataOutputStream dataToServer=null;
	public static ObjectInputStream fromServer=null;
	public static ObjectOutputStream toServer=null;

	
	public static UserState user=new UserState();
	
	public static Info info = new Info();
	public static History history= new History();
	
	public static LoginPanel loginpanel = new LoginPanel();
	public static SearchPanel searchpanel = new SearchPanel();
	public static ChoosePanel choosepanel = new ChoosePanel();
	public static TextPanel textpanel = new TextPanel();
	
	public static Vector<Message> messages=new Vector<Message>();
	
    //create a new lock
  	//private Lock lock=new ReentrantLock();
   
    
	public static void main(String[] args){
		Testwindow tw=new Testwindow();
		tw.setLocation(200,100);
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tw.setSize(700, 500);
		tw.setVisible(true);
	}
	
	Testwindow(){
		connect();
		
		//lock.lock();
		//DataOutputStream toServer;
		
		try {
			
			toServer.writeObject((int)4);//everyday sentences
		
			
			String sentence=(String)fromServer.readObject();
			//System.out.println("4");

			
			System.out.println(sentence);
			textpanel.sen.setText(sentence);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        searchpanel.Search.addActionListener(new ButtonListener(1));
        searchpanel.Prev.addActionListener(new ButtonListener(2));
        searchpanel.Next.addActionListener(new ButtonListener(3));
        loginpanel.Login.addActionListener(new ButtonListener(5));
        loginpanel.message.addActionListener(new ButtonListener(7));
        textpanel.share.addActionListener(new ButtonListener(8));
        loginpanel.Logout.addActionListener(new ButtonListener(9));
        textpanel.like.addActionListener(new ButtonListener(4));

        
        //change color
        loginpanel.colorgreen.addActionListener(new ButtonListener(10));
        loginpanel.coloryellow.addActionListener(new ButtonListener(11));
        loginpanel.colorblue.addActionListener(new ButtonListener(12));
        loginpanel.colordarkblue.addActionListener(new ButtonListener(13));
        loginpanel.colorpink.addActionListener(new ButtonListener(14));
        loginpanel.colorblack.addActionListener(new ButtonListener(15));
       

        //checkboxlistener
        choosepanel.bing.addItemListener(new CheckBoxListener(1));
        choosepanel.youdao.addItemListener(new CheckBoxListener(1));
        choosepanel.jinshan.addItemListener(new CheckBoxListener(1));
        textpanel.bing.addItemListener(new CheckBoxListener(4));
        textpanel.youdao.addItemListener(new CheckBoxListener(4));
        textpanel.jinshan.addItemListener(new CheckBoxListener(4));
     //   textpanel.like.addItemListener(new CheckBoxListener(7));
        
  //      searchpanel.input.getComponent(0).addMouseListener(new ComboBoxListener(searchpanel,textpanel) );
  //      textpanel.takeword.addItemListener(new CheckBoxListener(8,));
		
        //Thread receiveTask=new Thread(new ReceiveTask());
		//receiveTask.start();
		
		Thread fetchMessageTask=new Thread(new FetchMessageTask());
		fetchMessageTask.start();
	}
	
	
	public static void connect(){
		try{
			socket = new Socket("172.26.74.203",8080);
			//dataToServer=new DataOutputStream(socket.getOutputStream());
			//dataFromServer=new DataInputStream(socket.getInputStream());
			
			//objectFromServer=new ObjectInputStream(socket.getInputStream());
			toServer=new ObjectOutputStream(socket.getOutputStream());
			fromServer=new ObjectInputStream(socket.getInputStream());
			
			
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	class FetchMessageTask implements Runnable{
		private Lock lock=new ReentrantLock();
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try{
						if(user.Logged()){
						lock.lock();
						//DataOutputStream toServer=new DataOutputStream(socket.getOutputStream());
					//ObjectOutputStream toServer=new ObjectOutputStream(socket.getOutputStream());
						toServer.writeObject((int)0);
						
						Vector<Message>temp=(Vector<Message>)fromServer.readObject();
						
						lock.unlock();
						
						if(temp.size()>0){
							String messagefile1="image/message/3.png";
							String messagefile2="image/message/4.png";
							drawComponent draw=new drawComponent();
							draw.drawButton(messagefile1, messagefile2, 20, 20, loginpanel.message);
							loginpanel.Right.revalidate();
							loginpanel.Right.repaint();
						}
						for(int i=0;i<temp.size();i++){
							messages.add(temp.get(i));
						}
						
						System.out.println("messages: "+messages.size());
						//lock.unlock();
						}
						Thread.sleep(10000);
				}
				catch (Exception ex){
					lock.unlock();
					ex.printStackTrace();
					System.exit(0);
				}
			}
		}
		
	}
	
}
