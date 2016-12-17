package src.Interface.tool;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.*;

public class drawComponent {

	public static void drawLabel(String file,int width,int height,JLabel label){
		ImageIcon icon = new ImageIcon(file);  
        icon.setImage(icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
        label.setIcon(icon);
	}
	
	public static void drawButton(String file1,String file2,int width,int height,JButton button){
		ImageIcon icon = new ImageIcon(file1);  
        icon.setImage(icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
        button.setIcon(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(width,height)); 
        if(file2==null)
        	return;
        ImageIcon icon2 = new ImageIcon(file2);  
        icon2.setImage(icon2.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
        button.setPressedIcon(icon2 );
	}
	
	public static void drawCheckBox(String file1,String file2,int width,int height,JCheckBox box){
		ImageIcon icon1 = new ImageIcon(file1);  
        icon1.setImage(icon1.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
       
        ImageIcon icon2 = new ImageIcon(file2);  
        icon2.setImage(icon2.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
        
        box.setBorder(null);
        box.setBorderPainted(false);
        box.setContentAreaFilled(false);
        
        box.setIcon(icon1);
        box.setSelectedIcon(icon2);    
      
	}
	
}
