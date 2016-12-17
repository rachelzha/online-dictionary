package src.Interface;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;

import src.Interface.listener.ButtonListener;
import src.Interface.listener.CheckBoxListener;
import src.Interface.panel.ChoosePanel;
import src.Interface.panel.LoginPanel;
import src.Interface.panel.SearchPanel;
import src.Interface.panel.TextPanel;
import src.Interface.tool.drawComponent;
import src.Translate.History;
import src.information.Info;
import src.information.Message;
import src.information.UserState;


public class MainWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//socket
	public static Socket socket=null;
	public static ObjectInputStream fromServer=null;
	public static ObjectOutputStream toServer=null;

	//info
	public static UserState user=new UserState();
	public static Info info = new Info();
	public static History history= new History();
	
	//panel
	public static LoginPanel loginpanel = new LoginPanel();
	public static SearchPanel searchpanel = new SearchPanel();
	public static ChoosePanel choosepanel = new ChoosePanel();
	public static TextPanel textpanel = new TextPanel();
   
    //main
/*	public static void main(String[] args){
		MainWindow tw=new MainWindow();
		tw.setLocation(200,100);
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tw.setSize(700, 500);
		tw.setVisible(true);
	}*/
	
	public MainWindow(){
		connect();
		
		try {
			
			toServer.writeObject((int)4);//everyday sentences
			String sentence=(String)fromServer.readObject();
			
		//	System.out.println(sentence);
			textpanel.sen.setText(sentence);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//layout
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

        
        //ActionListener
        searchpanel.Search.addActionListener(new ButtonListener(1));
        searchpanel.Prev.addActionListener(new ButtonListener(2));
        searchpanel.Next.addActionListener(new ButtonListener(3));
        loginpanel.Login.addActionListener(new ButtonListener(5));
        loginpanel.message.addActionListener(new ButtonListener(7));
        textpanel.share.addActionListener(new ButtonListener(8));
        loginpanel.Logout.addActionListener(new ButtonListener(9));
        textpanel.like.addActionListener(new ButtonListener(4));

        
        //change color listener
        loginpanel.colorgreen.addActionListener(new ButtonListener(10));
        loginpanel.coloryellow.addActionListener(new ButtonListener(11));
        loginpanel.colorblue.addActionListener(new ButtonListener(12));
        loginpanel.colordarkblue.addActionListener(new ButtonListener(13));
        loginpanel.colorpink.addActionListener(new ButtonListener(14));
        loginpanel.colorblack.addActionListener(new ButtonListener(15));
       

        //ItemListener
        choosepanel.bing.addItemListener(new CheckBoxListener(1));
        choosepanel.youdao.addItemListener(new CheckBoxListener(1));
        choosepanel.jinshan.addItemListener(new CheckBoxListener(1));
        textpanel.bing.addItemListener(new CheckBoxListener(4));
        textpanel.youdao.addItemListener(new CheckBoxListener(4));
        textpanel.jinshan.addItemListener(new CheckBoxListener(4));
  
        //message
		Thread fetchMessageTask=new Thread(new FetchMessageTask());
		fetchMessageTask.start();
	}
	
	//connect Server
	public static void connect(){
		try{
			socket = new Socket("172.28.190.115",8080);
			
			toServer=new ObjectOutputStream(socket.getOutputStream());
			fromServer=new ObjectInputStream(socket.getInputStream());

		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	//fetch message each 10 seconds
	class FetchMessageTask implements Runnable{
		private Lock lock=new ReentrantLock();
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try{
					if(user.Logged()){
						lock.lock();
						//send
						toServer.writeObject((int)0);
						
						//receive
						Vector<Message>temp=(Vector<Message>)fromServer.readObject();
						
						lock.unlock();
					//	System.out.println(temp.size());
						
						if(temp.size()>0){
							//show message
							String messagefile1="image/message/3.png";
							String messagefile2="image/message/4.png";
							drawComponent.drawButton(messagefile1, messagefile2, 20, 20, loginpanel.message);
							loginpanel.Right.revalidate();
							loginpanel.Right.repaint();
						}
						info.setmessage(temp);
						
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
