package answerExtraction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.ParseException;



//import summerizer.A_driverClassforNQA;

import classify.AnalyzeQuestion;
import classify.Character_Normalization;
import classify.Classify_Q;
import classify.QueryGenerator;

public class AnswerExtraction {

	/**
	 * @author Hun Tila
	 * @param args
	 * @throws IOException 
	 */
	public static String retrievedAnswer="";
	public static String Query="";
	public static String QuesType="";
	public static List<String> defPatterns= new ArrayList<String>();
	public static List<String>desPatterns= new ArrayList<String>();
	public static Map <String,Double> defPatScore= new HashMap<String, Double>();
	public static Map <String,Double> desPatScore= new HashMap<String, Double>();
	public static Map<String,Double> rankedSen= new TreeMap<String,Double> ();
	
	public static void main(String[] args) throws IOException {		
		String question = "";
//		question="የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ማናት?";
		question= "ኮምፕዩተር ማለት ምን ማለት ነው?";
//		question="ኢንተርኔት ምን ጥቅም አለው?";
		
		try {
			String ans=runEveryThing(question);
			
			FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/testDesResult.txt"),"\n*** "+question+"\tQ Type: "+AnswerExtraction.QuesType+"\n"+ans+"\n",true );
		 System.out.println(ans);
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}
	
	public static String ansExtractorDD(Map<String, Double> FilteredDocs,String query,String qType) throws Exception{
		System.out.println("U are in DD");
		retrievedAnswer="";
		rankedSen.clear();//I do this for making ranked sentence null for every question
		String candidateAns="";
		StringBuffer candidateAns2=new StringBuffer();
		StringBuffer sb= new StringBuffer();
		Map<Integer,String>tokenizedDoc= new HashMap<Integer,String>();
		Map<Integer,String> rules= new HashMap<Integer,String>();//holds the id of the pattern and its structure that match a document.
		Iterator<String> it= FilteredDocs.keySet().iterator();
		AnswerExtraction ae= new AnswerExtraction();
		 
		while(it.hasNext()){
			String res="";
			String doc=it.next();
			rules=ruleBasedExtractor(doc,query,qType); 
			candidateAns2.append(extractSentecesByRule(doc,qType,query,FilteredDocs.get(doc)));
			//The other method to extract candidate answer
			tokenizedDoc=ae.tokenizeSentence(doc);	
			Iterator<Integer> it2=tokenizedDoc.keySet().iterator();
			while(it2.hasNext()){
				int rId=it2.next();
				String sent=tokenizedDoc.get(rId);
			res=filterSentences(rules,doc);
			sb.append(res);
			}
		}
		candidateAns = sb.toString();
		System.out.println("final ans: "+ candidateAns);
		System.out.println("final ans2: "+ candidateAns2.toString());
		List<String> selectedSents=new ArrayList<String>();
		selectedSents=sentSelection();
		String finalAns=sentOrdering(selectedSents);
		System.out.println("Final Answer: "+finalAns);
		retrievedAnswer=finalAns;
		return finalAns;
	}
	
	public static String filterSentences(Map<Integer,String> rules,String doc) throws Exception{//I am not using it.
		String result="";
		AnswerExtraction ae= new AnswerExtraction();
		Map<Integer,String>tokenizedDoc= new HashMap<Integer,String>();
		StringBuffer sb= new StringBuffer();
		Iterator<Integer> itRule=rules.keySet().iterator();
		Pattern regPat;
		Matcher mm;
		tokenizedDoc=ae.tokenizeSentence(doc);	
		Iterator<Integer> it2=tokenizedDoc.keySet().iterator();
		while(it2.hasNext()){
			int sentId=it2.next();
			String sent=tokenizedDoc.get(sentId);
		while(itRule.hasNext()){
			int rId=itRule.next();
			String pat=rules.get(rId);
			regPat = Pattern.compile(pat);
			mm= regPat.matcher(sent);
			if(mm.matches()){
				sb.append(sent+" ");
			}		
		}
		}
		result=sb.toString();
		return result;
	}
	//each filtered docs by the rules will be tokenized to sentences and sentences that satisfy the rules are extracted. 
	public static String extractSentecesByRule(String doc,String qType,String query, double dScore_Lucene) throws Exception{
		
		StringBuffer res=new StringBuffer();
		Map<Integer,String>sents= new HashMap<Integer,String>();
		AnswerExtraction ae= new AnswerExtraction();
		//System.out.println("in extractSenByRule: "+doc);
		sents=ae.tokenizeSentence(doc);
		Iterator<Integer> it=sents.keySet().iterator();
		Iterator<Integer> it2=sents.keySet().iterator();
	boolean flag=false;//used for indicating whether the first sentence is detected by the rule or not.		
	if(qType.equals("Definition")){
		while(it.hasNext()){
			int sId=it.next();
			String s=sents.get(sId);
			for (int i = 0; i < defPatterns.size(); i++) {
				String patt=defPatterns.get(i);
				Pattern p=Pattern.compile(patt);
				Matcher m= p.matcher(s);
				//if(m.matches()){//there is difference when using find() and matches() functions
				if(m.find()){
					flag=true;
					res.append(s+"\n");
					double score=sentRanking(s,patt,qType,query,dScore_Lucene,sId);
					rankedSen.put(s,score);
				}
				if(sId==1&&flag){
					int c=0;
						while(it2.hasNext()&&c<=3){
							c=it2.next();
							String txt=sents.get(c);
							double x=0.0;
							if(c!=1){
								x=sentRanking(txt,patt,qType,query,dScore_Lucene,c);
								rankedSen.put(txt, x);
							}
						}
					}
		}
			
		}
		}
		if(qType.equals("Description")){
			while(it.hasNext()){
				int sId=it.next();
				String s=sents.get(sId);
				for (int i = 0; i < desPatterns.size(); i++) {
					String patt=desPatterns.get(i);
					Pattern p=Pattern.compile(patt);
					Matcher m= p.matcher(s);
					if(m.find()){
						res.append(s+"\n");
						double score=sentRanking(s,patt,qType,query,dScore_Lucene,sId);
						rankedSen.put(s,score);
					}			
			}
			}
		}
		
		return res.toString();
	}
	//used to calculate the score every extracted sentence
	public static double sentRanking(String sen,String pat,String qType, String query, double dScore_Luce,int sPsition){
		List<Double> positionScoreDef=new ArrayList<Double>();//holds score of a sentence based on its position in the doc
		positionScoreDef.add(1.0);//if the position of the sentence is 1(first) 
		positionScoreDef.add(0.90);//if the position of the sentence is 2(second)
		positionScoreDef.add(0.80);//if the position of the sentence is 3(third)
		positionScoreDef.add(0.50);//if the position of the sentence is 4and 5(fourth and fifth)
		positionScoreDef.add(0.2);//if the position of the sentence is >=6(sixth and above)
			
		List<Double> positionScoreDes=new ArrayList<Double>();
		positionScoreDes.add(0.2);//if the position of the sentence is 1(first) 
		positionScoreDes.add(0.5);//if the position of the sentence is 2(second)
		positionScoreDes.add(0.8);//if the position of the sentence is 3(third)
		positionScoreDes.add(0.9);//if the position of the sentence is 4and 5(fourth and fifth)
		positionScoreDes.add(0.5);//if the position of the sentence is >=6(sixth and above)???
		
		Map<String, Double> sortedRankedSen= new HashMap<String, Double>();
		double score=0.0;
		double positionScore=0.0;
		double percentQTermsin_Sent=0.0;
		double patScore=0.0;
		boolean flag=false;
		StringTokenizer st= new StringTokenizer(query);
		double count=0.0;
		String[] count_Query=StringUtils.split(query);
		double QTermsLength=count_Query.length;
		if(qType.equals("Definition")){
			count=0.0;
			while (st.hasMoreTokens()) {
			String queryTerm= st.nextToken();
			flag=StringUtils.contains(sen,queryTerm);
			if(flag)
				{
				count++;
				}
			}
			
			percentQTermsin_Sent=count/QTermsLength;
			
			Iterator<String> it= defPatScore.keySet().iterator();
			while(it.hasNext()){
				String p=it.next();
				if(p.trim().equals(pat.trim())){//pat is the pattern that identifies sentence sen
					patScore=defPatScore.get(p);
					positionScore=1/sPsition;
					score=dScore_Luce+percentQTermsin_Sent+patScore+positionScore;	
					}
			}			
		}
		
		else if(qType.equals("Description")){
			while (st.hasMoreTokens()) {
				String queryTerm= st.nextToken();
				flag=StringUtils.contains(sen,queryTerm);
				if(flag)
					{
					count++;
					}
				}
				
				percentQTermsin_Sent=count/QTermsLength;
				
				Iterator<String> it2= desPatScore.keySet().iterator();
				while(it2.hasNext()){
					String p=it2.next();
					if(p.trim().equals(pat.trim())){
						patScore=desPatScore.get(p);
						
						if(sPsition==0)
							positionScore=positionScoreDes.get(0);
						else if(sPsition==1)
							positionScore=positionScoreDes.get(1);
						else if(sPsition==2)
							positionScore=positionScoreDes.get(2);
						else if(sPsition==3||sPsition==4)
							positionScore=positionScoreDes.get(3);
						else 
							positionScore=positionScoreDes.get(4);
						score=dScore_Luce+percentQTermsin_Sent+patScore;
					}
				}
				
		}
		
		return score;
	}
	//sort the scored sentences,filter similar sentences and produce final sentences used for def/des
	public static List<String> sentSelection(){
		
		Map<Double, Integer> sortRank=new TreeMap<Double, Integer>();
		int size=rankedSen.size();
		int c=size-1;
		String[] s= new String[size];
		Double[] sco= new Double[size];
		Iterator<String> a= rankedSen.keySet().iterator();
		while(a.hasNext()){
			String x=a.next();
			s[c]=x;
			sco[c]=rankedSen.get(x);
			c--;			
		}
		 
		 String temp;
		 double tempS;
		for (int i = 0; i < sco.length; i++) {
			for (int j = sco.length-1; j>i; j--) {
				if(sco[j]>sco[j-1]){
					//Swap the score
					tempS=sco[j];
					sco[j]=sco[j-1];
					sco[j-1]=tempS;
					//Swap the sentences too
					temp=s[j];
					s[j]=s[j-1];
					s[j-1]=temp;
				}
			}
		}
		double[][] sentSim=new double[s.length][s.length];
		List<String> ansPool=new ArrayList<String>();
			
		if(s.length!=0)
			{ansPool.add(s[0]);}
		
		for (int k = 1; k < s.length; k++) {
			boolean flag=addToPool(s[k],ansPool);
			if (flag) {
				ansPool.add(s[k]);
			}
		}
		System.out.println("******** This is Sentence Selection function +++++++++++ Final selected sentences: ");
		if(ansPool.isEmpty())
			System.out.println("No answer is extracted from the candidate sentences");
		else{
		for (int i = 0; i < ansPool.size(); i++) {
			System.out.println(ansPool.get(i));
		}
		}
		return ansPool;
	}
	public static boolean addToPool (String s,List<String> apool){
		boolean flag=false;
		int i=0;
		double sim=0.0;
		while(i<apool.size()){
			sim=0.0;
			sim=computeSenSim(s,apool.get(i));
			if(sim<=0.5)
				{flag=true;}
			i++;
		}
		return flag;
	}
	public static double computeSenSim(String sA,String sB){
		double sim=0.0;
		String[] termA=StringUtils.split(sA);
		String[] termB=StringUtils.split(sB);
		double nA=termA.length;
		double nB=termB.length;
		double commonTerms=0.0;
		for (int i = 0; i < termA.length; i++) {
			if(sB.contains(termA[i])){
				commonTerms++;
			}
		}
		double minA_OR_B=0.0;
		if(nA>=nB)
			 minA_OR_B=nB;
		else
			minA_OR_B=nA;
		
		if(minA_OR_B!=0){
			sim=commonTerms/minA_OR_B;
		}
		return sim;
	}
	public static String sentOrdering(List<String> cand){
		String res="";
		List<String> orderedSen=new ArrayList<String>();
		if(AnswerExtraction.QuesType.equals("Definition")){
		
		String[] qTer=StringUtils.split(Query);
		
		for (int i = 0; i < cand.size(); i++) {
			String[] terms=StringUtils.split(cand.get(i));
			if(terms[0].equals(qTer[0])){
					orderedSen.add(cand.get(i));
					cand.remove(i);
			}
		}
		}
		/*else if(AnswerExtraction.QuesType.equals("Description")){
			String q=extractTarget(Query);
			String[] qTer=StringUtils.split(Query);
			for (int i = 0; i < cand.size(); i++) {
				String[] terms=StringUtils.split(cand.get(i));
				if(terms[0].equals(qTer[0])){
						orderedSen.add(cand.get(i));
						cand.remove(i);
				}
			}
		}*/
		StringBuilder sb= new StringBuilder();
		
		if(orderedSen.size()<5){
		for (int i = 0; i <cand.size()&&orderedSen.size()<=5; i++) {
			orderedSen.add(cand.get(i));
//			sb.append(cand.get(i));
		}
		}
		for (int i = 0; i < orderedSen.size(); i++) {
			sb.append(orderedSen.get(i));
		}
		res=sb.toString();
		return res;
		
	}
	public static Map<Integer,String> ruleBasedExtractor(String doc,String query,String qType){
		//System.out.println("In rule Based extrator: "+doc);
		Map<Integer,String> result= new HashMap<Integer,String>();
		
		String pat1=query+" ማለት " +".*";//W weight=1
		String pat2=query+"( ማለት )?"+".*"+" ማለት  ነው[::|።|፡፡]";//W weight=1
		//String pat1= query+"(ማለት)?"+ "(.* ነው፡፡)";
		String pat3 =query +" .* ትርጉ[ሙ|ም]|ፍቺው (.*|ማለት ነው[::|።|፡፡])";//w weight=1
		String pat4 =".*"+query +".*"+" ወይም "+".*";//W weight=0.5
		String pat13 =".*"+ " ወይም "+query+" .*" ;//??
		String pat5 =".*"+ query+" .*"+"( ሊተረጎም| ሊፈታ| ሊጠራ| ነው ማለት)?"+"( ይችላል| ይቻላል([::|።|፡፡]))";//W weight=0.5
		String pat6 =query + " .*"+"( ሲል| ሲሆን| ሲባል)"+" .*";//W weight=0.5 but try to incorporate punctuation marks or remove them during pre-processing.
		String pat7 =".* " + query +" ይባላል[::|።|፡፡]";//w weight=0.7
		String pat8=".* "+query +" .* "+"( በመባል |ተብሎ| ተብሎም )?"+"( ይታወቃል[::|።|፡፡]| ይተረጎማል[::|።|፡፡]| ይፈታል[::|።|፡፡]| ይጠራል[::|።|፡፡])";//W weight=0.5
		String pat9 =query +" .*"+"( ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡])";//W weight= 0.6
		String pat10 =query+" .* "+"ነው ማለት ይቻላል[::|።|፡፡]";//W weight=0.6
		String pat11=".*"+" ማለት "+".* " +"([በ|ከ|የ])?"+query+" .*";// weight=0.6 properly check this rule
		String pat12=".* "+query+" ማለት "+".* "; //	weight=0.7
		String pat14= ".*"+"\\("+query+"\\)" +".*"+"( ማለት )?"+".*";// weight=0.5
		//String pat15=".* "+"([በ|ከ|የ])"+query+" .*";
		defPatterns.add(pat1);
		defPatterns.add(pat2);
		defPatterns.add(pat3);
		defPatterns.add(pat4);
		defPatterns.add(pat5);
		defPatterns.add(pat6);
		defPatterns.add(pat7);
		defPatterns.add(pat8);
		defPatterns.add(pat9);
		defPatterns.add(pat10);
		defPatterns.add(pat11);
		defPatterns.add(pat12);
		defPatterns.add(pat13);
		defPatterns.add(pat14);
		//defPatterns.add(pat15);
		//={pat1,pat2,pat3,pat4,pat5,pat6,pat7,pat8,pat9,pat10,pat11,pat12,pat13};
		defPatScore.put(pat1, 1.0);
		defPatScore.put(pat2, 1.0);
		defPatScore.put(pat3, 1.0);
		defPatScore.put(pat4, 0.5);
		defPatScore.put(pat5, 0.5);
		defPatScore.put(pat6, 0.5);
		defPatScore.put(pat7, 0.7);
		defPatScore.put(pat8, 0.5);
		defPatScore.put(pat9, 0.6);//0.6
		defPatScore.put(pat10, 0.6);
		defPatScore.put(pat11, 0.6);
		defPatScore.put(pat12, 0.7);
		defPatScore.put(pat13, 0.5);
		defPatScore.put(pat14, 0.5);
		
		String Target=extractTarget(query);
		String pat_des1=Target+" .*"+"( ጥቅ[ም|ሙ|ሞች|ሙም|ማቸው] | ሚና[ውም|ው|ዎች|ዎቹ |ዎቿ|ቸው] |ድርሻ[ም|ሙ|ሞች] | አገልግሎት[ቱ|ቶች]|ተግባ[ር|ሩ|ራት])"+".*" + "( ያለው | ያላቸው | ያላት )?"+ ".*"+"( ይውላል[::|።|፡፡]| ትውላለች[::|።|፡፡]| ይውላሉ[::|።|፡፡] |አላት[::|።|፡፡]|አላቸው[::|።|፡፡]| አለው[::|።|፡፡]| አሉት[::|።|፡፡]| ይኖሩታል[::|።|፡፡]| ይሰጣል[::|።|፡፡]| ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡|፡፡])?";//W weight=0.7
		String pat_des2=Target+" .*"+"( ጥቅ[ም|ሙ|ሞች|ሙም|ማቸው] | ሚና[ውም|ው|ዎች|ዎቹ |ዎቿ|ቸው] |ድርሻ[ም|ሙ|ሞች] | አገልግሎት[ቱ|ቶች]|ተግባ[ር|ሩ|ራት])?"+" .*" + "( ያለው | ያላቸው | ያላት )?" +".*" + "( ይውላል[::|።|፡፡]| ትውላለች[::|።|፡፡]| ይውላሉ[::|።|፡፡] |አላት[::|።|፡፡]|አላቸው[::|።|፡፡]| አለው[::|።|፡፡]| አሉት[::|።|፡፡]| ይኖሩታል[::|።|፡፡]| ይሰጣል[::|።|፡፡]| ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡|፡፡])";//W weight=0.7
		String pat_des3=Target+" .*"+"ተግባር (ሊጠቅም|ሊያገለግል)? (ይችላል[::|።|፡፡]|አላት[::|።|፡፡]|አላቸው[::|።|፡፡]| አለው[::|።|፡፡]| አሉት[::|።|፡፡])";// weight=0.5
		String pat_des4=Target+" .*"+"( ስለሚጠቅም | ይጠቅማል[::|።|፡፡]| ያገለግላል[::|።|፡፡])";//W weight=0.6
		String pat_des5=Target+" .*"+"( የሚጠቅም| የሚያስችል| የሚጠቅሙ| የሚያስችሉ| የምታስችል| የምትጠቅም)"+".*"+"( ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡])";// weight=0.8 it works but could also retrieve irrelevant docs
		String pat_des6=Target+".*"+ " ጥቅ[ም|ሙ|ሞች|ሙም|ማቸው] | ተግባር "+"(.*|ነው[::|።|፡፡])"; 
		String pat_des7=Target+" .*"+ "ጥቅም "+".*";	
		desPatterns.add(pat_des1);
		desPatterns.add(pat_des2);
		desPatterns.add(pat_des3);
		desPatterns.add(pat_des4);
		desPatterns.add(pat_des5);
		desPatterns.add(pat_des6);
		//desPatterns.add(pat_des7);

		desPatScore.put(pat_des1, 0.7);
		desPatScore.put(pat_des2, 0.7);
		desPatScore.put(pat_des3, 0.5);
		desPatScore.put(pat_des4, 0.6);
		desPatScore.put(pat_des5, 0.8);
		desPatScore.put(pat_des6, 0.7);
		//desPatScore.put(pat_des7, 0.8);
		if(qType.equals("Definition")) {
			for (int i = 0; i < defPatterns.size(); i++) {
				Pattern defPRes=Pattern.compile(defPatterns.get(i));
				Matcher defM= defPRes.matcher(doc);
				if(defM.find())//.matches())
					{					
					result.put(i,defPatterns.get(i));
					}			
		}
		}
		if(qType.equals("Description")){			
			for (int j = 0; j <desPatterns.size(); j++) {
				Pattern pat=Pattern.compile(desPatterns.get(j));
				Matcher matcher= pat.matcher(doc);
				if(matcher.find())//.matches())
					{
					result.put(j,desPatterns.get(j));
					}
				}		
				}
		return result; 
	}
	public static String extractTarget(String query){
		String res="";
		String[] terms=StringUtils.split("ጥቅም ፋይዳ አገልግሎት ሚና ተግባር ሚናው ጥቅሙ ፋይዳው አገልግሎቱ ድርሻው ተግባሩ ጥቅሟ ፋይዳዋ አገልግሎቷ ሚናዋ ድርሻዋ ተግባሯ ጥቅማቸው ፋይዳቸው አገልግሎታቸው ሚናቸው ድርሻቸው ተግባራቸው");
		for (int i = 0; i < terms.length; i++) {
			if(query.contains(terms[i]))
				res=query.replace(terms[i], "");
		}
		return res;
	}
  public Map<Integer,String> tokenizeSentence(String m)throws Exception
	    {     
	    Map<Integer,String> sentenceid = new TreeMap<Integer,String>();
	   
	    String hulet = "'\u1361'+'\u1361'";
	    String[] array={"'::'","'።'","'፡፡'"};
//	    String aratNetib="'\u1362'";
	    m=replaceArat_Hulet(m);
	    Integer k=1;
	    String[] results=null;
	    if(QuesType.equals("Definition"))
	    	 results = StringUtils.split(m,"'፡፡'");
	    else if(QuesType=="Description")
	    	results = StringUtils.split(m,"'፡፡'");
	   for(int i=0; i<results.length;i++)//subtract 1 from length when applied for Amharic
	   {
	       results[i]= results[i].trim();
	       sentenceid.put(k,results[i]+"፡፡ ");
	       k++;
	   }	    
	     return sentenceid;
	   }
	public static String ansExtractorBio(Map<String, Double> FilteredDocs,String query,String qType) throws IOException{
	System.out.println("You are in Biography Answer Extractor!");
		String answer="";
		AnswerExtraction ae= new AnswerExtraction();	
		int i=1;
		Iterator<String> it = FilteredDocs.keySet().iterator();
		while (it.hasNext()) {
			String ff=it.next();
			FileOutputStream fout = new FileOutputStream(new File("C:/NQA_Final/filesTobeSummerized/"+i+".txt"));
	     	OutputStreamWriter rt = new OutputStreamWriter (fout,"utf-16");
	     	BufferedWriter rtt = new BufferedWriter(rt);
	     	rtt.write(ff);
			rtt.close();
			//System.out.println(ff);
			i++;
		}
				
		JOptionPane.showMessageDialog(null, "Run Summerizer");
		String candidateAnswer=FileUtils.readFileToString(new File("C:/NQA_Final/files_summerizerResult/Result/summurized.txt"), "utf-16");
		String res=TextCategorization.svmSummeryCategorization(null);//parameter was null
		if(TextCategorization.tyRes==1)
			{
			answer=candidateAnswer;
			retrievedAnswer=candidateAnswer;
			System.out.println("The Summerizer result: "+candidateAnswer);
		}
		else if(TextCategorization.tyRes==2){//(res=="Good Biography"){
			System.out.println("do further analysis");	
			answer=candidateAnswer;
			retrievedAnswer=candidateAnswer;
			JOptionPane.showMessageDialog(null, " You need to do further analysis");
			}
		else if(TextCategorization.tyRes==3){
			answer="";
			System.out.println("bad bio");	
			retrievedAnswer="";
			//JOptionPane.showMessageDialog(null, " You need to do further analysis");
		}
		else{
			answer="";
			retrievedAnswer="";
		}
		return answer;	
			
	}
	public static String runEveryThing(String question) throws Exception{
		String answer="";
		Map<String,Double> IR_Result=new HashMap<String, Double>();
		Map<String,Double> FilteredDocs=new HashMap<String, Double>();
		AnswerExtraction nqa= new AnswerExtraction();
		Character_Normalization cn= new Character_Normalization();
		File docDir = new File("C:/NQA_Final/corpus/data/");
        try {			
			String []qT_query=nqa.findQType_Query(question);
			String qType=qT_query[0];
			System.out.println(qType);
			String query=qT_query[1];
			LuceneIndexing.indexData(docDir);
			IR_Result=LuceneSearch.search(query,10);
			FilteredDocs=documentFiltering(IR_Result,query,qType);
			if((qType.equals("Definition") || qType.equals("Description"))&& !FilteredDocs.isEmpty()){
				answer=ansExtractorDD(FilteredDocs,query,qType);
			}
			else if(qType.equals("Biography") && !FilteredDocs.isEmpty()){
				answer=ansExtractorBio(FilteredDocs,query,qType);
			}
			else{
				System.out.println("In runEverything ======= Sorry the retrived documents are not relevant for your question!");
			}
		} catch (IOException e) {		
			e.printStackTrace();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return answer;
		
		
	}
	public String[] findQType_Query(String question) throws IOException{
		String qtype="",qtypeRB="";
		Character_Normalization cn= new Character_Normalization();
		question=cn.Normalizedoc(question);
        qtype=Classify_Q.svmQType(question);
        System.out.println("SVM :"+ qtype);
        AnalyzeQuestion anq= new AnalyzeQuestion();
		//System.out.println("The Answer Type by Rule Based classifier is: "+anq.AnalyzedQuery(question));
        qtypeRB=anq.AnalyzedQuery(question);
        System.out.println("RB :"+ qtypeRB);
        QueryGenerator qg= new QueryGenerator();
        String []query= qg.generateQuery(question); // query contains the generated query and the sex
        
        
        String finalQType="";
        if (qtype.equals(qtypeRB)){
			finalQType=qtypeRB;		
		}
        else if(qtype.equals("Unknown") && !qtypeRB.equals("Unknown")){
			finalQType=qtypeRB;						
		}
        else if(qtype.equals("Unknown") && qtypeRB.equals("Unknown")){
			finalQType="Unknown";						
        }
        else if(!qtype.equals(qtypeRB)){
			finalQType=qtypeRB;						
		}
      /* QueryExpansion qExp= new QueryExpansion();
        String []expandedQuery= new String[10];
        if(finalQType!="unknown" && finalQType!=""){
		expandedQuery=qExp.expandQuery(query, finalQType);
        }
        StringBuffer expQuery=new StringBuffer();
        
        for (int i = 0; i < expandedQuery.length && expandedQuery[i]!=null; i++) {
			System.out.println(expandedQuery[i]);
        	expQuery.append(expandedQuery[i]+"\n");
			}
              
              Ans=searchAns(finalQType,expQuery.toString());
              String[] result={"SVM: "+qtype+" and RB: "+qtypeRB,expQuery.toString(),Ans};
       return result;
		}
		else
			{return null;}*/
        Query=query[0];
        QuesType=finalQType;
        String[]queryandQType={finalQType,query[0]};
        
			return queryandQType;
	}
	
	
	//docFiltering method checks whether each retrieved docs contains __% of the query 
	public static Map<String, Double> documentFiltering(Map<String,Double> retrievedDocs,String query,String qType) throws IOException{
		Map<String,Double> FilteredDocs=new HashMap<String,Double>();
		boolean flag=false;
		String[] count_Query=StringUtils.split(query);
		double QTermsLength=count_Query.length;
		
		double percentQTermsin_Docs;
		Character_Normalization cn= new Character_Normalization();
		Iterator<String> it = retrievedDocs.keySet().iterator();
		while (it.hasNext()) {
			double count=0.0;//used to hold how many query terms found in document
			String fileName=it.next().toString();
			String doc=FileUtils.readFileToString(new File("C:/NQA_Final/corpus/data/"+fileName), "utf-16");
			doc=cn.Normalizedoc(doc);			
			StringTokenizer st= new StringTokenizer(query);
			while (st.hasMoreTokens()) {
			String queryTerm= st.nextToken();
			flag=StringUtils.contains(doc,queryTerm);
			if(flag)
				{
				count++;
				}
			}
			percentQTermsin_Docs=count/QTermsLength;// this formula used to calculate the percent of query terms in a retrieved document
			double docScore= percentQTermsin_Docs+ retrievedDocs.get(fileName);
			if(qType.equals("Definition")){
				if(percentQTermsin_Docs>=0.3)//30% is used as a cut off point to remove a retrieved doc.
				{
					doc=replaceArat_Hulet(doc);
					FilteredDocs.put(doc,retrievedDocs.get(fileName));
				}
			}
			else if(qType.equals("Description")){
				String target=extractTarget(query);
				if(percentQTermsin_Docs>=0.75)
				{
					doc=replaceArat_Hulet(doc);
					FilteredDocs.put(doc,retrievedDocs.get(fileName));
				}
			}
			else if(qType.equals("Biography")){
				if(percentQTermsin_Docs>=0.75)//75% is used as a cut off point to remove a retrieved doc.
				//(Since the query is a name of organization/person, most of the query terms must found in the retrieved doc)
				{
					doc=replaceArat_Hulet(doc);
					String title=returntitle(doc);
					if(title.contains(query)|| query.contains(title))
						FilteredDocs.put(doc,retrievedDocs.get(fileName));
				}
			}
		}
		return FilteredDocs;
	}
	public static boolean checkQueryTerms(String doc, String queryTerm) throws IOException {//I didn't used this function -- the function tries to find term frequency through out the group /terms/files
		int count=0;	
		boolean a=StringUtils.contains(doc, queryTerm);
		String[] q=StringUtils.split(queryTerm);
		boolean[] flag=new boolean[q.length];
		StringTokenizer st= new StringTokenizer(queryTerm);
		int i=0;
		int c=0;
		while(st.hasMoreElements()){
			String x=st.nextToken();
			if(StringUtils.contains(doc,x))
				{
				flag[i]= true;
				c++;
				}
			i++;
		}
		if(q.length==c)
			return true;
		else
			return false;
	}
	public static String returntitle(String m)// returns the title of a document
	  {
	   String hulet = "'\u1361'+'\u1361'";
	    String title="";
	   String[] results = StringUtils.split(m,hulet);
	   for(int i=0; i<1;i++)
	   {
	       results[i]= results[i].trim();
	     title = results[i];
	   }
	   return title;
	  }
	public static String replaceArat_Hulet(String doc) throws IOException{
		int flag=0;
		String term; 
		int countSent=1;
		StringBuffer sb1= new StringBuffer();
		String temp,temp2 = null;
		StringTokenizer st= new StringTokenizer(doc);
		while(st.hasMoreElements()){
			flag=0;
			StringBuffer sb= new StringBuffer();
			term=st.nextToken();
			sb.append(term);
			temp=sb.reverse().toString();
			if(temp.charAt(0)=='።'){
				sb.deleteCharAt(0);
				temp2=sb.reverse().toString();
				flag=1;
			}
			else if(temp.charAt(0)=='፡'){
				sb.deleteCharAt(0);
				String x=sb.toString();
				if(!x.isEmpty())
					if(x.charAt(0)=='፡')
					{
					sb.deleteCharAt(0);
					temp2=sb.reverse().toString();
					flag=1;
					}
			}
			else{				
				temp2=sb.reverse().toString();
			}
			if(temp2!=null&& flag==1){
				sb1.append(temp2+"፡፡ ");		
			}
			else
				sb1.append(temp2+" ");
		}
		
		return sb1.toString();
	}
	

}
