package classify;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class Prepare_Test_Weight {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		File testDir= new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Test_TF/");
		String termdocfreq= "C:/NQA_Final/NQA_fin/Questions/Term_Frequency/TestDocFreq";//this file should be prepared
		StringBuilder sb=new StringBuilder();
		sb=assignWeight(testDir, termdocfreq, 0);
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/WtTest"), sb.toString());		
	}
	 public static StringBuilder assignWeight(File dir, String Termfreq, int docs) throws IOException{
	        String voc=FileUtils.readFileToString(new File(Termfreq)); //a document with term-number of docu coocurence
			File[] files = dir.listFiles();      
	                StringBuilder sb=new StringBuilder();  
	                int x = 0;
	                for(int i=0;i<files.length;i++){  
	                	x++;System.out.println(x);
	                    sb.append(docs+" ");
	                    String terms=FileUtils.readFileToString(files[i].getAbsoluteFile());//one document
	                    StringTokenizer stterm=new StringTokenizer(terms); 
	                    while(stterm.hasMoreElements()){
	                        String term=stterm.nextToken();
	                        Integer odf=Integer.parseInt(stterm.nextToken().toString());//number of occurrences of a feature and documents
	                      StringTokenizer stvoc= new StringTokenizer(voc);  
	                      while(stvoc.hasMoreElements()){
	                          String voct=stvoc.nextToken();
	                          Integer tremId=Integer.parseInt(stvoc.nextToken().toString());
	                          Integer termnf=Integer.parseInt(stvoc.nextToken().toString());
	                         if(term.equals(voct)){
	                             double tfidf= Math.log(1/termnf)*odf;;
	                             sb.append(tremId+":"+tfidf+" ");
	                             break;
	                         } 
	                      }
	                    }   
	                    sb.append("\n\n");
	                  //  System.out.println(sb.toString());
	                }
	                return sb;                
	    }
}
