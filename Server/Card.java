package Server;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Card {
	BufferedImage image;
	
	public static void main(String[] args){
		Card card=new Card("hello");
		
		card.savePhoto();
	}
	
	Card(String content){
		image=null;
		//background
		File f = new File("wallpaper.jpg");
		
		draw(content,f,true);
	}
	
	 //直接在一张已有的图片上写字，可指定文字颜色。如果背景图片参数为空或者""，则写张白图
    public void draw(String content, File background , boolean b ){
    			int lineLength=content.length();
    	
                if( background.equals("") || background == null){
                    double h = 256/16;
                    double w = 85/10;
                    int width=(int) (w*lineLength)+ 40 , height=(int) h;
                    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                }else{
                    try {
                        image = ImageIO.read(background);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                Graphics g = image.getGraphics();

                if( b == false){
                g.setColor(new Color(102,102,102));  //设字体为黑色,否则就是白色
                }

                Font mFont = new Font("宋体",Font.PLAIN,100);
                g.setFont(mFont);
                
                g.drawString(content, 300, 150);
  /*              int k = 0;
                Iterator<String> it = list.iterator();
                while(it.hasNext()){
                    g.drawString(it.next(), 30, 15 + 20*k);
                    ++k;
                }*/         
    }
    
    public void savePhoto(){
    	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	String datetime = tempDate.format(new java.util.Date());
    	String filename=datetime+".jpg";
    	
    	File file = new File(filename);
    	
    	try {
            ImageIO.write(image, "JPEG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
