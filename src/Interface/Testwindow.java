package src.Interface;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;
import src.userLogin.UserState;


public class Testwindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UserState user=new UserState();
	public Socket socket=null;
	
	int baidulike;
	int youdaolike;
	int jinshanlike;
	
	LoginPanel loginpanel = new LoginPanel();
	SearchPanel searchpanel = new SearchPanel();
	ChoosePanel choosepanel = new ChoosePanel();
	TextPanel textpanel = new TextPanel();
	Object obj1[]={searchpanel,textpanel};
    Object obj2[]={textpanel};
    Object []BoxObj1={baidulike,youdaolike,jinshanlike,textpanel,choosepanel};
    Object []BoxObj2={searchpanel,textpanel};
    Object []BoxObj3={socket,searchpanel,textpanel};
    
	public static void main(String[] args){
		Testwindow win=new Testwindow();
		//win.setTitle("Dictionary");
		win.setLocation(200,100);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(700, 500);
		win.setVisible(true);
	}
	
	Testwindow(){	
		
		this.connect();
		
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
     //   searchpanel.pulldown.addActionListener(new ButtonListener(4,user,socket,obj));
        loginpanel.Login.addActionListener(new ButtonListener(5,user,socket,obj2));
     //   loginpanel.individuation.addActionListener(new ButtonListener(6,user,socket,obj));
     //   loginpanel.message.addActionListener(new ButtonListener(7,user,socket,obj));
     //   textpanel.share.addActionListener(new ButtonListener(8,user,socket,obj));
       
        //checkboxlistener
        choosepanel.baidu.addItemListener(new CheckBoxListener(1,user,BoxObj1));
        choosepanel.youdao.addItemListener(new CheckBoxListener(1,user,BoxObj1));
        choosepanel.jinshan.addItemListener(new CheckBoxListener(1,user,BoxObj1));
        textpanel.baidu.addItemListener(new CheckBoxListener(4,user,BoxObj2));
        textpanel.youdao.addItemListener(new CheckBoxListener(4,user,BoxObj2));
        textpanel.jinshan.addItemListener(new CheckBoxListener(4,user,BoxObj2));
        textpanel.like.addItemListener(new CheckBoxListener(7,user,BoxObj3));
  //      textpanel.takeword.addItemListener(new CheckBoxListener(8,));
        
        
        Thread receiveTask=new Thread(new ReceiveTask());
		receiveTask.start();
	}
	
	
	public void connect(){
		try{
			//create a socket to connect to the server
			socket = new Socket("172.26.218.88",8080);
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	class ReceiveTask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{			   
			    while(true){
					DataInputStream fromServer=new DataInputStream(socket.getInputStream());
					
					int type=fromServer.readInt();
					
					switch(type){
					case 1:{//log in
						if(fromServer.readBoolean())user.setUsername("Zarah");////?????
						break;
					}
					case 2:{//register
						if(fromServer.readBoolean())textpanel.Out.append("Register SUCCESS\n");
						break;
					}
					case 3:{
						baidulike=fromServer.readInt();
						youdaolike=fromServer.readInt();
						jinshanlike=fromServer.readInt();
						
					//	textpanel.Out.append("likes: "+n1+"\t"+n2+"\t"+n3+"\n");
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
					default:break;
					}
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
