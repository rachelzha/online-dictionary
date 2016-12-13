package src.Interface;
import java.awt.FlowLayout;

import javax.swing.*;

public class ChoosePanel {
	JPanel MyPanel = new JPanel();
	drawComponent draw=new drawComponent();
	
	public JCheckBox bing=new JCheckBox("Bing");
	//JLabel baidulabel=new JLabel();
	public JCheckBox youdao=new JCheckBox("Youdao");
	//JLabel youdaolabel=new JLabel();
	public JCheckBox jinshan=new JCheckBox("iCIBA");
	//JLabel jinshanlabel=new JLabel();
	
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
}
