package src.Interface;
import java.awt.*;
import javax.swing.*;

public class TextPanel {
	JPanel MyPanel = new JPanel();
	drawComponent draw=new drawComponent();
	
	JPanel Left = new JPanel();
	JButton baidu = new JButton();//click to see baidu translation
	JButton youdao = new JButton();//click to see youdao translation
	JButton jinshan = new JButton();//click to see jinshan translation
	
	JPanel Center =new JPanel();
	JLabel tranword = new JLabel("Word");//output the word you translate
	JButton share = new JButton(); //share
	JButton like = new JButton(); //like
	JTextArea Out= new JTextArea("",12,35);
	JScrollPane text=new JScrollPane(Out);//translation
	
	JPanel Right = new JPanel();
	JLabel Daily = new JLabel("Daily Sentence");//no edit
	JLabel cartoon = new JLabel();
	JTextArea sen= new JTextArea("",15,10);
//	JScrollPane sentence=new JScrollPane(sen);//daily sentence
	JCheckBox takeword = new JCheckBox("È¡´Ê");
	
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
		String baidufile="image/tag.png";
		String youdaofile="image/tag.png";
		String jinshanfile="image/tag.png";
		draw.drawButton(baidufile, null, 30, 30, baidu);
	    draw.drawButton(youdaofile, null, 30, 30, youdao);
	    draw.drawButton(jinshanfile, null, 30, 30, jinshan);
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
		String likefile="image/"+color+"/like.png";
		String sharefile="image/"+color+"/share.png";
		draw.drawButton(likefile, null, 20, 20, like);
	    draw.drawButton(sharefile, null, 20, 20, share);
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
