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

import javax.swing.*;



public class Window extends JApplet {
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
	
	
	JPanel panel4 = new JPanel();
	JLabel Label4 = new JLabel("百度");
	JTextArea Out1 = new JTextArea("",5,30);
	JScrollPane text1=new JScrollPane(Out1);
	JCheckBox like1 = new JCheckBox("点赞",false);
	
	JPanel panel5 = new JPanel();
	JLabel Label5 = new JLabel("有道");
	JTextArea Out2 = new JTextArea("",5,30);
	JScrollPane text2=new JScrollPane(Out2);
	JCheckBox like2 = new JCheckBox("点赞",false);
	
	JPanel panel6 = new JPanel();
	JLabel Label6 = new JLabel("金山");
	JTextArea Out3 = new JTextArea("",5,30);
	JScrollPane text3=new JScrollPane(Out3);
	JCheckBox like3 = new JCheckBox("点赞",false);
	
	JPanel Pan2 = new JPanel();
	
	LikeDB likeDB = new LikeDB();
	String user = "Rachel";
	
	public void init(){
		
		
	//	setTitle("Dictionary");
		setLocation(200,100);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setVisible(true);
        
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
        Out1.setEditable(false);   //只读
		Out1.setLineWrap(true);  //自动换行
		Out1.setFont(new Font("Courier",Font.BOLD,15));//字体显示效果
        panel4.add(Label4);  
        panel4.add(text1);
        panel4.add(like1);
        
        FlowLayout flowLayout5 = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
        panel5.setLayout(flowLayout5);  
        //Out2.setMargin(new Insets(5, 5, 5, 5));
		Out2.setEditable(false);   //只读
		Out2.setLineWrap(true);  //自动换行
		Out2.setFont(new Font("Courier",Font.BOLD,15));//字体显示效果
        panel5.add(Label5);  
        panel5.add(text2);
        panel5.add(like2);
        
        FlowLayout flowLayout6 = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
        panel6.setLayout(flowLayout6);  
        //Out3.setMargin(new Insets(5, 5, 5, 5));
		Out3.setEditable(false);   //只读
		Out3.setLineWrap(true);  //自动换行
		Out3.setFont(new Font("Courier",Font.BOLD,15));//字体显示效果
        panel6.add(Label6);  
        panel6.add(text3);
        panel6.add(like3);
        
        GridLayout gridLayout2 = new GridLayout(3,1,10,5);  
        Pan2.setLayout(gridLayout2);
		Pan2.add(panel4);  
        Pan2.add(panel5);  
        Pan2.add(panel6);     
        
        setLayout (new BorderLayout(0,10));
        add(Pan1,BorderLayout.NORTH);
		add(Pan2,BorderLayout.CENTER);
		
		addaction();
	}
	
	public void addaction(){
		
		Label1.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Login login = new Login();
				login.init();
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
				Register reg = new Register();
				reg.init();
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
		        if(box1.isSelected()) 
		        	Out1.setText("1");
		        else
		        	Out1.setText("2");
		    }
		});
		
		box2.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		         
		    }
		});
		
		box3.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		         
		    }
		});
		
		like1.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	String key = input.getText();
		    	likeDB.changeLikes(user, key, "baidu");
		    }
		});
		
		like2.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	String key = input.getText();
		    	likeDB.changeLikes(user, key, "youdao");
		    }
		});
		
		like3.addItemListener(new ItemListener(){
		    @Override 
		    public void itemStateChanged(ItemEvent e){
		    	String key = input.getText();
		    	likeDB.changeLikes(user, key, "jinshan");
		    }
		});
		
		
		Search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String key = input.getText();
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
	
	
}
