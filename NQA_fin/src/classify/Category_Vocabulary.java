package classify;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class Category_Vocabulary {

	/**
	 * @param args
	 * create category vocabulary with the number of documents containing the term
	 */
	//public static File def = new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF/");
	
	public static void main(String[] args) throws IOException {
		//File def = new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF");
		categoryFreq();
		System.out.println("ደህና");
		//System.out.println("Ã¡â€¹Â°Ã¡Ë†â€¦Ã¡Å â€œ");

	}
	public static void categoryFreq() throws IOException{
		File def = new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF");
		File des = new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Des_TF");
		File bio = new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Bio_TF");
		doMatrix(def);
		doMatrix2(des);
		doMatrix3(bio);
	}
	public static void doMatrix(File dir) throws IOException{
		File[] files =dir.listFiles();
		Map<String,Integer> map=new HashMap<String, Integer>();
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < files.length; i++) {
			String f1= FileUtils.readFileToString(files[i].getAbsoluteFile());
			StringTokenizer token= new StringTokenizer(f1);
			while (token.hasMoreElements()) { 
				String x = token.nextToken();
				int y=Integer.parseInt((token.nextToken().toString()));
				//System.out.println(y);
				
				if(map.containsKey(x))
				{
					map.put(x, Integer.parseInt(map.get(x).toString())+y);
					//System.out.println(x+Integer.parseInt(map.get(x).toString()));
				}
				else
					{map.put(x,1);}				
			}			
		}
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String val=it.next().toString();			
			//if(!(val.equalsIgnoreCase("1")) )
				//  if(!(val.equalsIgnoreCase("2")))
					  sb.append(val+"\t"+map.get(val)+"\n");			
		}
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Each_Cat_TermFreq/def_TF.txt"), sb.toString());
		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Des_TF/des_TF.txt"), sb.toString());
		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Bio_TF/bio_TF.txt"), sb.toString());
			
	}
	public static void doMatrix2(File dir) throws IOException{
		File[] files =dir.listFiles();
		Map<String,Integer> map=new HashMap<String, Integer>();
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < files.length; i++) {
			String f1= FileUtils.readFileToString(files[i].getAbsoluteFile());
			StringTokenizer token= new StringTokenizer(f1);
			while (token.hasMoreElements()) { 
				String x = token.nextToken();
				int y=Integer.parseInt((token.nextToken().toString()));
				if(map.containsKey(x))
				{
					map.put(x, Integer.parseInt(map.get(x).toString())+y);
					//System.out.println(x+Integer.parseInt(map.get(x).toString()));
				}
				else
					{map.put(x,1);}				
			}			
		}
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String val=it.next().toString();			
			//if(!(val.equalsIgnoreCase("1")) )
				//  if(!(val.equalsIgnoreCase("2")))
					  sb.append(val+"\t"+map.get(val)+"\n");			
		}
		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF/def_TF.txt"), sb.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Each_Cat_TermFreq/des_TF.txt"), sb.toString());
		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Bio_TF/bio_TF.txt"), sb.toString());
			
	}
	public static void doMatrix3(File dir) throws IOException{
		File[] files =dir.listFiles();
		Map<String,Integer> map=new HashMap<String, Integer>();
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < files.length; i++) {
			String f1= FileUtils.readFileToString(files[i].getAbsoluteFile());
			StringTokenizer token= new StringTokenizer(f1);
			while (token.hasMoreElements()) { 
				String x = token.nextToken();
				int y=Integer.parseInt((token.nextToken().toString()));
				if(map.containsKey(x))
				{
					map.put(x, Integer.parseInt(map.get(x).toString())+y);
					//map.put(x, Integer.parseInt(map.get(x).toString())+1);
					//System.out.println(x+Integer.parseInt(map.get(x).toString()));
				}
				else
					{map.put(x, 1);}				
			}			
		}
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String val=it.next().toString();			
			//if(!(val.equalsIgnoreCase("1")) )
				//  if(!(val.equalsIgnoreCase("2")))
					  sb.append(val+"\t"+map.get(val)+"\n");			
		}
		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF/def_TF.txt"), sb.toString());
		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Des_TF/des_TF.txt"), sb.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Each_Cat_TermFreq/bio_TF.txt"), sb.toString());
			
	}

}
