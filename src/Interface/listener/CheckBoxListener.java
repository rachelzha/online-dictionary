package src.Interface.listener;
import java.awt.GridLayout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.Interface.Testwindow;
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
	
	private int type;
	private int likestate;
	
	public CheckBoxListener(int type){
		this.type=type;
		this.likestate=0;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		switch(type){
		case 1:handleChoose();break;//choosepanel.baidu,youdao,jinshan
		case 4:handlebookmark();break;//bookmark.baidu
		//case 7:handleLike();break;//textpanel.like
		case 8:handleTakeword();break;//textpanel.takeword
		}
	}
	
	public void handleChoose(){
		resetBookMark();
	}
	
	public void handlebookmark(){

		//String key = searchpanel.input.getSelectedItem().toString();
	//	likestate=1;
		
		String key=Testwindow.searchpanel.input.getText();
		if(key==null||key.length()==0)
			return;
		
		try{
			//send
			//ObjectOutputStream toServer=new ObjectOutputStream(Testwindow.socket.getOutputStream());
			Testwindow.toServer.writeObject(3);
			Testwindow.toServer.writeObject(key);
			//Testwindow.dataToServer.flush();
			
			//ObjectInputStream fromServer=new ObjectInputStream(Testwindow.socket.getInputStream());
			Testwindow.info.setbinglikes((int)Testwindow.fromServer.readObject());
			Testwindow.info.setyoudaolikes((int)Testwindow.fromServer.readObject());
			Testwindow.info.setjinshanlikes((int)Testwindow.fromServer.readObject());
			
			if(Testwindow.user.Logged()){
				Testwindow.info.setjudgebing((int)Testwindow.fromServer.readObject());
				Testwindow.info.setjudgeyoudao((int)Testwindow.fromServer.readObject());
				Testwindow.info.setjudgejinshan((int)Testwindow.fromServer.readObject());
				//System.out.println(judgebing+":"+judgeyoudao+":"+judgejinshan);
			}
			
		}
		catch(IOException | ClassNotFoundException ex){
			System.err.println(ex);
		}

		if(Testwindow.textpanel.bing.isSelected()){
			//System.out.println(info.getjudgebing());///////
			if(Testwindow.info.getjudgebing()==1)
				Testwindow.textpanel.like.setSelected(true);
			else if(Testwindow.info.getjudgebing()==-1)
				Testwindow.textpanel.like.setSelected(false);
			BingTranslate B = new BingTranslate();
			Translation trans = B.Translation(key);
			trans.print(Testwindow.textpanel.Out);
		}
		if(Testwindow.textpanel.youdao.isSelected()){
			if(Testwindow.info.getjudgeyoudao()==1)
				Testwindow.textpanel.like.setSelected(true);
			else if(Testwindow.info.getjudgeyoudao()==-1)
				Testwindow.textpanel.like.setSelected(false);
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation trans = Y.Translation(key);
			trans.print(Testwindow.textpanel.Out);
		}
		if(Testwindow.textpanel.jinshan.isSelected()){
			if(Testwindow.info.getjudgejinshan()==1)
				Testwindow.textpanel.like.setSelected(true);
			else if(Testwindow.info.getjudgejinshan()==-1)
				Testwindow.textpanel.like.setSelected(false);
			JinshanTranslate J = new JinshanTranslate();
			Translation trans = J.Translate(key);
			trans.print(Testwindow.textpanel.Out);
		}	
		Testwindow.textpanel.Above.revalidate();
		Testwindow.textpanel.Above.repaint();
		
	//	likestate=0;
	}
	

	public void handleTakeword(){
		
	}
	
	public static void resetBookMark(){
		
		int a = Testwindow.info.getbinglikes();
		int b = Testwindow.info.getyoudaolikes();
		int c = Testwindow.info.getjinshanlikes();
		Testwindow.textpanel.Left.removeAll();
		if(a>=b&&b>=c){
			Testwindow.textpanel.Left.add(Testwindow.textpanel.bing);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.youdao);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.jinshan);
		}
		else if(a>=c&&c>=b){
			Testwindow.textpanel.Left.add(Testwindow.textpanel.bing);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.jinshan);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.youdao);
		}
		else if(b>=a&&a>=c){
			Testwindow.textpanel.Left.add(Testwindow.textpanel.youdao);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.bing);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.jinshan);
		}
		else if(b>=c&&c>=a){
			Testwindow.textpanel.Left.add(Testwindow.textpanel.youdao);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.jinshan);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.bing);
		}
		else if(c>=a&&a>=b){
			Testwindow.textpanel.Left.add(Testwindow.textpanel.jinshan);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.bing);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.youdao);
		}
		else{//c>=b>=a
			Testwindow.textpanel.Left.add(Testwindow.textpanel.jinshan);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.youdao);  
			Testwindow.textpanel.Left.add(Testwindow.textpanel.bing);
		}

		int count = 3;
		if(!Testwindow.choosepanel.bing.isSelected()){
			Testwindow.textpanel.Left.remove(Testwindow.textpanel.bing);
			count--;
		}
		if(!Testwindow.choosepanel.youdao.isSelected()){
			Testwindow.textpanel.Left.remove(Testwindow.textpanel.youdao);
			count--;
		}
		if(!Testwindow.choosepanel.jinshan.isSelected()){
			Testwindow.textpanel.Left.remove(Testwindow.textpanel.jinshan);
			count--;
		}
		if(Testwindow.choosepanel.bing.isSelected())
			Testwindow.textpanel.bing.setSelected(true);
		else if(Testwindow.choosepanel.youdao.isSelected())
			Testwindow.textpanel.youdao.setSelected(true);
		else if(Testwindow.choosepanel.jinshan.isSelected())
			Testwindow.textpanel.jinshan.setSelected(true);
		
		Testwindow.textpanel.Left.setLayout(new GridLayout(count,1,5,10));
		Testwindow.textpanel.Left.revalidate();
		Testwindow.textpanel.Left.repaint();
	}
}
