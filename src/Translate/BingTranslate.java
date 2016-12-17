package src.Translate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//参考：https://github.com/ilikecyf86/OnlineDictionary/blob/master/src/OnlineSearch.java ///Thank you
//网页爬虫
public class BingTranslate {
	Translation t=new Translation();
	
	 public Translation Translation(String key) {
	        String url = "http://cn.bing.com/dict/search?q=" + key + "&go=%E6%90%9C%E7%B4%A2&qs=bs&form=Z9LH5";

	        try {
	            Document page = Jsoup.connect(url).get();
	            Elements pos = page.select(".qdef ul li .pos");
	            Elements def = page.select(".qdef ul li .def");
	            Elements hd_if = page.select(".qdef .hd_if");
	            
	            t.word=key;
		        
		        for(int i = 0; i < pos.size(); i++){//正则表达式解析
		        	Definition definition=new Definition();
	                definition.characteristic=pos.get(i).text();
	                definition.definitions=def.get(i).text();
	               
	                t.trans.add(definition);
		        }
		        
	            for(int i = 0; i < hd_if.size(); i++){
	                t.other.add(hd_if.get(i).text());
	            }
		        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	     
	        return t;
	 }
	 
	
}
