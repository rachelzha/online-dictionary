package src.Interface;
import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Translate.Readdictionary;

public class documentListener implements DocumentListener {

	String []relatedwords;
	SearchPanel searchpanel;
	Readdictionary Dic;
	public documentListener(SearchPanel searchpanel){
		this.searchpanel=searchpanel;
		File file = new File("dictionary.txt");
		Dic = new Readdictionary(file);
	}
	
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		String key=searchpanel.input.getText();
		String []hello={"hello"};
		searchpanel.input.addWords(hello);
	/*	relatedwords=Dic.getsimilarwords(key);
		//for(int i=0;i<1;i++){
		//	System.out.println(relatedwords[i]);
		//}
		if(relatedwords!=null)searchpanel.input.addWords(relatedwords);*/ 
	}

	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		String key=searchpanel.input.getText();
	/*	relatedwords=Dic.getsimilarwords(key);
		searchpanel.input.addWords(relatedwords);*/ 
		
	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		String key=searchpanel.input.getText();
	/*	relatedwords=Dic.getsimilarwords(key);
		searchpanel.input.addWords(relatedwords);*/ 
	}

}
