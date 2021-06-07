package classify;

//import index.AmharicStemFilter;
//import index.AmharicStemmer;
//import index.AmharicStopFilter;
//import index.Amharic_Tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.TokenStream;

public class QueryExpansion {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static File organizationListFile= new File("C:/Gazetteers/organizationT.txt");
	public static File synonymList= new File("C:/Gazetteers/synonymList.txt");
	public static File organizationList= new File("C:/Gazetteers/organizationT.txt");
	public static File personNameList= new File("C:/Gazetteers/personname.txt");
	public static File stopwordList= new File("C:/Gazetteers/stoplist.txt");
	
	public String[] expandQuery(String []query, String qType) throws IOException{
		
		String []result = new String[10];// I randomly give 10 for the size
		
		String []expandingTermM= {" ቀን ቦታ ተወለደ"," ክፍለ ሀገር ተወለደ"," ትምህርት ቤት ተማረ", " ታዋቂ", " ልጆች  ወለደ አባት"," ቀን ሞተ ተቀበረ"};
		String []expandingTermF= {" ቀን ቦታ ተወለደች"," ክፍለ ሀገር ተወለደች"," ትምህርት ቤት ተማችረ", " ታዋቂ", " ልጆች  ወለደች እናት"," ቀን ሞተች ተቀበረች"};
		String []expandingTermB= {" ቀን ቦታ ተወለዱ"," ክፍለ ሀገር ተወለዱ"," ትምህርት ቤት ተማሩ", " ታዋቂ"," ቀን ሞቱ ተቀበሩ"};// " ልጆች  ወለዱ እናት አባት",
		String []expandingTermO= {" ቀን ቦታ ተመሰረተ"," ቀን ቦታ ተቋቋመ"," ታዋቂ አተረፈ ከሰረ", " ለሀገር ሰራ ሰራተኞች ማናጀር"," ዘርፍ ከፍተኛ ትልቅ ሚና አስተዋጽኦ አድርጓል ሥራ ሰርቷል፡፡"};
		
		String mainQuery=query[0];
		String sex= query[1];		
		if(qType=="Biography" && sex=="Male")
		{
			
			if(!check(mainQuery,QueryExpansion.organizationListFile))
			for (int i = 0; i < expandingTermM.length; i++) {
				String x=mainQuery + expandingTermM[i];
				result[i]=x;
			}
			else{
				for (int i = 0; i < expandingTermO.length; i++) {
					String x=mainQuery + expandingTermO[i];
					result[i]=x;
				}
			}
				
		}
		else if(qType=="Biography" && sex=="Female")
		{
			
			if(!check(mainQuery,QueryExpansion.organizationListFile))
			for (int i = 0; i < expandingTermF.length; i++) {
				String x=mainQuery + expandingTermF[i];
				result[i]=x;
			}
			else{
				for (int i = 0; i < expandingTermO.length; i++) {
					String x=mainQuery + expandingTermO[i];
					result[i]=x;
				}
			}
				
		}
		else if(qType=="Biography" && sex=="Both")
		{			
			for (int i = 0; i < expandingTermB.length; i++) {
				String x=mainQuery + expandingTermB[i];
				result[i]=x;
			}							
		}
		else if (qType=="Definition")
		{
			String tok=null;
			StringTokenizer st= new StringTokenizer(mainQuery);
			int i=-1;
			String x="";
			 ArrayList<String> res= new ArrayList<String>();
			while (st.hasMoreElements()) {
				 tok = st.nextToken();
				res=checkSynonym(tok);
				 if(res!=null){
					 for (int j = 0; j < res.size(); j++) {
						 i++;
						 if(!tok.equals(res.get(j)))
						 {
							 x=mainQuery+" "+ res.get(j);
						 }
						result[i]=x;
					}
					 
				 }
				
			}
		}
		else if (qType=="Description")
		{
			String tok=null;
			StringTokenizer st= new StringTokenizer(mainQuery);
			StringBuffer sb;			
			int i=-1;
		 ArrayList<String> res= new ArrayList<String>();
			while (st.hasMoreElements()) {
				sb= new StringBuffer();
				i++;
				 tok = st.nextToken();
				res=checkSynonym(tok);
				 if(res!=null){
					 for (int j = 0; j < res.size(); j++) {						 
						 if(!tok.equals(res.get(j)))
						 {
							 sb.append(res.get(j)+" ");							 
						 }					
					}					 
				 }
				 if(sb.toString()!=null)
				 {result[i]=mainQuery+" "+sb.toString();}					  
			}			
		}
		result=stopFilter(result);
		//result=stemEQ(result);
		
		return result;	
	}
	public static String[]stopFilter(String[] expandedQ) throws IOException{
		//String stopList=FileUtils.openInputStream(stopwordList);
		String[] results= new String[10];
		
		FileReader fr=new FileReader(stopwordList);
		BufferedReader bf=new BufferedReader(fr);
		
		String x;
		String line = null;
		for (int i = 0; i < expandedQ.length && expandedQ[i]!=null; i++) {
			StringBuffer sb=new StringBuffer();
			StringTokenizer st= new StringTokenizer(expandedQ[i]);
			while(st.hasMoreElements()){
				x=st.nextToken();
				while((line=bf.readLine())!=null){
					if(!line.contains(x)){
						sb.append(x + " ");
						break;
					}
				}
			}
			results[i]=sb.toString();			
		}
		bf.close();
		return results;
	}
	public static String[] stemEQ(String[] expandedQ) throws IOException{
		//AmharicStemmer stemer= new AmharicStemmer();
		String[] results= new String[10];
		
		for (int i = 0; i < expandedQ.length && expandedQ[i]!=null; i++) {
			StringTokenizer st= new StringTokenizer(expandedQ[i]);
			StringBuffer sb=new StringBuffer();
			while(st.hasMoreElements()){
			//sb.append(stemer.Stem(st.nextToken()));
			//sb.append(u);
			}
			results[i]=sb.toString();
		}
		return results;
	}
	public static ArrayList<String> checkSynonym(String tok) throws IOException {
		
		ArrayList<String> result = new ArrayList<String>();
		//String [] result;
		FileReader fr=new FileReader(synonymList);
		BufferedReader bf=new BufferedReader(fr);
		StringTokenizer st;
		String x;
		String line = null;
		while((line=bf.readLine())!=null){
			if(line.contains(tok))
				{
				st= new StringTokenizer(line);
				while(st.hasMoreElements()){
					x=st.nextToken();
				if(x!=tok){
					result.add(x);
				}
				}
				}				
			}
		bf.close();
		return result;
	}
	public static boolean check(String term,File orgList) throws IOException{
		
		boolean flag=false;
		FileReader tr=new FileReader(orgList);
		BufferedReader bf=new BufferedReader(tr);
		int numberOfLines=0;
		String line = new String();
		System.out.println(term);
		while((line=bf.readLine())!=null){
			if(term.contains(line))
				{
				flag=true;
				break;
				}				
			if(line.contains(term))
				{
				flag=true;
				break;
				}
			numberOfLines++;
		}
		bf.close();
		return flag;
	}
	public static int readLines(File f ) throws IOException{
		FileReader fr=new FileReader(f);
		BufferedReader bf=new BufferedReader(fr);
		int numberOfLines=0;
		while((bf.readLine())!=null){
			numberOfLines++;
		}
		bf.close();
		return numberOfLines;
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		System.out.println("የኢትዮጵያ ");
		//String question="አርቲስት መሀሙድ አህመድ ማነው";
		//String question=" የኢትዮጵያ አየር መንገድ ማነው";
		//String question=" የኢትዮጵያ አየር መንገድ ማናት";
		String question="አትሌት መሰረት ደፋር ማናት";
		//String question="አምስተኛው የኢትዮጵያ ፓትርያርክ የነበሩት ብጹዕ አቡነ ጳውሎስ ማናቸው";
		//String question="ደን ማለት ምን ማለት ነው";
		//String question="ዩኒቨርሲቲ በአንድ ማህበረሰብ ውስጥ ያለው ፋይዳ ምንድነው";
		AnalyzeQuestion anq= new AnalyzeQuestion();
		String qType= anq.AnalyzedQuery(question);
		QueryGenerator qg= new QueryGenerator();
		
		String []query= qg.generateQuery(question); // query contains the generated query and the sex
		
		QueryExpansion qE=new QueryExpansion();
		String []expandedQuery= new String[10];
			expandedQuery=qE.expandQuery(query, qType);
		for (int i = 0; i < expandedQuery.length && expandedQuery[i]!=null; i++) {
			System.out.println(expandedQuery[i]);
		}
		
	}

}
