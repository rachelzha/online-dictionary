package src.Interface.listener;
import java.awt.GridLayout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import src.Interface.panel.ChoosePanel;
import src.Interface.panel.SearchPanel;
import src.Interface.panel.TextPanel;
import src.Translate.BaiduTranslate;
import src.Translate.BingTranslate;
import src.Translate.JinshanTranslate;
import src.Translate.Translation;
import src.Translate.YoudaoTranslate;
import src.userLogin.UserState;


public class CheckBoxListener implements ItemListener{
	Object []obj;
	private int type;
	private UserState user;
	private Socket socket;
	
	public CheckBoxListener(Socket socket,int type,UserState user,Object []obj){
		this.obj=obj;
		this.type=type;
		this.user=user;
		
		this.socket=socket;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		switch(type){
		case 1:handleChoose();break;//choosepanel.baidu,youdao,jinshan
		case 4:handlebookmark();break;//bookmark.baidu
		case 7:handleLike();break;//textpanel.like
		case 8:handleTakeword();break;//textpanel.takeword
		}
	}
	
	public void handleChoose(){
		resetBookMark();
	}
	
	public void handlebookmark(){
		SearchPanel searchpanel = (SearchPanel)obj[0];
		TextPanel textpanel = (TextPanel)obj[1];
		Info info =(Info)obj[2];
		//String key = searchpanel.input.getSelectedItem().toString();
		String key=searchpanel.input.getText();
		if(key==null||key.length()==0)
			return;
		if(textpanel.bing.isSelected()){
			//System.out.println(info.getjudgebing());///////
			if(info.getjudgebing()==1)
				textpanel.like.setSelected(true);
			else if(info.getjudgebing()==-1)
				textpanel.like.setSelected(false);
			BingTranslate B = new BingTranslate();
			Translation trans = B.Translation(key);
			trans.print(textpanel.Out);
		}
		if(textpanel.youdao.isSelected()){
			if(info.getjudgeyoudao()==1)
				textpanel.like.setSelected(true);
			else if(info.getjudgeyoudao()==-1)
				textpanel.like.setSelected(false);
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation trans = Y.Translation(key);
			trans.print(textpanel.Out);
		}
		if(textpanel.jinshan.isSelected()){
			if(info.getjudgejinshan()==1)
				textpanel.like.setSelected(true);
			else if(info.getjudgejinshan()==-1)
				textpanel.like.setSelected(false);
			JinshanTranslate J = new JinshanTranslate();
			Translation trans = J.Translate(key);
			trans.print(textpanel.Out);
		}	
		textpanel.Above.revalidate();
		textpanel.Above.repaint();
	
	}
	
	
	public void handleLike(){

		SearchPanel searchpanel = (SearchPanel)obj[0];
		TextPanel textpanel = (TextPanel) obj[1];
		Info info = (Info)obj[2];
		//System.out.println(obj[2]+":"+obj[3]+":"+obj[4]);///////
		//String key = searchpanel.input.getSelectedItem().toString();
		String key=searchpanel.input.getText();
		DataOutputStream toServer;
		try{
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
			toServer.writeInt(8);
			toServer.writeUTF(key);
			if(textpanel.bing.isSelected()){
				toServer.writeUTF("baidu");
			}
			else if(textpanel.youdao.isSelected()){
				toServer.writeUTF("youdao");
			}
			else if(textpanel.jinshan.isSelected()){
				toServer.writeUTF("jinshan");
			}
			textpanel.Center.revalidate();
			textpanel.Center.repaint();
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	public void handleTakeword(){
		
	}
	
	public void resetBookMark(){
		TextPanel textpanel = (TextPanel)obj[1];
		ChoosePanel choosepanel = (ChoosePanel)obj[2];
		Info info = (Info)obj[0];
		int a = info.getbinglikes();
		int b = info.getyoudaolikes();
		int c = info.getjinshanlikes();
		textpanel.Left.removeAll();
		if(a>=b&&b>=c){
			textpanel.Left.add(textpanel.bing);  
			textpanel.Left.add(textpanel.youdao);  
			textpanel.Left.add(textpanel.jinshan);
		}
		else if(a>=c&&c>=b){
			textpanel.Left.add(textpanel.bing);  
			textpanel.Left.add(textpanel.jinshan);  
			textpanel.Left.add(textpanel.youdao);
		}
		else if(b>=a&&a>=c){
			textpanel.Left.add(textpanel.youdao);  
			textpanel.Left.add(textpanel.bing);  
			textpanel.Left.add(textpanel.jinshan);
		}
		else if(b>=c&&c>=a){
			textpanel.Left.add(textpanel.youdao);  
			textpanel.Left.add(textpanel.jinshan);  
			textpanel.Left.add(textpanel.bing);
		}
		else if(c>=a&&a>=b){
			textpanel.Left.add(textpanel.jinshan);  
			textpanel.Left.add(textpanel.bing);  
			textpanel.Left.add(textpanel.youdao);
		}
		else{//c>=b>=a
			textpanel.Left.add(textpanel.jinshan);  
			textpanel.Left.add(textpanel.youdao);  
			textpanel.Left.add(textpanel.bing);
		}

		int count = 3;
		if(!choosepanel.bing.isSelected()){
			textpanel.Left.remove(textpanel.bing);
			count--;
		}
		if(!choosepanel.youdao.isSelected()){
			textpanel.Left.remove(textpanel.youdao);
			count--;
		}
		if(!choosepanel.jinshan.isSelected()){
			textpanel.Left.remove(textpanel.jinshan);
			count--;
		}
		if(choosepanel.bing.isSelected())
			textpanel.bing.setSelected(true);
		else if(choosepanel.youdao.isSelected())
			textpanel.youdao.setSelected(true);
		else if(choosepanel.jinshan.isSelected())
			textpanel.jinshan.setSelected(true);
		
		textpanel.Left.setLayout(new GridLayout(count,1,5,10));
		textpanel.Left.revalidate();
		textpanel.Left.repaint();
	}
	
	public void setlike(Info info,TextPanel panel){
		if(panel.bing.isSelected()){
			if(info.getjudgebing()==-1){
				
			}
			if(info.getjudgebing()==1){
				
			}
		}
	}
}
