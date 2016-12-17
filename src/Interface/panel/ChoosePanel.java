package src.Interface.panel;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

public class ChoosePanel {
	JPanel MyPanel = new JPanel();
	
	public JCheckBox bing=new JCheckBox("Bing");
	public JCheckBox youdao=new JCheckBox("Youdao");
	public JCheckBox jinshan=new JCheckBox("iCIBA");
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public ChoosePanel(){
		bing.setSelected(true);
		youdao.setSelected(true);
		jinshan.setSelected(true);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
	    MyPanel.setLayout(flowLayout);  
	    MyPanel.add(bing);  
	    MyPanel.add(youdao);
	    MyPanel.add(jinshan); 
	}
	
	//set background color
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
		bing.setBackground(bg);
		youdao.setBackground(bg);
		jinshan.setBackground(bg);
		MyPanel.setBackground(bg);
		MyPanel.revalidate();
		MyPanel.repaint();
	}
}
