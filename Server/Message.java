package Server;

public class Message {
	String sender;
	Card card;
	String time;
	
	Message(String sender,Card card,String time){
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
