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
}
