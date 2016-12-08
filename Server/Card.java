package Server;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Card {
	int width = 400;  
	int height = 300;
	BufferedImage image;
	
	
	Card(String word){
		File file=new File("wallpaper.jpg");
		
		Font font=new Font("Serif", Font.BOLD,10);
		//创建一个画布
		image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//获取画布的画笔
		Graphics2D g2d=(Graphics2D) image.getGraphics();
		
		//透明
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);  
		g2d.dispose();  
		g2d = image.createGraphics();
		
		//画图
		g2d.setPaint(new Color(255,0,0));  
		//g2d.setStroke(new BasicStroke(1));
		
		FontRenderContext context = g2d.getFontRenderContext();   
        Rectangle2D bounds = font.getStringBounds(word, context);   
        double x = (width - bounds.getWidth()) / 2;   
        double y = (height - bounds.getHeight()) / 2;   
        double ascent = -bounds.getY();   
        double baseY = y + ascent;   
           
        g2d.drawString(word, (int)x, (int)baseY);   
 
        //释放对象  
        g2d.dispose();
        
        try {
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("生成图片出错........");
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args){
		new Card("Hello");
	}
}
