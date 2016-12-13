package Server;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class ScreenShots implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BufferedImage screenshot;
	
	public ByteArrayOutputStream snapShot(){
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 

	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	           
	    try {
	    	// 拷贝屏幕到一个BufferedImage对象screenshot
	        screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0,(int) d.getWidth(), (int) d.getHeight()));                            
	            
	        // 将screenshot对象写入输出流
	        ImageIO.write(screenshot, "jpg", out);
	                    
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return out;
	}
}
