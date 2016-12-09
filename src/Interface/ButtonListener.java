package src.Interface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import src.Translate.BaiduTranslate;
import src.Translate.JinshanTranslate;
import src.Translate.YoudaoTranslate;

public class ButtonListener implements ActionListener{

	private int type;
	private SearchPanel searchpanel = new SearchPanel();
	private TextPanel textpanel = new TextPanel();
	private ChoosePanel choosepanel = new ChoosePanel();
	private LoginPanel loginpanel = new LoginPanel();
	
	public ButtonListener(LoginPanel login,SearchPanel search,ChoosePanel choose,TextPanel text){
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (type){
		case 1:handleSearch();break;//search button
		case 2:handlePrev();break;//prev button
		case 3:handleNext();break;//next button
		case 4:handlePulldown();break;//pulldown button
		case 5:handleLogin();break;//login button
		case 6:handleIndividuation();break;//individuation button
		case 7:handleMessage();break;//message button
		case 8:handleShare();break;//share button
		}
	}

	public void handleSearch(){
		String key = searchpanel.input.getText();
		if(textpanel.baidu.isSelected()){
			BaiduTranslate B = new BaiduTranslate();
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
	
	public void handlePrev(){
		
	}
	
	public void handleNext(){
		
	}
	
	public void handlePulldown(){
		
	}
	
	public void handleLogin(){
		
	}
	
	public void handleIndividuation(){
		
	}
	
	public void handleMessage(){
		
	}
	
	public void handleShare(){
		
	}
}
