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
		String key = searchpanel.input.getSelectedItem().toString();
		//String key=searchpanel.input.getText();
		if(key==null||key.length()==0)
			return;
		if(textpanel.bing.isSelected()){
			BingTranslate B = new BingTranslate();
			String text = B.Translation(key);
			textpanel.Out.setText(text);
		}
		if(textpanel.youdao.isSelected()){
			YoudaoTranslate Y = new YoudaoTranslate();
			String text = Y.Translation(key);
			textpanel.Out.setText(text);
		}
		if(textpanel.jinshan.isSelected()){
			JinshanTranslate J = new JinshanTranslate();
			String text = J.Translate(key);
			textpanel.Out.setText(text);
		}	
	
	}
	
	
	public void handleLike(){
		//Socket socket = (Socket)obj[0];
		SearchPanel searchpanel = (SearchPanel)obj[0];
		TextPanel textpanel = (TextPanel) obj[1];
		String key = searchpanel.input.getSelectedItem().toString();
		//String key=searchpanel.input.getText();
		DataOutputStream toServer;
		try{
			//create an output stream to send data to the server
			toServer=new DataOutputStream(socket.getOutputStream());
			toServer.writeInt(7);
			toServer.writeUTF(key);
			if(textpanel.bing.isSelected()){
				if((int)obj[2]==1)
					textpanel.like.setSelected(true);
				else
					textpanel.like.setSelected(false);
				toServer.writeUTF("baidu");
			}
			else if(textpanel.youdao.isSelected()){
				if((int)obj[3]==1)
					textpanel.like.setSelected(true);
				else
					textpanel.like.setSelected(false);
				toServer.writeUTF("youdao");
			}
			else if(textpanel.jinshan.isSelected()){
				if((int)obj[4]==1)
					textpanel.like.setSelected(true);
				else
					textpanel.like.setSelected(false);
				toServer.writeUTF("jinshan");
			}
		}
		catch (IOException ex){
			System.err.println(ex);
			System.err.println("Fail!");
		}
	}
	
	public void handleTakeword(){
		
	}
	
	public void resetBookMark(){
		TextPanel textpanel = (TextPanel)obj[3];
		ChoosePanel choosepanel = (ChoosePanel)obj[4];
		int a = (int)obj[0];
		int b = (int)obj[1];
		int c = (int)obj[2];
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
}
