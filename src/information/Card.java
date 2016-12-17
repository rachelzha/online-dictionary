package src.information;

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
    
    
    
    public void draw(Translation t,Color color){
    	Graphics g = image.getGraphics();

     	g.setColor(color); 

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
        mFont = new Font("黑体",Font.PLAIN,15);
        g.setFont(mFont);
        int defX=50;
        int defY=90;
        
        FontMetrics fm = g.getFontMetrics(mFont);  
        int fontHeight=fm.getHeight();
        
        int offset=defX;
       
        if(t.trans.size()>0){
        	for(int j=0;j<t.trans.size();j++){
        		if(height<=defY)return;
        	
        		//characteristic
	     	   g.drawString(t.trans.get(j).characteristic, defX, defY);
	     	   int cw=0;
	     	   for(int i=0;i<t.trans.get(j).characteristic.length();i++){
	     		   cw+=fm.charWidth(t.trans.get(j).characteristic.charAt(i));
	     	   }
	     	   offset+=cw;
	     	   
	     	   //definitions
	     	   String text=t.trans.get(j).definitions;
	     	   for(int i=0;i<text.length();i++){
	     		   char c=text.charAt(i);
	     		   int charWidth=fm.charWidth(c);
	     		   
	     		   //另起一行
	     		   if(Character.isISOControl(c)||offset>=textWidth-charWidth){
	     			   offset=defX;
	     			   defY+=fontHeight;
	     		   }
	     		   g.drawString(String.valueOf(c), offset, defY);
	     		   offset+=charWidth;
	     	   }
	     	   //另起一行
	     	   offset=defX;
	     	   defY+=fontHeight;
        	}
        }
        
        //sentence
        int senX=defX;
        int senY=defY+30;
        if(height<=senY)return;
        offset=senX;
        if(t.sen.size()>0){
        	//for(int j=0;j<t.sen.size();j++){
        		
	        	String text=t.sen.get(0);
	      	   	for(int i=0;i<text.length();i++){
	      		   char c=text.charAt(i);
	      		   int charWidth=fm.charWidth(c);
	      		   
	      		   //另起一行
	      		   if(Character.isISOControl(c)||offset>=textWidth-charWidth){
	      			   offset=senX;
	      			   senY+=fontHeight;
	      		   }
	      		   g.drawString(String.valueOf(c), offset, senY);
	      		   offset+=charWidth;
	      	   }
	      	   //另起一行
	      	   //offset=senX;
	      	   //senY+=fontHeight;
        	//}
        }
    }
    
    
    //在图片上写字
    public void draw(Translation t, String name , Color color){
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
               
               draw(t,color);
    }

    //保存图片到本地
    public String saveCard(String path){
    	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");  
    	String datetime = tempDate.format(new java.util.Date());
    	
    	//os
    	//String divide="/";
    	
    	
    	String filename=path+"/"+datetime+".jpg";
    	
    	File file = new File(filename);
    	
    	try {
            ImageIO.write(image, "JPEG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return filename;
    }
}
