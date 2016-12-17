package src.Interface.share;

import src.Interface.MainWindow;
import src.information.Card;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.net.Socket;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReceivePicture extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String [] meList = new String[1000];
	
	private JButton save=new JButton("save");
	JList<String>messagelist;
	
	
	private JLabel label=new JLabel();
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
		
        setVisible(true);
       
        //get messages
        meList = new String[MainWindow.info.getmessage().size()+5];
        for(int i=0;i<MainWindow.info.getmessage().size();i++){
        	meList[i]=MainWindow.info.getmessage().get(i).getinfo();
        }
		
        setTitle("Message Box~");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        JPanel panel2=new JPanel();
        panel2.setLayout(new BorderLayout(10,20));
		
		messagelist=new JList<String>(meList);
		messagelist.setFixedCellWidth(150);
		messagelist.setFixedCellHeight(20);
		messagelist.setVisibleRowCount(20);
		panel2.add(new JScrollPane(messagelist),BorderLayout.CENTER);
        panel2.add(save,BorderLayout.SOUTH);
        
        add(panel2,BorderLayout.WEST);
        add(jsp,BorderLayout.CENTER);
        //add(label,BorderLayout.CENTER);
        
       // chooser.setCurrentDirectory(new File("."));
        
        //chooser.setCurrentDirectory(new File("."));
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
        
        messagelist.addListSelectionListener(new ListSelectionListener(){    //ÏàÓ¦µã»÷list

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(messagelist.getSelectedValue()==null) return;
				int num=messagelist.getSelectedIndex();
				
				card=MainWindow.info.getmessage().get(num).getCard();
                
                    icon=new ImageIcon(card.image);
                       
                    label.setIcon(icon);
                    
                    jsp.add(label);
                    jsp.setViewportView(label);
        
			}
		});
    }
}