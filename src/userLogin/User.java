package userLogin;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	
	public void setId(int id){
		this.id=id;
	}
	 
	public int getId(){
		return id;
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	
	public String getEmail(){
		return email;
	}
}
