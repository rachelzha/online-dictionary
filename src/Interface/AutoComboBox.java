package src.Interface;
import javax.swing.*; 


public class AutoComboBox extends JComboBox<String>{
	 /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	AutoComboBox(){
		super();
	}
	
	public   AutoComboBox(ComboBoxModel<String> cm)   { 
         super(cm); 
  
	}
	
	public String getText(){ 
		 return   ((JTextField)   getEditor().getEditorComponent()).getText();
    } 

	public JTextField getTextField(){
		return   ((JTextField)   getEditor().getEditorComponent());
	}
	
	public void addWords(String[] relatedwords){
		
	}
}

