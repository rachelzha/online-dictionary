package src.Interface.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import src.Interface.panel.SearchPanel;
import src.Interface.panel.TextPanel;
import src.Translate.Readdictionary;
public class ComboBoxListener implements MouseListener {

	TextPanel textpanel;
	SearchPanel searchpanel;
	public ComboBoxListener(SearchPanel search,TextPanel text){
		textpanel=text;
		searchpanel=search;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	//	textpanel.Out.setText("lalala");
	//	String key = searchpanel.input.getSelectedItem().toString();
		//System.out.println(key);
	/*	Readdictionary Dic = new Readdictionary(new File("dictionary.txt"));
		ArrayList<String>words=Dic.getsimilarwords(key);
		for(int i=0;i<words.size();i++){
			System.out.println(words.get(i));
	//		searchpanel.input.addItem(words.get(i));
		}*/
	//	searchpanel.input.addItem("ok");
	//	searchpanel.input.addItem(key);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		textpanel.Out.setText("lalala");
		//String key = searchpanel.input.getSelectedItem().toString();
	//	System.out.println(key);
	}

}
