package src.Interface.share;

import Server.Card;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RecievePicture extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String [] meList;
	
	private JTextField path=new JTextField(20);
	private JButton open=new JButton("open file");
	
	private JButton save=new JButton("save");
	JList<String>messagelist;
	
	
	private JLabel label=new JLabel();
    private JFileChooser chooser=new JFileChooser();
    private JFileChooser chooserToSave = new JFileChooser();
    
    
    private static final int DEFAULT_WIDTH = 700;
    private static final int DEFAULT_HEIGHT = 600;
    
	ImageIcon icon=null;
	Card card=new Card();
	
	Socket socket=null;
	
	private Lock lock=new ReentrantLock();

	
	public RecievePicture(String content,Socket socket,String[] melist){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        this.socket=socket;
        this.meList=melist;
		
        setTitle("Make a word card!");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
       
        JPanel panel1=new JPanel();
        panel1.add(path,FlowLayout.LEFT);
        panel1.add(open,FlowLayout.CENTER);
        
        JPanel panel2=new JPanel();
        GridLayout gridLayout = new GridLayout(2,1,5,10);  
		panel2.setLayout(gridLayout);
		messagelist=new JList<String>(meList);
		messagelist.setFixedCellWidth(100);
		messagelist.setFixedCellHeight(20);
		messagelist.setVisibleRowCount(20);
		panel2.add(messagelist);
        panel2.add(save);
        
        add(panel1,BorderLayout.NORTH);
        add(label,BorderLayout.CENTER);
        add(panel2,BorderLayout.EAST);
        
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
        
        messagelist.addListSelectionListener(new ListSelectionListener(){    //相应点击联想框

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(messagelist.getSelectedValue()==null) return;
				int num = messagelist.getSelectedIndex();
				String key = meList[num];
				//setpicturelabel
			}
		});
    }
}