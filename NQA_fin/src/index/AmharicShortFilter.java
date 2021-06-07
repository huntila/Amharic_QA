package index;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class AmharicShortFilter extends TokenFilter

{
	//private StopStemmer stemmer = null;
	public AmharicShortFilter(TokenStream input) {
		super(input);
		//stemmer=new StopStemmer();

	}

	public final String next(String tok) throws IOException {
			String term = tok;
			shortword expand = new shortword();
			String str = expand.Expander(term);
			// AmharicTokenizer tokenizer=new AmharicTokenizer();
			if (str.equals(term))
				return tok;
			else {			
					//Amharic_Tokenizer result = new Amharic_Tokenizer(new StringReader(str));
					//str=result.chunkTerm(str);
						return str;
						// return token;
					}
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		//AmharicShortFilter r=new AmharicShortFilter();
		FileInputStream fin = new FileInputStream(new File("c:/NQA/NFQA/T_N_doc/1.txt"));
		InputStreamReader read = new InputStreamReader(fin, "utf-8");
		BufferedReader rd = new BufferedReader(read);
		FileOutputStream fout = new FileOutputStream(new File("c:/NQA/NFQA/T_N_doc/expanded.txt"));
		OutputStreamWriter rt = new OutputStreamWriter(fout, "utf-8");
		BufferedWriter rtt = new BufferedWriter(rt);
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		String sentence = sb.toString();
		//String token2 = null;
		//Amharic_Tokenizer tk = new Amharic_Tokenizer(new StringReader(sentence));
		//StringTokenizer st=new StringTokenizer(sentence);
		Token token = null;
		TokenStream result = new Amharic_Tokenizer(new StringReader(sentence));
		result = new AmharicShortFilter(result);
		while ((token = ((AmharicShortFilter) result).next()) != null) {
			rtt.write(token.term() + " ");
               
		}
		rtt.close();

//		AmharicShortFilter	sf = new AmharicShortFilter(new TokenStream() {
//			public boolean incrementToken() throws IOException {
//					return false;
//			}
//		});
//		//System.out.println(sentence);
//		while (st.hasMoreElements()) {
//			token2=st.nextToken();
//			//token=tk.chunkTerm(token);
//			token2=sf.next(token).toString();
//			rtt.write(token2 + " ");
//		}
//		rtt.close();
	}

	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public final Token next() throws IOException {
		// return the first non-stop word found
		//Token token2 = null;
		Token token1 = null;
		for (Token token = ((AmharicShortFilter) input).next(); token != null; token = ((AmharicShortFilter) input).next()) {

			String term = token.term();
			shortword expand = new shortword();
			String str = expand.Expander(term);
			// AmharicTokenizer tokenizer=new AmharicTokenizer();
			if (str.equals(term))
				return token;
			else {

				int index = str.indexOf(" ");
				if (index == -1) {
					token1 = new Token(str, token.startOffset(), token.endOffset()- index, token.type());
					return token1;
				} else if (index > 0) {

					TokenStream result = new Amharic_Tokenizer(new StringReader(
							str));

					while ((token = ((AmharicShortFilter) result).next()) != null) {

						return token;

						// return token;
					}

				}

			}
			return null;
		}
		return null;
	}

}
