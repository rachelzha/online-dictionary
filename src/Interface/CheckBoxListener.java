package src.Interface;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckBoxListener implements ItemListener{

	private int type;
	private SearchPanel searchpanel = new SearchPanel();
	private TextPanel textpanel = new TextPanel();
	private ChoosePanel choosepanel = new ChoosePanel();
	private LoginPanel loginpanel = new LoginPanel();
	
	public CheckBoxListener(LoginPanel login,SearchPanel search,ChoosePanel choose,TextPanel text){
		this.type=0;
		this.loginpanel = login;
		this.searchpanel = search;
		this.choosepanel = choose;
		this.textpanel = text;
	}
	
	public void setType(int type){
		this.type = type;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		switch(type){
		case 1:handleChoosebaidu();break;//choosepanel.baidu
		case 2:handleChooseyoudao();break;//choosepanel.youdao
		case 3:handleChoosejinshan();break;//choosepanel.jinshan
		case 4:handlemarkbaidu();break;//bookmark.baidu
		case 5:handlemarkyoudao();break;//bookmark.youdao
		case 6:handlemarkjinshan();break;//bookmark.jinshan
		case 7:handleLike();break;//textpanel.like
		case 8:handleTakeword();break;//textpanel.takeword
		}
	}
	
	public void handleChoosebaidu(){
		
	}
	
	public void handleChooseyoudao(){
		
	}
	
	public void handleChoosejinshan(){
		
	}
	
	public void handlemarkbaidu(){
		
	}
	
	public void handlemarkyoudao(){
		
	}
	
	public void handlemarkjinshan(){
		
	}
	
	public void handleLike(){
		
	}
	
	public void handleTakeword(){
		
	}
}
