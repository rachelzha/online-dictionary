package src.Translate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
//参考：https://github.com/ilikecyf86/OnlineDictionary/blob/master/src/OnlineSearch.java ///Thank you

public class BingTranslate {
	 public String Translate(String key) {
	        String url = "http://cn.bing.com/dict/search?q=" + key + "&go=%E6%90%9C%E7%B4%A2&qs=bs&form=Z9LH5";
	        String result="";
	        try {
	            Document page = Jsoup.connect(url).get();
	            Elements pos = page.select(".qdef ul li .pos");
	            Elements def = page.select(".qdef ul li .def");
	            Elements hd_if = page.select(".qdef .hd_if");
	            for(int i = 0; i < pos.size(); i++){//正则表达式解析
	                result += '[';
	                result += pos.get(i).text();
	                result += "] ";
	                result += def.get(i).text();
	                result += "\n";
	            }
	            if(hd_if.size() != 0)
	                result += '\n';
	            for(int i = 0; i < hd_if.size(); i++){
	                result += hd_if.get(i).text();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	     return result;
	 }
	 
	 public static void main(String []args){
		 String key="abstract";
		 String result=new BingTranslate().Translate(key);
		 System.out.println(result);
	 }
	 
}
