package classify;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class Term_Frequency {

	/**
	 * @param args
	 * this class used to find the frequency of each term and writes the term with its respective frequency into a file with in a folder
	 */
	static File def=new File("C:/NQA_Final/NQA_fin/Questions/Def/");
	static File des=new File("C:/NQA_Final/NQA_fin/Questions/Des/");
	static File bio=new File("C:/NQA_Final/NQA_fin/Questions/Bio/");
	static void main(String[] args) throws IOException{		
		
		System.out.println("ደህና");
		//System.out.println("Ã¡â€¹Â°Ã¡Ë†â€¦Ã¡Å â€œ");
	}
	public static void termFreq() throws IOException{
		doMatrix(def);
		doMatrix(des);
		doMatrix(bio);
	}
	public static void doMatrix(File dir)throws IOException {
		File [] files=dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			Map<String, Integer> map=new HashMap<String, Integer>();
			StringBuffer sb=new StringBuffer();
			String f1=FileUtils.readFileToString(files[i].getAbsoluteFile(),"utf-8");
			StringTokenizer token= new StringTokenizer(f1);
			while (token.hasMoreElements()) {
				String x = token.nextToken();
				if (map.containsKey(x)) 
				{
					//map.put(x, Integer.parseInt(map.get(x).toString())+1);
					map.put(x, Integer.parseInt(map.get(x).toString())+1);
				}
				else {
					map.put(x, 1);
				}											
			}
			
			Iterator<String> it= map.keySet().iterator();
			while(it.hasNext()){
				  String val=it.next().toString();
				  //if(!(val.equalsIgnoreCase("1")) )
					  //if(!(val.equalsIgnoreCase("2")))
						  sb.append(val+"\t"+map.get(val)+"\n");				 					 	 
				  } 
			int did=i+1;//document id
				  if(dir.equals(def))
					  {
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF/"+ did), sb.toString());
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Def_Des_TF/"+ did), sb.toString());//writing copy of def_term frequency in def_des folder
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Def_Bio_TF/"+ did), sb.toString());// writing copy of def_term frequency in def_bio folder
					  }
				  else if(dir.equals(des))
					  {
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Des_TF/"+ did), sb.toString());
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Def_Des_TF/"+ did+"(2)"), sb.toString());
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Des_Bio_TF/"+ did), sb.toString());
					  }
				  else if(dir.equals(bio))
				  { 
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Bio_TF/"+ did), sb.toString());
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Def_Bio_TF/"+ did+"(2)"), sb.toString());
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Des_Bio_TF/"+ did+"(2)"), sb.toString());
				  }
				  else
					  System.out.println("Incorrect file path");
		}
	}

}
