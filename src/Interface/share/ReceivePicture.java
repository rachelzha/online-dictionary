package src.Interface.share;

import Server.Card;
import Server.Message;
import src.Interface.Testwindow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReceivePicture extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String [] meList = new String[100];
	
	private JButton save=new JButton("save");
	JList<String>messagelist;
	
	
	private JLabel label=new JLabel();
    private JFileChooser chooser=new JFileChooser();
    private JFileChooser chooserToSave = new JFileChooser();
    
   // JPanel panel1=new JPanel();
    JScrollPane jsp=new JScrollPane();
    
    
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    
	ImageIcon icon=null;
	Card card=new Card();
	
	Socket socket=null;
	
	//private Lock lock=new ReentrantLock();

	
	public ReceivePicture(){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
       
        for(int i=0;i<Testwindow.messages.size();i++){
        	meList[i]=Testwindow.messages.get(i).getinfo();
        }
		
        setTitle("Message Box~");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        JPanel panel2=new JPanel();
        panel2.setLayout(new BorderLayout(10,20));
		
		messagelist=new JList<String>(meList);
		messagelist.setFixedCellWidth(100);
		messagelist.setFixedCellHeight(20);
		messagelist.setVisibleRowCount(20);
		panel2.add(messagelist,BorderLayout.CENTER);
        panel2.add(save,BorderLayout.SOUTH);
        
        add(panel2,BorderLayout.WEST);
        add(jsp,BorderLayout.CENTER);
        //add(label,BorderLayout.CENTER);
        
        chooser.setCurrentDirectory(new File("."));
        
        chooser.setCurrentDirectory(new File("."));
        chooserToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        //save
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub            	
            	int result = chooserToSave.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                	String path = chooserToSave.getSelectedFile().getPath();
                	card.saveCard(path);
                }	
			}
        });
        
        messagelist.addListSelectionListener(new ListSelectionListener(){    //相应点击联想框

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(messagelist.getSelectedValue()==null) return;
				int num=messagelist.getSelectedIndex();
				
				Card card=Testwindow.messages.get(num).getCard();
                
                    icon=new ImageIcon(card.image);
                    //icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), getHeight()-25, Image.SCALE_DEFAULT));
		                
                    label.setIcon(icon);
                    
                    //panel1.add(icon);
                    jsp.add(label);
                    jsp.setViewportView(label);
        
			}
		});
    }
}