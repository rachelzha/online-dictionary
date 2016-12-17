package src.Interface.listener;
import java.awt.GridLayout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import src.Interface.MainWindow;
import src.Translate.BingTranslate;
import src.Translate.JinshanTranslate;
import src.Translate.Translation;
import src.Translate.YoudaoTranslate;



public class CheckBoxListener implements ItemListener{
	
	private int type;
	private Lock lock=new ReentrantLock();

	public CheckBoxListener(int type){
		this.type=type;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		switch(type){
		case 1:handleChoose();break;//choosepanel.baidu,youdao,jinshan
		case 4:handlebookmark();break;//bookmark.baidu
		}
	}
	
	//choosepanel
	public void handleChoose(){
		resetBookMark();
	}
	
	//bookmark in textpanel
	public void handlebookmark(){
		
		String temp=MainWindow.searchpanel.input.getText();
		String key=ButtonListener.deleteExtraSpace(temp);
		if(key.length()==0)return;
		
		try{
			//send
			lock.lock();
			MainWindow.toServer.writeObject(3);
			MainWindow.toServer.writeObject(key);
			//receive likes
			MainWindow.info.setbinglikes((int)MainWindow.fromServer.readObject());
			MainWindow.info.setyoudaolikes((int)MainWindow.fromServer.readObject());
			MainWindow.info.setjinshanlikes((int)MainWindow.fromServer.readObject());
			
			if(MainWindow.user.Logged()){
				MainWindow.info.setjudgebing((int)MainWindow.fromServer.readObject());
				MainWindow.info.setjudgeyoudao((int)MainWindow.fromServer.readObject());
				MainWindow.info.setjudgejinshan((int)MainWindow.fromServer.readObject());
			}
			lock.unlock();
		}
		catch(IOException | ClassNotFoundException ex){
			lock.unlock();
			System.err.println(ex);
		}

		//show translation
		if(MainWindow.textpanel.bing.isSelected()){
			if(MainWindow.info.getjudgebing()==1)
				MainWindow.textpanel.like.setSelected(true);
			else if(MainWindow.info.getjudgebing()==-1)
				MainWindow.textpanel.like.setSelected(false);
			BingTranslate B = new BingTranslate();
			Translation trans = B.Translation(key);
			trans.print(MainWindow.textpanel.Out);
		}
		if(MainWindow.textpanel.youdao.isSelected()){
			if(MainWindow.info.getjudgeyoudao()==1)
				MainWindow.textpanel.like.setSelected(true);
			else if(MainWindow.info.getjudgeyoudao()==-1)
				MainWindow.textpanel.like.setSelected(false);
			YoudaoTranslate Y = new YoudaoTranslate();
			Translation trans = Y.Translate(key);
			trans.print(MainWindow.textpanel.Out);
		}
		if(MainWindow.textpanel.jinshan.isSelected()){
			if(MainWindow.info.getjudgejinshan()==1)
				MainWindow.textpanel.like.setSelected(true);
			else if(MainWindow.info.getjudgejinshan()==-1)
				MainWindow.textpanel.like.setSelected(false);
			JinshanTranslate J = new JinshanTranslate();
			Translation trans = J.Translate(key);
			trans.print(MainWindow.textpanel.Out);
		}	
		//refresh mainwindow
		MainWindow.textpanel.Above.revalidate();
		MainWindow.textpanel.Above.repaint();

	}
	
	//reset bookmark by likes
 	public static void resetBookMark(){
		//get likes
		int a = MainWindow.info.getbinglikes();
		int b = MainWindow.info.getyoudaolikes();
		int c = MainWindow.info.getjinshanlikes();
		
		//repaint bookmarks by likes and choosepanel
		MainWindow.textpanel.Left.removeAll();
		if(a>=b&&b>=c){
			MainWindow.textpanel.Left.add(MainWindow.textpanel.bing);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.youdao);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.jinshan);
		}
		else if(a>=c&&c>=b){
			MainWindow.textpanel.Left.add(MainWindow.textpanel.bing);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.jinshan);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.youdao);
		}
		else if(b>=a&&a>=c){
			MainWindow.textpanel.Left.add(MainWindow.textpanel.youdao);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.bing);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.jinshan);
		}
		else if(b>=c&&c>=a){
			MainWindow.textpanel.Left.add(MainWindow.textpanel.youdao);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.jinshan);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.bing);
		}
		else if(c>=a&&a>=b){
			MainWindow.textpanel.Left.add(MainWindow.textpanel.jinshan);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.bing);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.youdao);
		}
		else{//c>=b>=a
			MainWindow.textpanel.Left.add(MainWindow.textpanel.jinshan);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.youdao);  
			MainWindow.textpanel.Left.add(MainWindow.textpanel.bing);
		}

		int count = 3;
		if(!MainWindow.choosepanel.bing.isSelected()){
			MainWindow.textpanel.Left.remove(MainWindow.textpanel.bing);
			count--;
		}
		if(!MainWindow.choosepanel.youdao.isSelected()){
			MainWindow.textpanel.Left.remove(MainWindow.textpanel.youdao);
			count--;
		}
		if(!MainWindow.choosepanel.jinshan.isSelected()){
			MainWindow.textpanel.Left.remove(MainWindow.textpanel.jinshan);
			count--;
		}
		
		//set default translation
		if(MainWindow.choosepanel.youdao.isSelected())
			MainWindow.textpanel.youdao.setSelected(true);
		else if(MainWindow.choosepanel.jinshan.isSelected())
			MainWindow.textpanel.jinshan.setSelected(true);
		else if(MainWindow.choosepanel.bing.isSelected())
			MainWindow.textpanel.bing.setSelected(true);
		
		MainWindow.textpanel.Left.setLayout(new GridLayout(count,1,5,10));
		MainWindow.textpanel.Left.revalidate();
		MainWindow.textpanel.Left.repaint();
	}
}
