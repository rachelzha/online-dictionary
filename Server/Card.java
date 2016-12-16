package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import src.Translate.Translation;

public class Card implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public transient BufferedImage image=null;
	
	public Card(){
		File picture=new File("image/template/wallpaper.jpg");
		
		try {
	     	   image = ImageIO.read(picture);
	           if (image == null) {  
	         	   JOptionPane.showMessageDialog(null,"The file could not be opened , it is not an image");
	                return ;
	           }
	        } catch (IOException e1) {
	     	   JOptionPane.showMessageDialog(null,"The file could not be opened , an error occurred.");
	            return ;
	        }
	}
	
	public Card(String name){
		File picture=new File(name);
		
		BufferedImage temp=null;
        try {
     	   temp = ImageIO.read(picture);
           if (temp == null) {  
         	   JOptionPane.showMessageDialog(null,"The file could not be opened , it is not an image");
                return ;
           }
        } catch (IOException e1) {
     	   JOptionPane.showMessageDialog(null,"The file could not be opened , an error occurred.");
            return ;
        }
         
        image=temp;
	}
	
	private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();
        //write buff with imageIO to out
        ImageIO.write(image, "JPEG", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        //read buff with imageIO from in
        image=ImageIO.read(in);
    } 
    
    //image!=null
    public boolean validable(){
    	if(image==null)return false;
    	else return true;
    }
    
    
    
    public void draw(Translation t,boolean b){
    	Graphics g = image.getGraphics();

        if( b == false){
     	   g.setColor(new Color(102,102,102)); 
        }

        //word
        Font mFont = new Font("Arial",Font.BOLD,30);
        g.setFont(mFont);
        int wordX=50;
        int wordY=50;
        g.drawString(t.word, wordX, wordY); 
        
        
        int width=image.getWidth();//图片宽度
        int height=image.getHeight();
    	int textWidth=width-50;
    	
        //definition
        mFont = new Font("宋体",Font.PLAIN,20);
        g.setFont(mFont);
        int defX=50;
        int defY=90;
        if(height<=defY)return;
        
        FontMetrics fm = g.getFontMetrics(mFont);  
        int fontHeight=fm.getHeight();
        
        int offset=defX;
       
        if(t.trans.size()>0){
     	   g.drawString(t.trans.get(0).characteristic, defX, defY);
     	   offset+=40;
     	   
     	   String text=t.trans.get(0).definitions;
     	   for(int i=0;i<text.length();i++){
     		   char c=text.charAt(i);
     		   int charWidth=fm.charWidth(c);
     		   
     		   if(Character.isISOControl(c)||offset>=textWidth-charWidth){
     			   offset=defX;
     			   defY+=fontHeight;
     		   }
     		   g.drawString(String.valueOf(c), offset, defY);
     		   offset+=charWidth;
     	   }     	   
        }
        
        //sentence
        int senX=defX;
        int senY=defY+30;
        if(height<=senY)return;
        offset=senX;
        if(t.sen.size()>0){
        	String text=t.sen.get(0);
      	   	for(int i=0;i<text.length();i++){
      		   char c=text.charAt(i);
      		   int charWidth=fm.charWidth(c);
      		   
      		   if(Character.isISOControl(c)||offset>=textWidth-charWidth){
      			   offset=senX;
      			   senY+=fontHeight;
      		   }
      		   g.drawString(String.valueOf(c), offset, senY);
      		   offset+=charWidth;
      	   }   
        }
    }
    
    
    //在图片上写字
    public void draw(Translation t, String name , boolean b ){
    		File background=new File(name);
    		
    		BufferedImage temp=null;
               try {
            	   temp = ImageIO.read(background);
                   if (temp == null) {  
                	   JOptionPane.showMessageDialog(null,"The file could not be opened , it is not an image");
                       return ;
                   }
               } catch (IOException e1) {
            	   JOptionPane.showMessageDialog(null,"The file could not be opened , an error occurred.");
                   return ;
               }
                
               image=temp;
               
               draw(t,b);
    }

    //保存图片到本地
    public String saveCard(String path){
    	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	String datetime = tempDate.format(new java.util.Date());
    	
    	//os
    	String divide;
    	if(System.getProperty("os.name")=="Windows")divide="\\";
    	else divide="/";
    	
    	String filename=path+divide+datetime+".jpg";
    	
    	File file = new File(filename);
    	
    	try {
            ImageIO.write(image, "JPEG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return filename;
    }
}
