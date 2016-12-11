package src.Interface;
import java.awt.FlowLayout;

import javax.swing.*;

public class ChoosePanel {
	JPanel MyPanel = new JPanel();
	drawComponent draw=new drawComponent();
	
	public JCheckBox baidu=new JCheckBox("baidu");
	//JLabel baidulabel=new JLabel();
	public JCheckBox youdao=new JCheckBox("youdao");
	//JLabel youdaolabel=new JLabel();
	public JCheckBox jinshan=new JCheckBox("jinshan");
	//JLabel jinshanlabel=new JLabel();
	
	public JPanel getPanel(){
		return MyPanel;
	}
	
	public ChoosePanel(){
		baidu.setSelected(true);
		youdao.setSelected(true);
		jinshan.setSelected(true);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER , 30 , 5);  
	    MyPanel.setLayout(flowLayout);  
	    MyPanel.add(baidu);  
	    MyPanel.add(youdao);
	    MyPanel.add(jinshan); 
	}
}
