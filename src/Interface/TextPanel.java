package src.Interface;
import java.awt.*;
import javax.swing.*;

public class TextPanel {
	JPanel MyPanel = new JPanel();
	drawComponent draw=new drawComponent();
	
	JPanel Left = new JPanel();
	public JCheckBox baidu = new JCheckBox();//click to see baidu translation
	public JCheckBox youdao = new JCheckBox();//click to see youdao translation
	public JCheckBox jinshan = new JCheckBox();//click to see jinshan translation
	ButtonGroup bookmark = new ButtonGroup(); 
	
	JPanel Center =new JPanel();
	public JLabel tranword = new JLabel("Word");//output the word you translate
	public JButton share = new JButton(); //share
	//JButton like = new JButton(); //like
	public JCheckBox like = new JCheckBox();//like
	public JTextArea Out= new JTextArea("",12,35);
	public JScrollPane text=new JScrollPane(Out);//translation
	
	JPanel Right = new JPanel();
	JLabel Daily = new JLabel("Daily Sentence");//no edit
	public JLabel cartoon = new JLabel();
	public JTextArea sen= new JTextArea("",15,10);
//	JScrollPane sentence=new JScrollPane(sen);//daily sentence
	public JCheckBox takeword = new JCheckBox("È¡´Ê");
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public TextPanel(){
		drawLeft();
		drawRight();
		drawCenter("blue"); 
		JPanel pan = new JPanel();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,5, 5);  
		pan.setLayout(flowLayout);
		pan.add(Left);
		pan.add(Center);
	
		MyPanel.setLayout(new BorderLayout(10,10));  
	
	//	MyPanel.add(useless,BorderLayout.WEST);
		MyPanel.add(pan,BorderLayout.CENTER);
		MyPanel.add(Right,BorderLayout.EAST);
	}
	
	public void drawLeft(){
		GridLayout gridLayout = new GridLayout(3,1,5,10);  
		Left.setLayout(gridLayout);
		String baidufile1="image/tag.png";
		String youdaofile1="image/tag.png";
		String jinshanfile1="image/tag.png";
		String baidufile2="image/taged.png";
		String youdaofile2="image/taged.png";
		String jinshanfile2="image/taged.png";
		
		draw.drawCheckBox(baidufile1, baidufile2, 30, 30, baidu);
	    draw.drawCheckBox(youdaofile1, youdaofile2, 30, 30, youdao);
	    draw.drawCheckBox(jinshanfile1, jinshanfile2, 30, 30, jinshan);
	    bookmark.add(baidu);
	    bookmark.add(youdao);
	    bookmark.add(jinshan);
		Left.add(baidu);
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
		
		String cartoonfile="image/bd_logo1_31bdc765.png";
		draw.drawLabel(cartoonfile, 200, 120, cartoon);
		sen.setBorder(null);
		
		pan1.add(Daily,BorderLayout.NORTH);
		pan1.add(cartoon,BorderLayout.CENTER);
		pan2.add(sen,BorderLayout.CENTER);
		pan2.add(takeword,BorderLayout.SOUTH);
		
		Right.add(pan1);
		Right.add(pan2);
	}
	
	public void drawCenter(String color){
		JPanel Above = new JPanel();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT ,5, 5);  
		Above.setLayout(flowLayout);
		String likefile1="image/"+color+"/like.png";
		String likefile2="image/"+color+"/liked.png";
		String sharefile1="image/"+color+"/share.png";
		String sharefile2="image/grey/share.png";
		draw.drawCheckBox(likefile1,likefile2, 20, 20, like);
		draw.drawButton(sharefile1,sharefile2, 20, 20, share);
	    Above.add(tranword);
	    Above.add(like);
	    Above.add(share);
	    Above.setBackground(Color.WHITE);
	    text.setBackground(Color.WHITE);
	    text.setBorder(null);
		Center.setLayout (new BorderLayout(0,0));
		Center.add(Above,BorderLayout.NORTH);
		Center.add(text,BorderLayout.CENTER);
	}
	
}
