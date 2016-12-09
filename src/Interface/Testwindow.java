package src.Interface;

import java.awt.*;
import javax.swing.*;


public class Testwindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LoginPanel loginpanel = new LoginPanel();
	SearchPanel searchpanel = new SearchPanel();
	ChoosePanel choosepanel = new ChoosePanel();
	TextPanel textpanel = new TextPanel();
	ButtonListener buttonlistener = new ButtonListener(loginpanel,searchpanel,choosepanel,textpanel);
	CheckBoxListener checkboxlistener = new CheckBoxListener(loginpanel,searchpanel,choosepanel,textpanel);
	
	public Testwindow(){
		JPanel panel1=loginpanel.getPanel();
        JPanel panel2=searchpanel.getPanel();
        JPanel panel3=choosepanel.getPanel();
        JPanel panel4=textpanel.getPanel();
        
        JPanel Pan1 = new JPanel();
		GridLayout gridLayout = new GridLayout(3, 1);  
        Pan1.setLayout(gridLayout); 
        Pan1.add(panel1);
        Pan1.add(panel2);
        Pan1.add(panel3);

        add(panel4,BorderLayout.CENTER);
        add(Pan1,BorderLayout.NORTH);
        

        buttonlistener.setType(1);
        searchpanel.Search.addActionListener(buttonlistener);
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
