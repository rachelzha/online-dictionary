package src.Translate;
import java.io.*;
import java.util.Vector;

public class History {
	
	private int pointer;
	int size;

	
	public History(){
		pointer=0;
		size=0;
	};
	
	public Vector<String> Read(){
		Vector<String> history=new Vector<String>();
		File file = new File("history.txt");
	    BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(file));
	    	String tempString = null;
	    //	tempString = reader.readLine();
	    //	pointer = Integer.parseInt(tempString);
	    	while ((tempString = reader.readLine()) != null) {
	    		if(tempString!=null||tempString.length()!=0)
	    			history.add(tempString);
	    		//System.out.println(tempString);
	    	}
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	            	reader.close();
	            } catch (IOException e1) {
	            	}
	        }
	    }
	    size=history.size();
		return history;
	}
	
	public void Write(Vector<String> history){
		if(history.size()>10)
			pointer=10-1;
		else
			pointer=history.size()-1;
		 try{
			StringBuffer sb = new StringBuffer();
		//	sb.append(pointer+"\n");
			int i=0;
			while(history.size()>10){
				history.removeElementAt(0);
			}
			while(i<history.size()){
				String s1 =history.elementAt(i);
				if(s1.length()!=0||s1!=null){
					sb.append(s1+"\n" );
					i++;
				}
			}

			File sourcefile = new File("history.txt");	
			PrintWriter output = new PrintWriter(sourcefile);
			output.print(sb);
			output.close();
		}catch(IOException e){
		      e.printStackTrace();
		 }
	}
	
	public void prevpointer(){
		if(size==0)
			pointer=0;
		else{
			pointer--;
			if(pointer<0)
				pointer=size-1;
		}
	}
	
	public void nextpointer(){
		if(size==0)
			pointer=0;
		else{
			pointer++;
			if(pointer>=size)
				pointer=0;
		}
	}
	
	public int getpointer(){
		return pointer;
	}
	
	public int getsize(){
		return size;
	}
}
