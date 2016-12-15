package src.Translate;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JTextArea;

public class Translation {
	public String word=null;
	public String phonetic=null;
	public String enPhonetic=null;
	public String amPhonetic=null;
	public Vector<Definition> trans=new Vector<Definition>();
	public Vector<String> sen=new Vector<String>();
	public Vector<String> other=new Vector<String>();
	
	public void print(JTextArea out){
		out.setText("");
		out.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		
		if(phonetic!=null){
			out.append("\n“Ù±Í: /"+phonetic+"/\n");
		}
		if(enPhonetic!=null){	
			out.append("\n”¢“Ù: /"+enPhonetic+"/\t");
		}
		if(amPhonetic!=null){
			out.append("√¿“Ù: /"+amPhonetic+"/\n");
		}
		
		out.append("\nTranslation\n\n");
		
		for(int i=0;i<trans.size();i++){
			out.append("["+trans.get(i).characteristic+"] ");
			out.append(trans.get(i).definitions+"\n");
		}
		
		if(sen.size()>0){
			out.append("\nSentences\n\n");
			for(int i=0;i<sen.size();i++){
				out.append(sen.get(i)+"\n\n");
			}
		}
		
		if(other.size()>0){
			out.append("\nExtend\n\n");
			for(int i=0;i<other.size();i++){
				out.append(other.get(i)+'\n');
			}
		}
	}
}
