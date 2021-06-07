package index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import classify.Character_Normalization;

/**
 * Title: AmharicAnalyzer Description: build from a AmharicTokenizer, filtered
 * with AmharicStopFilter and AmharicStemFilter.
 * 
 */

public class AmharicAnalyzer extends Analyzer {

	public AmharicAnalyzer() {
	}

	/**
	 * Creates a TokenStream which tokenizes all the text in the provided
	 * Reader.
	 * 
	 * @return A TokenStream build from AmharicTokenizer filtered with
	 *         AmharicstoFilter amd AmharicStemFilter.
	 */
	public final TokenStream tokenStream(String fieldName, Reader reader){
		TokenStream result=null;
		try {
			result = new Amharic_Tokenizer(reader);
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		StringTokenizer st= new StringTokenizer(result.toString());
		try {
			for (String token = st.nextToken(); token != null; token = st.nextToken()) {
				String term = token.toString();
				shortword expand = new shortword();
				term = expand.Expander(term);
				sb.append(term + " ");
			}
		} catch (Exception e) {
		}
		String sentence = sb.toString();
		TokenStream results=null;
		try {
			results = new Amharic_Tokenizer(new StringReader(sentence));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		results = new AmharicStopFilter(results);
		results = new AmharicStemFilter(results);
		return results;
	}
	public static void main(String args[])throws IOException{
		//String stemmedWord=null;
		AmharicAnalyzer an= new AmharicAnalyzer();
		File dir= new File("C:/NQA/NFQA/Corpus/");
		File [] files=dir.listFiles();
		String writeToF="c:/NQA/NFQA/T_N_doc/";
		//int j=1;
		//StringBuffer sb;
		StringBuilder sBuilder;
		for (int i = 0; i < 10; i++) {//files.length
			//sb=new StringBuffer();
			sBuilder=new StringBuilder();
			String f1=FileUtils.readFileToString(files[i].getAbsoluteFile(),"utf-16");
			sBuilder.append(an.main2(f1));
			
			//writeFile(sb.toString(), writeToF+j+".txt");
			writeFile(sBuilder.toString(), writeToF+i+".txt");
			//j++;
			//rtt.write(sb.toString());
		}
		
	}
	
	public String main2(String data) throws IOException{
		String res=null;
		String stemmedWord = null;
		StringBuilder sb= new StringBuilder();
		Character_Normalization cn=new Character_Normalization();
		shortword shortw= new shortword();
		StopStemmer stopSm=new StopStemmer();
		AmharicStemmer mainStemmer= new AmharicStemmer();
		
		//Amharic_Tokenizer result = new Amharic_Tokenizer(new StringReader(f1));
		//result.next();
		StringTokenizer token= new StringTokenizer(data);
		while (token.hasMoreElements()) {
			String x = token.nextToken();
			x=cn.Normalizedoc(x);
			x=shortw.Expander(x);
			//x=result.chunkTerm(x);
			
			if(!stopSm.isStop(x)){
				stemmedWord=mainStemmer.Stem(x);
			}
		sb.append(" "+stemmedWord);	
		}
		res=sb.toString();
		System.out.println(res+"ስራ");
		return res;
	}
	public static void writeFile(String doc,String writeTo) throws IOException{
		FileOutputStream fout = new FileOutputStream(new File(writeTo));
		OutputStreamWriter rt = new OutputStreamWriter(fout, "utf-8");
		BufferedWriter rtt = new BufferedWriter(rt);
		FileUtils.writeStringToFile(new File(writeTo), doc.toString());
		//rtt.write(doc.toString()+"።");
		rtt.close();
	}
}