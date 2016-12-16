package src.Interface.share;

import Server.Card;
import src.Interface.Testwindow;
import src.Translate.Translation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SendPicture extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Vector<String>list = new Vector<String>();
	String []list = new String[1000];
	String []onlinelist = new String[1000];
	
	JTextField path=new JTextField(20);
	JButton open=new JButton("open file");
	
	JTextField users=new JTextField(20);
	JButton send=new JButton("send");
	//JButton save=new JButton("save");
	
	JLabel label=new JLabel();
    JFileChooser chooser=new JFileChooser();
    JFileChooser chooserToSave = new JFileChooser();
    
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> userlist=new JList<>(model);
    
    JCheckBox online = new JCheckBox("online");
    JPanel Left = new JPanel();
    
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    
	ImageIcon icon=null;
	Card card=new Card();
	
	JScrollPane jsp=new JScrollPane();
	
	Socket socket=null;
	
	//private Lock lock=new ReentrantLock();

	
	public SendPicture(Translation t){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
       
        for(int i=0;i<Testwindow.info.getuserlist().size();i++){
        	list[i]=Testwindow.info.getuserlist().get(i);
        }
        for(int i=0;i<Testwindow.info.getonlineuserlist().size();i++){
        	onlinelist[i]=Testwindow.info.getonlineuserlist().get(i);
        }
        
		
        setTitle("Make a word card!");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
       
        JPanel panel1=new JPanel();
        panel1.add(path,FlowLayout.LEFT);
        panel1.add(open,FlowLayout.CENTER);
        
        JPanel panel2=new JPanel();
        panel2.add(users, FlowLayout.LEFT);
        panel2.add(send,FlowLayout.CENTER);
    //    panel2.add(save,FlowLayout.RIGHT);
       
        GridLayout gridLayout = new GridLayout(2,1,5,10);  
		Left.setLayout(gridLayout);
		userlist=new JList<String>(list);////////
		
		userlist.setFixedCellWidth(100);
		userlist.setFixedCellHeight(20);
		userlist.setVisibleRowCount(20);
		Left.add(userlist);
		Left.add(online);
	
        
        add(panel1,BorderLayout.NORTH);
        //add(label,BorderLayout.CENTER);
        add(jsp,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        add(Left,BorderLayout.WEST);
        
        chooser.setCurrentDirectory(new File("."));
        
        chooser.setCurrentDirectory(new File("."));
        chooserToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        //default card
        card.draw(t, false);
        icon=new ImageIcon(card.image);
        label.setIcon(icon);
        jsp.add(label);
        jsp.setViewportView(label);
        
        //open
        open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 int result = chooser.showOpenDialog(null);
	                if(result == JFileChooser.APPROVE_OPTION){
	                	//get file name
	                    String name = chooser.getSelectedFile().getPath();
	                    //print the file path
	                    path.setText(name);
	                    
	                    //draw
	                    card.draw(t,name,false);

		                icon=new ImageIcon(card.image);
		                label.setIcon(icon);
		                jsp.add(label);
	                    jsp.setViewportView(label);
	                }
			}
        });
        
        
        //send
        send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(users.getText().length()==0)return;
				
				sendCard(users.getText());
			}
        	
        });
        
        userlist.addListSelectionListener(new ListSelectionListener(){    //��Ӧ���

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
			//	if(userlist.getSelectedValue()==null) return;
				String key = userlist.getSelectedValue();
				System.out.println(key);/////
				users.setText(key);
			}
		});
        
        
        online.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(!online.isSelected()){
					userlist.setListData(list);
				}
				else{
					userlist.setListData(onlinelist);
				}		
				
				for(int i=0;i<onlinelist.length;i++){
					System.out.println(onlinelist[i]);
				}
				userlist.revalidate();
				userlist.repaint();
				Left.revalidate();
				Left.repaint();
			}
        	
        });
        
    }
	
	//send the card
	public void sendCard(String usernames){
		//lock.lock();
		try {
			//send
			//ObjectOutputStream toServer=new ObjectOutputStream(Testwindow.socket.getOutputStream());
			Testwindow.toServer.writeObject(9);
			Testwindow.toServer.writeObject(usernames);

			//Testwindow.toServer.flush();
				        
			//ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());

			Testwindow.toServer.writeObject(card);
	        //objectOutputStream.flush();
	        //lock.unlock();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}