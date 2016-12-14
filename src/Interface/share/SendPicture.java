package src.Interface.share;

import Server.Card;

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

	Vector<String>list = new Vector<String>();
	
	JTextField path=new JTextField(20);
	JButton open=new JButton("open file");
	
	JTextField users=new JTextField(20);
	JButton send=new JButton("send");
	//JButton save=new JButton("save");
	
	JLabel label=new JLabel();
    JFileChooser chooser=new JFileChooser();
    JFileChooser chooserToSave = new JFileChooser();
    
    JList<String> userlist;
    JCheckBox online = new JCheckBox("online");
    JPanel Left = new JPanel();
    
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    
	ImageIcon icon=null;
	Card card=new Card();
	
	Socket socket=null;
	
	private Lock lock=new ReentrantLock();

	
	public SendPicture(String content,Socket socket,Vector<String>namelist,Vector<String>onlinenamelist){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.list=namelist;
        this.socket=socket;
		
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
		userlist=new JList<String>(list);
		userlist.setFixedCellWidth(100);
		userlist.setFixedCellHeight(20);
		userlist.setVisibleRowCount(20);
		Left.add(userlist);
		Left.add(online);
	
        
        add(panel1,BorderLayout.NORTH);
        add(label,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        add(Left,BorderLayout.WEST);
        
        chooser.setCurrentDirectory(new File("."));
        
        chooser.setCurrentDirectory(new File("."));
        chooserToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
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
	                    card.draw(content,name,true);
			            
	                    if(card.validable()){
		                    icon=new ImageIcon(card.image);
		                    //icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), getHeight()-25, Image.SCALE_DEFAULT));
				                
		                    label.setIcon(icon);
	                    }
	                }
			}
        });
        
        
        //send
        send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!card.validable())return;
				if(users.getText().length()==0)return;
				
				sendCard(users.getText());
			}
        	
        });
        
        userlist.addListSelectionListener(new ListSelectionListener(){    //相应点击联想框

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(userlist.getSelectedValue()==null) return;
				int num = userlist.getSelectedIndex();
				String key = list.elementAt(num);
				users.setText(key);
			}
		});
        
        
        online.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(online.isSelected()){
					list=onlinenamelist;
				}
				else
					list=namelist;
				userlist.revalidate();
				userlist.repaint();
				Left.revalidate();
				Left.repaint();
			}
        	
        });
        
    }
	
	//send the card
	public void sendCard(String usernames){
		lock.lock();
		try {
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			//send
			toServer.writeInt(9);
			toServer.writeUTF(usernames);

			toServer.flush();
				        
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());

	        objectOutputStream.writeObject(card);
	        objectOutputStream.flush();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
		
	}

}