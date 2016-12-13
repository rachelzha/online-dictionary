package Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Picture extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField path=new JTextField(20);
	private JButton open=new JButton("open file");
	
	private JTextField users=new JTextField(20);
	private JButton send=new JButton("send");
	private JButton save=new JButton("save");
	
	private JLabel label=new JLabel();
    private JFileChooser chooser=new JFileChooser();
    private JFileChooser chooserToSave = new JFileChooser();
    
    private static final int DEFAULT_WIDTH = 700;
    private static final int DEFAULT_HEIGHT = 600;
    
	ImageIcon icon=null;
	Card card=new Card();
	
	Socket socket=null;
	
	private Lock lock=new ReentrantLock();

	
	public Picture(String content,Socket socket){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        this.socket=socket;
		
        setTitle("Make a word card!");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
       
        JPanel panel1=new JPanel();
        panel1.add(path,FlowLayout.LEFT);
        panel1.add(open,FlowLayout.CENTER);
        
        JPanel panel2=new JPanel();
        panel2.add(users, FlowLayout.LEFT);
        panel2.add(send,FlowLayout.CENTER);
        panel2.add(save,FlowLayout.RIGHT);
        
        add(panel1,BorderLayout.NORTH);
        add(label,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        
        chooser.setCurrentDirectory(new File("."));
        
        chooser.setCurrentDirectory(new File("."));
        chooserToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        //open
        open.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
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
	                    icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), getHeight()-25, Image.SCALE_DEFAULT));
			                
	                    label.setIcon(icon);
                    }
                }
            }
        });
        
        //save
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
            	if(!card.validable())return;
            	
            	int result = chooserToSave.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                	String path = chooserToSave.getSelectedFile().getPath();
                	card.saveCard(path);
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
    }
	
	//send the card
	public void sendCard(String usernames){
		lock.lock();
		try {
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			//send
			toServer.writeInt(4);
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