 

import java.io.*;
import java.net.*;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class JinshanTranslate {
	private String url = "http://dict-co.iciba.com/api/dictionary.php";
	private String key = "DFAD3B39DE20B8E63E050779B91F9F0D";
	
	private String sendGet(String str) {
	  // 编码成UTF-8
		try {
			str = URLEncoder.encode(str, "utf-8");
		} 
		catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		}
	  
		String param = "w="+str+"&key="+key;
		BufferedReader reply = null;
		try {
		//http://fanyi.youdao.com/openapi.do?keyfrom=ZrqandZfy&key=1740122831&type=data&doctype=<doctype>&version=1.1&q=要翻译的文本
		  String urlName = url + "?" + param;
		  URL realUrl = new URL(urlName);
	   // 打开和URL之间的连接
		  URLConnection conn = realUrl.openConnection();
		  conn.setConnectTimeout(10000);
		  
		  reply = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//约定输入流的编码
		  
		  String temp ;
		  String result = "";
		  while((temp = reply.readLine()) != null){
			result = result + temp.trim();
		  }
		  
		  reply.close();
		  //System.out.println(result);
		  return result;
		}
		
		catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
    
	public static String xmltostring(String protocolXML) {   
        SAXBuilder builder=new SAXBuilder(false);   
        try {   
        	String message="";
        	Document doc = builder.build(new InputSource(new StringReader(protocolXML)));   
            Element eles = doc.getRootElement(); // 得到根元素   
             //System.out.println("根节点"+eles.getName());   
               
             @SuppressWarnings("unchecked")
			List<Element> list = eles.getChildren(); // 得到元素的集合   
  
            int flag=0;
            if( list!=null){   
                for (int i = 0; i < list.size(); i++) {   
                     Element book = list.get(i);   
                     if(book.getName()=="query"||book.getName()=="key")
                    	 flag=1;
                     if(flag==1)
                    	 message += book.getName() + " : "  + book.getValue()+'\n';
                 }   
            }   
            return message;
        } 
        catch (Exception e) {   
             e.printStackTrace();   
        }   
        return null;
     }   
	
	public String Translate(String query){
		String message = xmltostring(sendGet(query));
		return message;
	}
	/*
	 public static void main(String[] args)throws Exception {
		 JinshanTranslate test = new JinshanTranslate();
		 System.out.println(test.Translate("water"));
		// System.out.println(temp);
	 }*/ 

}
