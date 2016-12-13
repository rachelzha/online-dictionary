package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Card implements Serializable{
	
	transient BufferedImage image;
	
	Card(){
		image=null;
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
    
    //直接在一张已有的图片上写字，可指定文字颜色。如果背景图片参数为空或者""，则写张白图
    public void draw(String content, File background , boolean b ){
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
               Graphics g = image.getGraphics();

               if( b == false){
            	   g.setColor(new Color(102,102,102));  //设字体为黑色,否则就是白色
               }

               Font mFont = new Font("宋体",Font.PLAIN,100);
               g.setFont(mFont);
                
               g.drawString(content, 300, 150); 
    }
    
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
