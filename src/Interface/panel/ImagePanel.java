package src.Interface.panel;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		super.paint(g);
// ImageIcon icon = new ImageIcon("D:\\1.jpg");
		ImageIcon icon = new ImageIcon("image/GIF/3.gif");
		g.drawImage(icon.getImage(), 0, 0, 120, 120, this);
	}
}