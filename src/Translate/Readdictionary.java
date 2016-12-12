package src.Translate;

import java.util.Scanner;
import java.util.TreeMap;

import java.io.*;

public class Readdictionary {
	private TreeMap<String,String> Tree = new TreeMap<String,String>();
	
	//Read dictionary
	public Readdictionary(File file){
		try{
			if(!file.exists()){
				System.out.println("Source file "+file+" does not exist");
				System.exit(0);
			}
			Scanner input = new Scanner(file);
			String S = input.nextLine();
		//System.out.println(S);
		//int num=0;
		//StringBuffer sb = new StringBuffer();
			while(input.hasNext()){
			//num++;
				S = input.nextLine();
				int index1=0;
				while((S.charAt(index1)<='9'&&S.charAt(index1)>='0')||S.charAt(index1)==' '){
					index1++;
				}
			
				int index3=index1;
				int index2=index1;
				while(index3+2<S.length()){
					if(S.charAt(index3)==' '&&S.charAt(index3+1)==' '&&index2==index1){
						index2=index3;
					}
					if(S.charAt(index3)==' '&&S.charAt(index3+1)==' '&&S.charAt(index3+2)!=' '){
						index3=index3+2;
						break;
					}
					index3++;
				}
			
			//System.out.println(S);
				Tree.put(S.substring(index1, index2), S.substring(index3));
			}
			input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//http://www.codeproject.com/Articles/13525/Fast-memory-efficient-Levenshtein-algorithm//编辑距离算法
	public static int Levenshtein(String s,String t){
		int n=s.length();
		int m=t.length();
		if(n==0)return m;
		if(m==0)return n;
		int []v0 = new int[m+1];
		int []v1 = new int[m+1];
		for(int i=0;i<m+1;i++){
			v0[i]=i;
			v1[i]=0;
		}
		int cost=0;
		for(int i=1;i<n+1;i++){	
			v1[0]=v0[0]+1;
			for(int j=1;j<m+1;j++){
				char a=s.charAt(i-1);
				char b=t.charAt(j-1);
				if(a>='A'&&a<='Z')
					a=(char)(a-'A'+'a');
				if(b>='A'&&b<='Z')
					b=(char)(b-'A'+'a');
				
				if(a==b)
					cost=0;
				else
					cost=1;
				v1[j]=v1[j-1]+1;
				if(v1[j]>v0[j]+1)
					v1[j]=v0[j]+1;
				if(v1[j]>v0[j-1]+cost)
					v1[j]=v0[j-1]+cost;
			}
			
			for(int j=0;j<m+1;j++)
				v0[j]=v1[j];
		}
		return v1[m];
	}
	
	//获取编辑长度小于单词长度一半的相似单词
	public String[] getsimilarwords(String key){
		int len = 2;
		int num=0;
		String[] similar =  new String[1000];//////////////////////////
		String word = Tree.firstKey();
		for(int i=1;i<Tree.size();i++){
			if(Levenshtein(word,key)<len){
				similar[num]=word;
				num++;
			}
			word=Tree.higherKey(word);
		}
		return similar;
	}
}
