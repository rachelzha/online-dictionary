package src.Interface;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Translate.Readdictionary; 


public class AutoComboBox extends JComboBox<String> implements KeyListener {
	 /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private JTextField editor = null;
	ArrayList<String> relatedwords=new ArrayList<String>();
	Readdictionary Dic;
	
	AutoComboBox(){
		editor=((JTextField)   getEditor().getEditorComponent());
		File file = new File("dictionary.txt");
		Dic = new Readdictionary(file);
		//setModel(new DefaultComboBoxModel<>());
		setModel(new DefaultComboBoxModel<>(new String[]{""}));
		editor.getDocument().addDocumentListener(inputListener);
	}
	
	public String getText(){ 
		 return   ((JTextField)   getEditor().getEditorComponent()).getText();
    } 

	public JTextField getTextField(){
		return editor;
	}
	
	/*
	public void addWords(){
		DefaultComboBoxModel<String>   model   =   new   DefaultComboBoxModel<String>(); 
        model.addElement( "abc "); 
        model.addElement( "aab "); 
        model.addElement( "aba ");
        super.setModel(model);
       
        //this.setModel(model);
	}*/

	public void getSimilarWords(String key, int loc){
	//	relatedwords=Dic.getsimilarwords(key);
		
		String []words={"1","2","3"};
		int i=0;
		if(words.length>1) {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			while(i<words.length) {
				model.addElement(words[i].toString());
				i++;
			}
		//	setModel(model);
            editor.setCaretPosition(loc); 
            showPopup();
		} 
	/*	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for(int i=0;i<relatedwords.size();i++){
			model.addElement(relatedwords.get(i));
		}
		setModel(model);
        editor.setCaretPosition(loc); 
        showPopup();*/
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		char ch = e.getKeyChar(); 
        if(ch == KeyEvent.CHAR_UNDEFINED||Character.isISOControl(ch)) {//||ch   ==   KeyEvent.VK_DELETE
        	return;
        }
        int caretPosition = editor.getCaretPosition(); 
        String str = editor.getText();
        if(str.length()==0) 
            return; 
        getSimilarWords(str, caretPosition);
	}
	
	private DocumentListener inputListener = new DocumentListener() {
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			getSimilarWords(editor.getText(),editor.getCaretPosition());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			getSimilarWords(editor.getText(),editor.getCaretPosition());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			getSimilarWords(editor.getText(),editor.getCaretPosition());
		}
    };

}

