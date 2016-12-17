package src.Interface.share;

import src.Interface.MainWindow;
import src.Translate.Translation;
import src.information.Card;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.Socket;
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
	
	String usernames;
	
	String []list = new String[1000];
	String []onlinelist = new String[1000];
	
	JTextField path=new JTextField(20);
	JButton open=new JButton("open file");
	JButton colorchooser=new JButton("choose color");
	
	
	JTextField users=new JTextField(20);
	JButton send=new JButton("send");
	
	JLabel label=new JLabel();
    JFileChooser chooser=new JFileChooser();
    JFileChooser chooserToSave = new JFileChooser();
    
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> userlist=new JList<>(model);
    
    JCheckBox online = new JCheckBox("online");
    JCheckBox sendall = new JCheckBox("SendAll"); 
    
    JPanel Left = new JPanel();
    
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    
	ImageIcon icon=null;
	
	Card card=new Card();
	
	JScrollPane jsp=new JScrollPane();
	
	Socket socket=null;
	
	private Lock lock=new ReentrantLock();
	
	int flag=1;
	
	Color color=new Color(102,102,102);

	
	public SendPicture(Translation t){
		
        setVisible(true);
       
        list = new String[MainWindow.info.getuserlist().size()+5];
        onlinelist = new String[MainWindow.info.getonlineuserlist().size()+5];
        for(int i=0;i<MainWindow.info.getuserlist().size();i++){
        	list[i]=MainWindow.info.getuserlist().get(i);
        }
        for(int i=0;i<MainWindow.info.getonlineuserlist().size();i++){
        	onlinelist[i]=MainWindow.info.getonlineuserlist().get(i);
        }
        
		
        setTitle("Make a word card!");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
       
        JPanel panel1=new JPanel();
        panel1.add(path,FlowLayout.LEFT);
        panel1.add(open,FlowLayout.CENTER);
        panel1.add(colorchooser,FlowLayout.RIGHT);
        
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

		GridLayout grid = new GridLayout(2,1,5,5);
		JPanel down = new JPanel();
		down.setLayout(grid);
		down.add(online);
		down.add(sendall);
		Left.add(new JScrollPane(userlist));
		Left.add(down);
	
        
        add(panel1,BorderLayout.NORTH);
        //add(label,BorderLayout.CENTER);
        add(jsp,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        add(Left,BorderLayout.WEST);
        
        chooser.setCurrentDirectory(new File("."));
        
        chooser.setCurrentDirectory(new File("."));
        chooserToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        //default card
        card.draw(t, color);
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
	                    card.draw(t,name,color);

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
        
        //
        userlist.addListSelectionListener(new ListSelectionListener(){//点击相应选项

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
			//	if(userlist.getSelectedValue()==null) return;
				if(flag==1){
					usernames=users.getText();
					if(usernames==null||usernames.length()==0){
						usernames=userlist.getSelectedValue();
						System.out.println("1 "+usernames);
					}
					else{
						usernames+=";";
						usernames+=userlist.getSelectedValue();
						System.out.println("2 "+usernames);
					}
					//System.out.println(usernames);/////
					users.setText(usernames);
					
				}
				flag=1-flag;
			}
		});
        
        //群发
        sendall.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(!sendall.isSelected()){
					users.setText("");
					return;
				}
				if(!online.isSelected()){
					usernames=list[0];
					for(int i=1;i<MainWindow.info.getuserlist().size();i++){
						usernames+=";";
						usernames+=list[i];
					}
				}
				else{
					usernames=onlinelist[0];
					for(int i=1;i<MainWindow.info.getonlineuserlist().size();i++){
						usernames+=";";
						usernames+=onlinelist[i];
					}
					
				}
				users.setText(usernames);
			}
        	
        });
        
        //show online users
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
        
        //color chooser
        colorchooser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				color = JColorChooser.showDialog(null,"Choose Color",color);
				
				//draw
				//card.draw(t, new Color(255,255,255));
                card.draw(t,color);

                icon=new ImageIcon(card.image);
                label.setIcon(icon);
                jsp.add(label);
                jsp.setViewportView(label);
			}
        	
        });
    }
	
	//send the card
	public void sendCard(String usernames){
		
		try {
			//send
			lock.lock();
			MainWindow.toServer.writeObject((int)9);
			MainWindow.toServer.writeObject(usernames);

			MainWindow.toServer.writeObject(card);

			lock.unlock();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			lock.unlock();
			e.printStackTrace();
		}
		
	}

}