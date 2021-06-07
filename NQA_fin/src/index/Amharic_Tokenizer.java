package index;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.analysis.Token;

public class Amharic_Tokenizer extends CharTokenizer {
	
	public Amharic_Tokenizer(Reader in) throws IOException{
		super(in);
			
	}

	/**
	 * Collects only characters which do not satisfy
	 * {@link Character#isWhitespace(char)}.
	 */
	protected boolean isTokenChar(char cc){
		if ((cc == '\u1360') || (cc == '\u1361') || (cc == '\u1362')
				|| (cc == '\u1363') || (cc == '\u1364') || (cc == '\u1365')
				|| (cc == '\u1366') || (cc == '\u0020') || (cc == '\u0021')
				|| (cc == '\u0009') || (cc == '\n') || (cc == '\r'))
			return false;
		else
			return true;
	}
	/**
	 * Replace characters that can be used interchangably in Amharic language
	 */
	protected char normalize(char c) {
		if (c == '\u1210') // replace HMERU HA with HALETAW HA
			return '\u1200';
		else if (c == '\u1211') // replace HU
			return '\u1201';
		else if (c == '\u1212')
			return '\u1202';
		else if (c == '\u1213')
			return '\u1203';
		else if (c == '\u1214')
			return '\u1204';
		else if (c == '\u1215')
			return '\u1205';
		else if (c == '\u1216')
			return '\u1206';
		else if (c == '\u1217')
			return '\u1207';
		else if (c == '\u1203')
			return '\u1200';

		else if (c == '\u1280')
			return '\u1200';
		else if (c == '\u1281')
			return '\u1201';
		else if (c == '\u1282')
			return '\u1202';
		else if (c == '\u1283')
			return '\u1203';
		else if (c == '\u1284')
			return '\u1204';
		else if (c == '\u1285')
			return '\u1205';
		else if (c == '\u1286')
			return '\u1206';
		else if (c == '\u128B')
			return '\u1207';

		else if (c == '\u1220') // Replace NEGUSU SE with ESATU SE
			return '\u1230';
		else if (c == '\u1221')
			return '\u1231';
		else if (c == '\u1222')
			return '\u1232';
		else if (c == '\u1223')
			return '\u1233';
		else if (c == '\u1224')
			return '\u1234';
		else if (c == '\u1225')
			return '\u1235';
		else if (c == '\u1226')
			return '\u1236';
		else if (c == '\u1227')
			return '\u1237';

		else if (c == '\u12D0') // Replace the fidel A
			return '\u12A0';
		else if (c == '\u12D1')
			return '\u12A1';
		else if (c == '\u12D2')
			return '\u12A2';
		else if (c == '\u12D3')
			return '\u12A3';
		else if (c == '\u12D4')
			return '\u12A4';
		else if (c == '\u12D5')
			return '\u12A5';
		else if (c == '\u12D6')
			return '\u12A6';

		else if (c == '\u1340') // Replace the fidel TS
			return '\u1338';
		else if (c == '\u1341')
			return '\u1339';
		else if (c == '\u1342')
			return '\u133A';
		else if (c == '\u1343')
			return '\u133B';
		else if (c == '\u1344')
			return '\u133C';
		else if (c == '\u1345')
			return '\u133D';
		else if (c == '\u1346')
			return '\u133E';
		else if (c == '\u1347')
			return '\u133F';

		else if (c == '\u1310') // Replace GO
			return '\u130E';

		else if (c == '\u12B0') // Replace KO
			return '\u12AE';

		else
			return c;

	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("hi");
		FileInputStream fin = new FileInputStream(new File("c:/NQA/NFQA/Corpus/1.txt"));
		InputStreamReader read = new InputStreamReader(fin);
		BufferedReader rd = new BufferedReader(read);
		
		FileOutputStream fout = new FileOutputStream(new File("c:/NQA/NFQA/T_N_doc/1.txt"));//kio4.txt contains words that are tokenized and their characters are normalized. 
		OutputStreamWriter rt = new OutputStreamWriter(fout);
		BufferedWriter rtt = new BufferedWriter(rt);
		String line;
		StringBuffer sb = new StringBuffer();
		
		while ((line = rd.readLine()) != null) {
			sb.append(line+" ");
			//System.out.println(line);
		}
		//sb=sb.append("á�¢");
		rd.close();
		String sentence = sb.toString();
		//Token token = null;
		Amharic_Tokenizer tk = new Amharic_Tokenizer(new StringReader(sentence));
		String token=null;
		StringTokenizer st=new StringTokenizer(sentence);
		while (st.hasMoreElements()){
			token=st.nextToken();
			token=tk.chunkTerm(token);
			rtt.write(" "+token);
		}
		rtt.write("።");
//		Token toke= new Token();
//		while ((toke = tk.next()) != null) {
//			rtt.write(toke.termText()+" ");
//
//		}
		rtt.close();
		
	}
	public String readFile(File dir)throws IOException{
		
		return null;
		
	}
//	public Token next
	public Token next() throws IOException {
		// return the first non-stop word found
		
	BufferedReader sbf= new BufferedReader(input);
	Amharic_Tokenizer tk= new Amharic_Tokenizer(sbf);
	String token=null;
	String termText=null;
	Token toke= new Token();
	while ((toke = tk.next()) != null) {
		return toke;

	}
//	while((token=sbf.readLine())!=null)
//		{
//			 //termText= chunkTerm(token);
//			//if (!stemmer.isStop(termText))
//				return termText;
//		}
//		// reached EOS -- return null
		return null;
	}
	public String chunkTerm(String term) throws IOException{
		StringBuffer sb1 = new StringBuffer();
		Amharic_Tokenizer tk= new Amharic_Tokenizer(new StringReader(term));
		int i=0;
		char ch;
		while(i<term.length())
			{
			ch=term.charAt(i);
			if(tk.isTokenChar(ch)){
				ch=tk.normalize(ch);
				sb1.append(ch);
			}
			else{
			//if(ch=='\n'||ch==' ')
				//sb1.append("\n");//rtt.write(sb1.toString());
			}
			i++;
			//System.out.println("dfad"+ch);
		}
		return sb1.toString();
	}
	
}
