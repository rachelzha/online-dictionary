
/*
 * �ο���http://www.jb51.net/article/68635.htm  //md5����
 * http://blog.csdn.net/yueqinglkong/article/details/37739771   //JsonToString
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;

public class BaiduTranslate {

	private String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	String id="20161106000031408";
	String key="I9l6aqkcBL3cEmMC3a3Z";
	String salt=Long.toString(new Date().getTime());
	
	public String sendGet(String str) {
		String sign=id+str+salt+key;//appid=2015063000000001+q=apple+salt=1435660288+��Կ=12345678
		String sign_md5=toMD5(sign);
		// �����UTF-8
		try {
			str = URLEncoder.encode(str, "utf-8");
		} 
		catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		} 
		String param = "q="+str+"&from=en&to=zh&appid="+id+"&salt="+salt+"&sign="+sign_md5;

		BufferedReader reply = null;
		try {
		  String urlName = url + "?" + param;
		  URL realUrl = new URL(urlName);
	   // �򿪺�URL֮�������
		  URLConnection conn = realUrl.openConnection();
		  conn.setConnectTimeout(10000);//���ó�ʱ
		  
		  reply = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//Լ���������ı���  
		  
          StringBuilder builder = new StringBuilder();
          String line = null;
          while ((line = reply.readLine()) != null) {
              builder.append(line);
          }
          String text = builder.toString();
          
          reply.close();
          
          return JsonToString(text);
		}
		catch (MalformedURLException e) {
            e.printStackTrace();
        } 
		catch (IOException e) {
            e.printStackTrace();
        }		
		return null;
	}
	
	
	public static String JsonToString(String jstring) {  
		try {  
			JSONObject obj = new JSONObject(jstring);  
	        org.json.JSONArray array = obj.getJSONArray("trans_result");  
	        obj = array.getJSONObject(0);
	        String word = obj.getString("dst");  
	        return word;  
	    } 
		catch (JSONException e) {  
	            // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return null;  
	}  
	
	public static String toMD5(String plainText) {
		StringBuffer buf = new StringBuffer("");
	    try {
	        //����ʵ��ָ��ժҪ�㷨�� MessageDigest ����
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        //ʹ��ָ�����ֽ��������ժҪ��
	        md.update(plainText.getBytes());
	        //ͨ��ִ���������֮������ղ�����ɹ�ϣ���㡣
	        byte b[] = md.digest();
	        //���ɾ����md5���뵽buf����
	        int i;
	  
	        for (int offset = 0; offset < b.length; offset++) {
	          i = b[offset];
	          if (i < 0)
	            i += 256;
	          if (i < 16)
	            buf.append("0");
	          buf.append(Integer.toHexString(i));
	        }
	      //  System.out.println("32λ: " + buf.toString());// 32λ�ļ���
	      //  System.out.println("16λ: " + buf.toString().substring(8, 24));// 16λ�ļ��ܣ���ʵ����32λ���ܺ�Ľ�ȡ
	     } 
	     catch (Exception e) {
	       e.printStackTrace();
	     }
	     return buf.toString();
	}

	public String Translation(String query){
		String message="ԭ�ģ�"+query+'\n'+"���룺";
		message+=sendGet(query);
		return message;
	}
	
/*	public static void main(String[] args){
		BaiduTranslate B = new BaiduTranslate();
		String m = B.Translation("water");
		System.out.println(m);
	}*/
}
