package Translate;
/*
参考
http://blog.csdn.net/daixinmei/article/details/10210731
http://wangbaiyuan.cn/android-java-parsing-api-youdao-translation-json-data.html  //有道api json解析
*/ 

import java.io.*;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YoudaoTranslate {
	private String url = "http://fanyi.youdao.com/openapi.do";
	 
	private String sendGet(String str) {
	  // 编码成UTF-8
		try {
			str = URLEncoder.encode(str, "utf-8");
		} 
		catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		}
	  
		String param ="keyfrom=ZrqandZfy&key=1740122831&type=data&doctype=json&version=1.1&q="+str ;
		BufferedReader reply = null;
		try {
		//http://fanyi.youdao.com/openapi.do?keyfrom=ZrqandZfy&key=1740122831&type=data&doctype=<doctype>&version=1.1&q=要翻译的文本
		  String urlName = url + "?" + param;
		  URL realUrl = new URL(urlName);
	   // 打开和URL之间的连接
		  URLConnection conn = realUrl.openConnection();
		  conn.setConnectTimeout(3000);
          conn.connect();
		  reply = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//约定输入流的编码
		  
		  String result = reply.readLine();
		  reply.close();
		  
		  return result;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String JsonToString(String jstring) {  
        try {  
        	String message="";
            JSONObject obj = new JSONObject(jstring);  
            String errorcode = obj.getString("errorCode");
            //System.out.println(errorcode);
            
            if(errorcode.equals("20")){
            	message = "要翻译的文本过长";
            }
            else if(errorcode.equals("30")){
            	message = "无法进行有效的翻译";
            }
            else if(errorcode.equals("40")){
            	message = "不支持的语言类型";
            }
            else if(errorcode.equals("50")){
            	message = "无效的key";
            }
            else if(errorcode.equals("60")){
            	message = "无词典结果，仅在获取词典结果生效";
            }
            else if(errorcode.equals("0")){
            	String query = obj.getString("query");
            	JSONArray translation = obj.has("translation") ? obj.getJSONArray("translation") : null;
            	JSONObject basic = obj.has("basic") ? obj.getJSONObject("basic") : null;
            	JSONArray web = obj.has("web") ? obj.getJSONArray("web") : null;
            	
            	String phonetic=null;
                String uk_phonetic=null;
                String us_phonetic=null;
                JSONArray explains=null;
                if(basic!=null){
                	phonetic=basic.has("phonetic")? basic.getString("phonetic"):null;
                	uk_phonetic=basic.has("uk-phonetic")? basic.getString("uk-phonetic"):null;
                	us_phonetic=basic.has("us-phonetic")? basic.getString("us-phonetic"):null;
                    explains=basic.has("explains")? basic.getJSONArray("explains"):null;
                }
                String translationStr="";
                if(translation!=null){
                    translationStr="\n翻译：\n";
                    for(int i=0;i<translation.length();i++){
                        translationStr+="\t<"+(i+1)+">"+translation.getString(i)+"\n";
                    }
                }
                String phoneticStr=(phonetic!=null? "\n发音："+phonetic:"")
                        +(uk_phonetic!=null? "\n英式发音："+uk_phonetic:"")
                        +(us_phonetic!=null? "\n美式发音："+us_phonetic:"");
                String explainStr="";
                if(explains!=null){
                    explainStr="\n\n释义：\n";
                    for(int i=0;i<explains.length();i++){
                        explainStr+="\t<"+(i+1)+">"+explains.getString(i)+"\n";
                    }
                }

                message="原文："+query+"\n"+translationStr+phoneticStr+explainStr;
                
                if (web!=null) {  
                    JSONArray webString = new JSONArray("[" + web  
                            + "]");  
                    message += "\n网络释义：";  
                    JSONArray webArray = webString.getJSONArray(0);  
                    int count = 0;  
                    while (!webArray.isNull(count)) {  

                        if (webArray.getJSONObject(count)  
                                .has("key")) {  
                            String key = webArray.getJSONObject(  
                                    count).getString("key");  
                            message += "\n\t<" + (count + 1) + ">"  
                                    + key;  
                        }  
                        if (webArray.getJSONObject(count).has(  
                                "value")) {  
                            String value = webArray.getJSONObject(  
                                    count).getString("value");  
                            message += "\n\t   " + value;  
                        }  
                        count++;  
                    }  
                }

            }
            return message;  
        } 
        catch (JSONException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;  
    }   
	
	public String Translation(String query){
		String message = JsonToString(sendGet(query));
		return message;
	}
	
	public static void main(String[] args){
		YoudaoTranslate Y = new YoudaoTranslate();
		String m = Y.Translation("water");
		System.out.println(m);
	}
}
