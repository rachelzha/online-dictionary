package src.Interface;

import java.awt.*;
import javax.swing.*;

public class Testwindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel Pan1=new JPanel();
	JPanel panel1 = new JPanel();
	JPanel panel2=new JPanel();
	JPanel panel3=new JPanel();
	JPanel panel4=new JPanel();
	
	public Testwindow(){
		
		panel1=new LoginPanel().getPanel();
        panel2=new SearchPanel().getPanel();
        panel3=new ChoosePanel().getPanel();
        panel4=new TextPanel().getPanel();
        
		GridLayout gridLayout = new GridLayout(3, 1);  
        Pan1.setLayout(gridLayout); 
        Pan1.add(panel1);
        Pan1.add(panel2);
        Pan1.add(panel3);

        add(panel4,BorderLayout.CENTER);
        add(Pan1,BorderLayout.NORTH);
	}
	
	public static void main(String[] args){
		Testwindow win=new Testwindow();
		//win.setTitle("Dictionary");
		win.setLocation(200,100);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(700, 500);
		win.setVisible(true);
	}
}
