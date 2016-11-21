package src.Interface;

import src.Translate.*;
import src.userLogin.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;


public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel panel1 = new JPanel();
	JLabel Label1 = new JLabel("登陆");
	JLabel Label2 = new JLabel("注册");
	
	JPanel panel2 = new JPanel();
	JLabel Label3 = new JLabel("Input");
	JTextField input = new JTextField(35);
	JButton Search = new JButton("Search");

	JPanel panel3 = new JPanel();
	JCheckBox box1 = new JCheckBox("百度",true);
	JCheckBox box2 = new JCheckBox("有道",true);
	JCheckBox box3 = new JCheckBox("金山",true);
	
	JPanel Pan1 = new JPanel();
	
	
	mypanel panel4 = new mypanel();
	JLabel Label4 = new JLabel("百度");
	JTextArea Out1 = new JTextArea("",5,35);
	JScrollPane text1=new JScrollPane(Out1);
	JCheckBox like1 = new JCheckBox("点赞",false);
	
	mypanel panel5 = new mypanel();
	JLabel Label5 = new JLabel("有道");
	JTextArea Out2 = new JTextArea("",5,35);
	JScrollPane text2=new JScrollPane(Out2);
	JCheckBox like2 = new JCheckBox("点赞",false);
	
	mypanel panel6 = new mypanel();
	JLabel Label6 = new JLabel("金山");
	JTextArea Out3 = new JTextArea("",5,35);
	JScrollPane text3=new JScrollPane(Out3);
	JCheckBox like3 = new JCheckBox("点赞",false);
	
	mypanel Pan2 = new mypanel();
	
	//用户状态
	UserState user=new UserState();
	
	Socket socket;
	//io streams
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	//likes
	int baiduLikes;
	int youdaoLikes;
	int jinshanLikes;
	
	
	public static void main(String[] args){
		Window window=new Window();
		window.setTitle("Dictionary");
		window.setLocation(200,100);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(700, 500);
		window.setVisible(true);
	}
	
	public Window(){
        FlowLayout flowLayout1 = new FlowLayout(FlowLayout.RIGHT , 30 , 5);  
        panel1.setLayout(flowLayout1);  
        panel1.add(Label1);  
        panel1.add(Label2);  
        
        FlowLayout flowLayout2 = new FlowLayout(FlowLayout.CENTER , 20 , 5);  
        panel2.setLayout(flowLayout2);  
        panel2.add(Label3);  
        panel2.add(input);
        panel2.add(Search);
        
        FlowLayout flowLayout3 = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
        panel3.setLayout(flowLayout3);  
        panel3.add(box1);  
        panel3.add(box2);
        panel3.add(box3); 
        
        GridLayout gridLayout = new GridLayout(3, 1);  
        Pan1.setLayout(gridLayout); 
        Pan1.add(panel1);
        Pan1.add(panel2);
        Pan1.add(panel3);
		
        FlowLayout flowLayout4 = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
        panel4.setLayout(flowLayout4);  
        //Out1.setMargin(new Insets(5, 5, 5, 5));
        Out1.setEditable(false);  
		Out1.setLineWrap(true);
		Out1.setFont(new Font("Courier",Font.BOLD,15));
        panel4.add(Label4);  
        panel4.add(text1);
      //  panel4.add(like1);
        
        FlowLayout flowLayout5 = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
        panel5.setLayout(flowLayout5);  
        //Out2.setMargin(new Insets(5, 5, 5, 5));
		Out2.setEditable(false);
		Out2.setLineWrap(true);
		Out2.setFont(new Font("Courier",Font.BOLD,15));
        panel5.add(Label5);  
        panel5.add(text2);
     //   panel5.add(like2);
        
        FlowLayout flowLayout6 = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
        panel6.setLayout(flowLayout6);  
        //Out3.setMargin(new Insets(5, 5, 5, 5));
		Out3.setEditable(false);
		Out3.setLineWrap(true); 
		Out3.setFont(new Font("Courier",Font.BOLD,15));
        panel6.add(Label6);  
        panel6.add(text3);
     //   panel6.add(like3);
        
        GridLayout gridLayout2 = new GridLayout(3,1,10,5);  
        Pan2.setLayout(gridLayout2);
		Pan2.add(panel4);  
        Pan2.add(panel5);  
        Pan2.add(panel6);     
        
        setLayout (new BorderLayout(0,10));
        add(Pan1,BorderLayout.NORTH);
		add(Pan2,BorderLayout.CENTER);
		
		addaction();
		
		try{
			//create a socket to connect to the server
			//socket = new Socket("172.28.130.138",8000);
			socket = new Socket("172.26.74.203",8000);
			//Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	public void addaction(){
		
		Label1.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!user.Logged()){
					Login login = new Login(socket);
					login.setLocation(200,100);
					login.setVisible(true);
					user=login.getUser();
					//System.out.println(user.Logged());		
				}
				if(user.Logged()){	
					Label1.setText(user.getUsername());
					Label1.revalidate();
					Label1.repaint();
					panel4.add(like1);
					panel4.revalidate();
					panel4.repaint();
					panel5.add(like2);
					panel5.revalidate();
					panel5.repaint();
					panel6.add(like3);
					panel6.revalidate();
					panel6.repaint();
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Label2.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Register reg = new Register(socket);
				reg.setLocation(200,100);
				reg.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		box1.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	resetPan2();
		    }
		});
		
		box2.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	resetPan2();
		    }
		});
		
		box3.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	resetPan2();
		    }
		});
		
		//点赞
		like1.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	String key = input.getText();
		    	
		    	try{
					//send
					toServer.writeInt(5);
					toServer.writeUTF(user.getUsername());
					toServer.writeUTF(key);
					toServer.writeUTF("baidu");
				}
				catch(IOException ex){
					System.err.println(ex);
				}
		    }
		});
		
		like2.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	String key = input.getText();

		    	try{
					//send
					toServer.writeInt(5);
					toServer.writeUTF(user.getUsername());
					toServer.writeUTF(key);
					toServer.writeUTF("youdao");
				}
				catch(IOException ex){
					System.err.println(ex);
				}
		    }
		});
		
		like3.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	String key = input.getText();

		    	try{
					//send
					toServer.writeInt(5);
					toServer.writeUTF(user.getUsername());
					toServer.writeUTF(key);
					toServer.writeUTF("jinshan");
				}
				catch(IOException ex){
					System.err.println(ex);
				}
		    }
		});
		
		//查询
		Search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String key = input.getText();
				
				try{
					//send
					if(!user.Logged()){
						toServer.writeInt(4);
					}
					else{
						toServer.writeInt(3);
						toServer.writeUTF(user.getUsername());
					}
					
					toServer.writeUTF(key);
					
					//receive
					baiduLikes=fromServer.readInt();
				//	System.out.println(baiduLikes);

					youdaoLikes=fromServer.readInt();
				//	System.out.println(youdaoLikes);

					jinshanLikes=fromServer.readInt();
					
				//	System.out.println(jinshanLikes);
					resetPan2();
				}
				catch(IOException ex){
					System.err.println(ex);
				}
				
				
				if(box1.isSelected()){
					BaiduTranslate B = new BaiduTranslate();
					String text = B.Translation(key);
					Out1.setText(text);
				}
				if(box2.isSelected()){
					YoudaoTranslate Y = new YoudaoTranslate();
					String text = Y.Translation(key);
					Out2.setText(text);
				}
				if(box3.isSelected()){
					JinshanTranslate J = new JinshanTranslate();
					String text = J.Translate(key);
					Out3.setText(text);
				}
			}
		});
		
	}
	
	public void resetPan2(){
		Pan2.removeAll();
		int a = baiduLikes;
		int b = youdaoLikes;
		int c = jinshanLikes;
		if(a>=b&&b>=c){
			Pan2.add(panel4);  
	        Pan2.add(panel5);  
	        Pan2.add(panel6);
		}
		else if(a>=c&&c>=b){
			Pan2.add(panel4);  
	        Pan2.add(panel6);  
	        Pan2.add(panel5);
		}
		else if(b>=a&&a>=c){
			Pan2.add(panel5);  
	        Pan2.add(panel4);  
	        Pan2.add(panel6);
		}
		else if(b>=c&&c>=a){
			Pan2.add(panel5);  
	        Pan2.add(panel6);  
	        Pan2.add(panel4);
		}
		else if(c>=a&&a>=b){
			Pan2.add(panel6);  
	        Pan2.add(panel4);  
	        Pan2.add(panel5);
		}
		else{//c>=b>=a
			Pan2.add(panel6);  
	        Pan2.add(panel5);  
	        Pan2.add(panel4);
		}
		
		int count = 3;
		if(!box1.isSelected()){
			Pan2.remove(panel4);
			count--;
		}
		if(!box2.isSelected()){
			Pan2.remove(panel5);
			count--;
		}
		if(!box3.isSelected()){
			Pan2.remove(panel6);
			count--;
		}
		
		Pan2.setLayout(new GridLayout(count,1,10,5));
		Pan2.revalidate();
    	Pan2.repaint();
	}
}


class mypanel extends JPanel {    
    private static final long serialVersionUID = 1L;  
      
    public mypanel(){  
        super();  
    }  
    public void paint(Graphics g) {  
        //这一句很重要!!! 不加这句不会清除以前的图像  
        super.paint(g);  
    }  
}  
