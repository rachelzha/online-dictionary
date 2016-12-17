package src.information;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String sender;
	Card card;
	String time;
	
	public Message(String sender,Card card,String time){
		this.sender=sender;
		this.card=card;
		this.time=time;
	}
	
	public String getinfo(){
		String info = sender+"  "+time;
		return info;
	}
	public Card getCard(){
		return card;
	}
}
