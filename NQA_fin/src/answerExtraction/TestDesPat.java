package answerExtraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class TestDesPat {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ኮምፒዩተር");
		int x=StringUtils.getLevenshteinDistance("ኮምፕዩተር", "ኮምፕዩተርማቸው");
		
		System.out.println(x);
		//ruleBasedExtractor(null);
		
	}
	public static void ruleBasedExtractor(String doc) throws Exception{
		//System.out.println("In rule Based extrator: "+doc);
		Map<Integer,String> result= new HashMap<Integer,String>();
		
		doc="ኮምፕዩተር ማንኛውም ኢንፎርሜሽን ፕሮሰስ የሚያረግ ጥቅም ያለው ማሽን ነው:: ይህም የሚደረገው በተርታ የተቀመጡ ትዕዛዞች በመፈጸም ነው:: ለብዙ ሰዎች ኮምፒዩተር ሲባል ዐዕምሯቸው ላይ የሚመጣው የግል (ፐርሰናል) ኮምፒዩተር ነው:: ኮምፒዩተር ብዙ ተግባር አለው::";
			String Target="ኮምፕዩተር ";
			String pat_des1=Target+" .*"+"( ጥቅ[ም|ሙ|ሞች|ሙም|ማቸው] " +
					"| ሚና[ውም|ው|ዎች|ዎቹ |ዎቿ|ቸው] |ድርሻ[ም|ሙ|ሞች] | አገልግሎት[ቱ|ቶች]|ተግባ[ር|ሩ|ራት])"+".*" +
					"( ያለው | ያላቸው | ያላት )?"+ ".*"+"(( ይውላል| ትውላለች| ይውላሉ|አላት|አላቸው| አለው| አሉት| ይኖሩታል| ይሰጣል)[::|።|፡፡|፡፡])?";//W weight=0.7//| ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡|፡፡]
			String pat_des2=Target+" .*"+
							"( ጥቅ[ም|ሙ|ሞች|ሙም|ማቸው] | ሚና[ውም|ው|ዎች|ዎቹ |ዎቿ|ቸው] |ድርሻ[ም|ሙ|ሞች] | አገልግሎት[ቱ|ቶች]|ተግባ[ር|ሩ|ራት])?"+
							" .*" + "( ያለው | ያላቸው | ያላት )?" +".*" + "(( ይውላል| ትውላለች| ይውላሉ|አላት|አላቸው| አለው| አሉት| ይኖሩታል| ይሰጣል)[::|።|፡፡|፡፡])";//W weight=0.7//| ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡|፡፡]
			String pat_des3=Target+" .* "+"ተግባር (ሊጠቅም|ሊያገለግል)? (ይችላል[::|።|፡፡]|አላት[::|።|፡፡]|አላቸው[::|።|፡፡]| አለው[::|።|፡፡]| አሉት[::|።|፡፡])";// weight=0.5
			String pat_des4=Target+" .*"+"( ስለሚጠቅም | ይጠቅማል[::|።|፡፡]| ያገለግላል[::|።|፡፡])";//W weight=0.6
			String pat_des5=Target+" .*"+"( የሚጠቅም| የሚያስችል| የሚጠቅሙ| የሚያስችሉ| የምታስችል| የምትጠቅም)"+".*"+"( ናት[::|።|፡፡]| ናቸው[::|።|፡፡]| ነው[::|።|፡፡])";// weight=0.8 it works but could also retrieve irrelevant docs
			String pat_des6=Target+".*"+ " ጥቅም | ተግባር "+"(.*|ነው[::|።|፡፡])"; 
			String pat_des7=Target+".*"+ "ጥቅም "+".*";
			String pat_des8=Target+".*";
			
		List<String> desPatterns= new ArrayList<String>();
			desPatterns.add(pat_des1);
		desPatterns.add(pat_des2);
		desPatterns.add(pat_des3);
		desPatterns.add(pat_des4);
		desPatterns.add(pat_des5);
		desPatterns.add(pat_des6);
		desPatterns.add(pat_des7);
		desPatterns.add(pat_des8);
		Pattern pat;
		Matcher matcher;
		StringBuffer sb= new StringBuffer();
		Map<Integer,String> sentenceid = new TreeMap<Integer,String>();
				sentenceid=tokenizeSentence(doc);
				Iterator<Integer> it= sentenceid.keySet().iterator();
				while(it.hasNext()){
					int id=it.next();
					String s=sentenceid.get(id);
			for (int j = 0; j <desPatterns.size(); j++) {
//				System.out.println(desPatterns.get(j));
				pat=Pattern.compile(desPatterns.get(j));
				System.out.println(s+" : "+pat);
				matcher= pat.matcher(s);
				if(matcher.find())//.matches())//
					{
					sb.append(s+"=="+desPatterns.get(j));
//					System.out.println(desPatterns.get(j));
//					System.out.println("extre: "+ s);
					}
				}
				}
				System.out.println(sb.toString());		
				
		//return result; 
	}
	public static Map<Integer,String> tokenizeSentence(String m)throws Exception
    {     
//  System.out.println("I am in tokenizer: "+ m);
    Map<Integer,String> sentenceid = new TreeMap<Integer,String>();
   
    
    Integer k=1;
    String[] results=null;
    
    	results = StringUtils.split(m,"'::'");
   
   for(int i=0; i<results.length;i++)//subtract 1 from length when applied for amharic
   {
       results[i]= results[i].trim();
       sentenceid.put(k,results[i]+"፡፡ ");
       //System.out.println(results[i]+"::");
       //System.out.println(results[i]+"፡፡");
       k++;
   }	    
     return sentenceid;
    }

}
