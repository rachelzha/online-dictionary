package src.Interface.tool;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String num;
	
	public ImagePanel(){
		super();
		int n=0;
		while(n<=0||n>12){
			n=(int) (Math.random()*12);
		}
		num=String.valueOf(n);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		String file = "image/GIF/"+num+".gif";
		ImageIcon icon = new ImageIcon(file);
		g.drawImage(icon.getImage(), 0, 0, 200, 150, this);
	}
	
}