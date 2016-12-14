package src.Interface.listener;

import java.util.Vector;

import Server.Message;

public class Info {
	public Info(){};
	
	private int binglikes;
	private int youdaolikes;
	private int jinshanlikes;
	private int judgebing;
	private int judgeyoudao;
	private int judgejinshan;
	private Vector<String>userlist=new Vector<String>();
	private Vector<String>onlineuserlist=new Vector<String>();
	private Vector<Message>messages=new Vector<Message>();
	
	public int getbinglikes(){return binglikes;}
	public int getyoudaolikes(){return youdaolikes;}
	public int getjinshanlikes(){return jinshanlikes;}
	public int getjudgebing(){return judgebing;}
	public int getjudgeyoudao(){return judgeyoudao;}
	public int getjudgejinshan(){return judgejinshan;}
	public Vector<String> getuserlist(){return userlist;}
	public Vector<String> getonlineuserlist(){return onlineuserlist;}
	public Vector<Message> getmessage(){return messages;}
	
	public void setbinglikes(int value){ binglikes=value;}
	public void setyoudaolikes(int value){ youdaolikes=value;}
	public void setjinshanlikes(int value){ jinshanlikes=value;}
	public void setjudgebing(int value){ judgebing=value;}
	public void setjudgeyoudao(int value){ judgeyoudao=value;}
	public void setjudgejinshan(int value){ judgejinshan=value;}
	public void setuserlist(Vector<String>list){userlist=list;}
	public void setonlineuserlist(Vector<String>list){onlineuserlist=list;}
	public void setmessage(Vector<Message>list){messages=list;}
}
