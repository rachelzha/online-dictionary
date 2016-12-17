package src.Interface.panel;
import java.awt.*;
import javax.swing.*;

import src.Interface.tool.ImagePanel;
import src.Interface.tool.drawComponent;

public class TextPanel {
	JPanel MyPanel = new JPanel();
	
	public JPanel Left = new JPanel();
	public JCheckBox bing = new JCheckBox();//click to see baidu translation
	public JCheckBox youdao = new JCheckBox();//click to see youdao translation
	public JCheckBox jinshan = new JCheckBox();//click to see jinshan translation
	public ButtonGroup bookmark = new ButtonGroup(); 
	
	public JPanel Center =new JPanel();
	public JPanel Above = new JPanel();
	public JLabel tranword = new JLabel("Word");//output the word you translate
	public JButton share = new JButton(); //share
	public JCheckBox like = new JCheckBox();//like
	public JTextArea Out= new JTextArea("",12,35);
	public JScrollPane text=new JScrollPane(Out);//translation
	
	public JPanel Right = new JPanel();
	public JLabel Daily = new JLabel("Daily Sentence");//no edit
	public ImagePanel cartoon = new ImagePanel();
	public JTextArea sen= new JTextArea("",23,18);
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public TextPanel(){
		drawLeft();
		drawRight();
		drawCenter("blue"); 
	
		MyPanel.setLayout(new BorderLayout(10,10));  
	
		MyPanel.add(Left,BorderLayout.WEST);
		MyPanel.add(Center,BorderLayout.CENTER);
		MyPanel.add(Right,BorderLayout.EAST);
	}
	
	//left layout
	public void drawLeft(){
		youdao.setSelected(true);
		
		GridLayout gridLayout = new GridLayout(3,1,5,10);  
		Left.setLayout(gridLayout);
		
		//draw
		String bingfile1="image/mark/Bingtag.png";
		String youdaofile1="image/mark/Youdaotag.png";
		String jinshanfile1="image/mark/Jinshantag.png";
		String bingfile2="image/mark/Bingtaged.png";
		String youdaofile2="image/mark/Youdaotaged.png";
		String jinshanfile2="image/mark/Jinshantaged.png";
		drawComponent.drawCheckBox(bingfile1, bingfile2, 82, 40,bing);
		drawComponent.drawCheckBox(youdaofile1, youdaofile2, 82, 40, youdao);
		drawComponent.drawCheckBox(jinshanfile1, jinshanfile2, 82, 40, jinshan);
		
	    bookmark.add(youdao);
	    bookmark.add(jinshan);
	    bookmark.add(bing);
	    
		Left.add(bing);
		Left.add(youdao);
		Left.add(jinshan);
	}
	
	//right layout
	public void drawRight(){ 
		GridLayout gridLayout = new GridLayout(2,1,5,10);  
		Right.setLayout(gridLayout);
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		pan1.setLayout(new BorderLayout(5,5));
		pan2.setLayout(new BorderLayout(5,5));
		
		sen.setBackground(null);
		sen.setBorder(null);
		sen.setEditable(false);
		sen.setLineWrap(true);        //激活自动换行功能 
		sen.setWrapStyleWord(true);            // 激活断行不断字功能
		
		pan1.setBackground(null);
		pan1.add(Daily,BorderLayout.NORTH);
		pan1.add(cartoon,BorderLayout.CENTER);
		pan2.setBackground(null);
		pan2.add(sen,BorderLayout.CENTER);

		Right.add(pan1);
		Right.add(pan2);
	}
	
	//center layout
	public void drawCenter(String color){
		Above.removeAll();
		Center.removeAll();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT ,5, 5);  
		Above.setLayout(flowLayout);
		String likefile1="image/"+color+"/like.png";
		String likefile2="image/"+color+"/liked.png";
		String sharefile1="image/"+color+"/share.png";
		String sharefile2="image/grey/share.png";
		drawComponent.drawCheckBox(likefile1,likefile2, 20, 20, like);
		drawComponent.drawButton(sharefile1,sharefile2, 20, 20, share);
	    Above.add(tranword);
	 //   Above.add(like);
	 //   Above.add(share);
	    Above.setBackground(Color.WHITE);
	    text.setBackground(Color.WHITE);
	    text.setBorder(null);
	    Out.setEditable(false);  
	    Out.setLineWrap(true);        //激活自动换行功能 
		Out.setWrapStyleWord(true);   // 激活断行不断字功能
		Center.setLayout (new BorderLayout(0,0));
		Center.add(Above,BorderLayout.NORTH);
		Center.add(text,BorderLayout.CENTER);
		Above.revalidate();
		Above.repaint();
		Center.revalidate();
		Center.repaint();
	}
	
	//set background color
	public void setColor(String color){
		Color bg=null;
		switch(color){
		case "green":bg=new Color(173,255,47,255);break;
		case "yellow":bg=new Color(255,255,100,255);break;
		case "blue":bg=new Color(135,206,235,255);break;
		case "darkblue":bg=new Color(0,100,139,255);break;
		case "pink":bg=new Color(218,112,214,255);break;
		case "black":bg=new Color(130,130,130,255);break;
		}
		Left.setBackground(bg);
		Center.setBackground(bg);
		Right.setBackground(bg);
		MyPanel.setBackground(bg);
		MyPanel.revalidate();
		MyPanel.repaint();
	}
	
}
