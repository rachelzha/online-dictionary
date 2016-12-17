package src.information;

public class UserState {
	private String username=null;
	
	public void setUsername(String username){
		this.username=username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public boolean Logged(){
		if(username==null)return false;
		else return true;
	}
}
