
/*
 * 参考：http://www.jb51.net/article/68635.htm  //md5编码
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
		String sign=id+str+salt+key;//appid=2015063000000001+q=apple+salt=1435660288+密钥=12345678
		String sign_md5=toMD5(sign);
		// 编码成UTF-8
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
	   // 打开和URL之间的连接
		  URLConnection conn = realUrl.openConnection();
		  conn.setConnectTimeout(10000);//设置超时
		  
		  reply = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));//约定输入流的编码  
		  
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
	        //生成实现指定摘要算法的 MessageDigest 对象。
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        //使用指定的字节数组更新摘要。
	        md.update(plainText.getBytes());
	        //通过执行诸如填充之类的最终操作完成哈希计算。
	        byte b[] = md.digest();
	        //生成具体的md5密码到buf数组
	        int i;
	  
	        for (int offset = 0; offset < b.length; offset++) {
	          i = b[offset];
	          if (i < 0)
	            i += 256;
	          if (i < 16)
	            buf.append("0");
	          buf.append(Integer.toHexString(i));
	        }
	      //  System.out.println("32位: " + buf.toString());// 32位的加密
	      //  System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
	     } 
	     catch (Exception e) {
	       e.printStackTrace();
	     }
	     return buf.toString();
	}

	public String Translation(String query){
		String message="原文："+query+'\n'+"翻译：";
		message+=sendGet(query);
		return message;
	}
	
	public static void main(String[] args){
		BaiduTranslate B = new BaiduTranslate();
		String m = B.Translation("water");
		System.out.println(m);
	}
}
