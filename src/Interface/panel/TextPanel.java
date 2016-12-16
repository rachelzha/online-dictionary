package src.Interface.panel;
import java.awt.*;
import javax.swing.*;

public class TextPanel {
	JPanel MyPanel = new JPanel();
	drawComponent draw=new drawComponent();
	
	public JPanel Left = new JPanel();
	public JCheckBox bing = new JCheckBox();//click to see baidu translation
	public JCheckBox youdao = new JCheckBox();//click to see youdao translation
	public JCheckBox jinshan = new JCheckBox();//click to see jinshan translation
	public ButtonGroup bookmark = new ButtonGroup(); 
	
	public JPanel Center =new JPanel();
	public JPanel Above = new JPanel();
	public JLabel tranword = new JLabel("Word");//output the word you translate
	public JButton share = new JButton(); //share
	//JButton like = new JButton(); //like
	public JCheckBox like = new JCheckBox();//like
	public JTextArea Out= new JTextArea("",12,35);
	public JScrollPane text=new JScrollPane(Out);//translation
	
	public JPanel Right = new JPanel();
	public JLabel Daily = new JLabel("Daily Sentence");//no edit
	public ImagePanel cartoon = new ImagePanel();
	public JTextArea sen= new JTextArea("",23,18);
	//JScrollPane sentence=new JScrollPane(sen);//daily sentence
	//public JCheckBox takeword = new JCheckBox("取词");
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public TextPanel(){
		drawLeft();
		drawRight();
		drawCenter("blue"); 
	//	Panel pan = new Panel();
	//	FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,5, 5);  
	//	pan.setLayout(flowLayout);
	//	pan.add(Left);
	///	pan.add(Center);
	
		MyPanel.setLayout(new BorderLayout(10,10));  
	
		MyPanel.add(Left,BorderLayout.WEST);
		MyPanel.add(Center,BorderLayout.CENTER);
		MyPanel.add(Right,BorderLayout.EAST);
	}
	
	public void drawLeft(){
		GridLayout gridLayout = new GridLayout(3,1,5,10);  
		Left.setLayout(gridLayout);
		String bingfile1="image/mark/Bingtag.png";
		String youdaofile1="image/mark/Youdaotag.png";
		String jinshanfile1="image/mark/Jinshantag.png";
		String bingfile2="image/mark/Bingtaged.png";
		String youdaofile2="image/mark/Youdaotaged.png";
		String jinshanfile2="image/mark/Jinshantaged.png";
		
		draw.drawCheckBox(bingfile1, bingfile2, 82, 40, bing);
	    draw.drawCheckBox(youdaofile1, youdaofile2, 82, 40, youdao);
	    draw.drawCheckBox(jinshanfile1, jinshanfile2, 82, 40, jinshan);
	    bookmark.add(bing);
	    bookmark.add(youdao);
	    bookmark.add(jinshan);
	    bing.setSelected(true);
		Left.add(bing);
		Left.add(youdao);
		Left.add(jinshan);
	}
	
	public void drawRight(){ 
		GridLayout gridLayout = new GridLayout(2,1,5,10);  
		Right.setLayout(gridLayout);
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		pan1.setLayout(new BorderLayout(5,5));
		pan2.setLayout(new BorderLayout(5,5));
		
	//	String cartoonfile="image/green/color.png";
		//draw.drawLabel(cartoonfile, 200, 200, cartoon);
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
	//	pan2.add(takeword,BorderLayout.SOUTH);
	//	takeword.setBackground(null);
		Right.add(pan1);
		Right.add(pan2);
	}
	
	public void drawCenter(String color){
		Above.removeAll();
		Center.removeAll();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT ,5, 5);  
		Above.setLayout(flowLayout);
		String likefile1="image/"+color+"/like.png";
		String likefile2="image/"+color+"/liked.png";
		String sharefile1="image/"+color+"/share.png";
		String sharefile2="image/grey/share.png";
		draw.drawCheckBox(likefile1,likefile2, 20, 20, like);
		draw.drawButton(sharefile1,sharefile2, 20, 20, share);
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
	
	public void setColor(String color){
		Color bg=null;
		switch(color){
		case "green":bg=Color.green;break;
		case "yellow":bg=Color.yellow;break;
		case "blue":bg=new Color(135,206,235,255);break;
		case "darkblue":bg=new Color(0,0,139,255);break;
		case "pink":bg=new Color(218,112,214,255);break;
		case "black":bg=Color.darkGray;break;
		}
		Left.setBackground(bg);
		Center.setBackground(bg);
		Right.setBackground(bg);
		MyPanel.setBackground(bg);
		MyPanel.revalidate();
		MyPanel.repaint();
	}
	
}
