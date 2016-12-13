package src.Interface;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Translate.Readdictionary;

public class documentListener implements DocumentListener {

	ArrayList<String> relatedwords=new ArrayList<String>();
	SearchPanel searchpanel;
	TextPanel textpanel;
	Readdictionary Dic;
	public documentListener(SearchPanel searchpanel,TextPanel textpanel){
		this.searchpanel=searchpanel;
		this.textpanel=textpanel;
		File file = new File("dictionary.txt");
		Dic = new Readdictionary(file);
/*		String key=searchpanel.input.getText();
		if(key==null||key.length()==0)
			return;
		relatedwords=Dic.getsimilarwords(key);
		searchpanel.model.removeAllElements();
		for(int i=0;i<relatedwords.size();i++){
			searchpanel.model.addElement(relatedwords.get(i));
		}
	*/	
	}
	
	public void insertUpdate(DocumentEvent e) {
		String key=searchpanel.input.getText();
		//searchpanel.model.addElement("ok");
		relatedwords=Dic.getsimilarwords(key);
        searchpanel.model.addElement( "abc "); 
		//	searchpanel.input.addWords();
	}

	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		String key=searchpanel.input.getText();
		//searchpanel.model.addElement("ok");
		textpanel.Out.setText(key);
	//	relatedwords=Dic.getsimilarwords(key);
		//searchpanel.input.addWords();
/*		DefaultComboBoxModel<String>   model   =   new   DefaultComboBoxModel<String>(); 
        model.addElement( "abc "); 
		searchpanel.input.setModel(model);
*/	/*	relatedwords=Dic.getsimilarwords(key);
		searchpanel.input.addWords(relatedwords);*/ 
	}

}
