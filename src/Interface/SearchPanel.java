package src.Interface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;


public class SearchPanel {
	JPanel MyPanel = new JPanel();
	drawComponent draw=new drawComponent();
	
	JPanel Left=new JPanel();
	public JButton Prev=new JButton();//find prevoius word
	public JButton Next=new JButton(); //find next word
	JLabel Line1=new JLabel();//parting line ,no edit
	
	JPanel Center=new JPanel();
	JLabel En=new JLabel();//english,no edit
	JLabel Line2=new JLabel();//parting line , no edit
	public JTextField input=new JTextField(30);//input word
	//public JComboBox<String> Input=new JComboBox<String>();
	//public AutoComboBox input=new AutoComboBox();
	DefaultComboBoxModel<String>   model   =   new   DefaultComboBoxModel<String>(); 
	//public JButton pulldown=new JButton();///open or close related word list
	
	JPanel Right=new JPanel();
	JLabel Line3=new JLabel();//parting line , no edit
	public JButton Search=new JButton();//click to search

	public JPanel getPanel(){
		return MyPanel;
	}
	
	public SearchPanel(){
		drawLeft("blue");
		drawCenter("blue");
		drawRight("blue");
		MyPanel.setBackground(Color.WHITE);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,5, 5);  
		MyPanel.setLayout(flowLayout);  
		MyPanel.add(Left);
		MyPanel.add(Center);
		MyPanel.add(Right);
	}
	
	public void drawLeft(String color){
		Left.setBackground(Color.WHITE);
		String prevfile1="image/"+color+"/front.png";
		String prevfile2="image/grey/front.png";
		String nextfile1="image/"+color+"/back.png";
		String nextfile2="image/grey/back.png";
		String partline="image/"+color+"/line.png";
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,10, 5);  
        Left.setLayout(flowLayout);  
        draw.drawButton(prevfile1, prevfile2, 20, 20, Prev);
        draw.drawButton(nextfile1, nextfile2, 20, 20, Next);
        draw.drawLabel(partline, 40, 40, Line1);
        Left.add(Prev);  
        Left.add(Next);
        Left.add(Line1);
	}
	
	public void drawCenter(String color){
       	input.setEditable(true);
		input.setPreferredSize(new Dimension(300,25));
		
		Center.setBackground(Color.WHITE);
		String Enfile="image/"+color+"/en.png";
		String partline="image/"+color+"/line.png";
		String downfile1="image/"+color+"/down.png";
		String downfile2="image/grey/down.png";
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,5, 5);  
        Center.setLayout(flowLayout);
        draw.drawLabel(Enfile, 30, 30, En);
        draw.drawLabel(partline, 30, 30, Line2);
      //  draw.drawButton(downfile1, downfile2, 15, 15, pulldown);
     //   input.setBorder(BorderFactory.createEtchedBorder());
        Center.add(En);
        Center.add(Line2);
        Center.add(input);
      //  Center.add(Input);
      //  Center.add(input);
     //   Center.add(pulldown);
	}
	
	public void drawRight(String color){
		Right.setBackground(Color.WHITE);
		String partline="image/"+color+"/line.png";
		String searchfile1="image/"+color+"/search.png";
		String searchfile2="image/grey/search.png";
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER ,5, 5);  
		Right.setLayout(flowLayout);
		draw.drawButton(searchfile1, searchfile2, 30, 30, Search);
		draw.drawLabel(partline, 40, 40, Line3);
		Right.add(Line3);
		Right.add(Search);
	}
}
