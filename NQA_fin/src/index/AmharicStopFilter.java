package index;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

//import com.sun.corba.se.spi.orbutil.fsm.Input;

public class AmharicStopFilter extends TokenFilter

{
	private StopStemmer stemmer = null;

	public AmharicStopFilter(TokenStream input) {
		super(input);
		stemmer = new StopStemmer();
	}
	public final Token next() throws IOException {
		// return the first non-stop word found
		for (Token token = ((AmharicStopFilter) input).next(); token != null; token = ((AmharicStopFilter) input).next()) {
			String termText = token.term();
            try {
                if (!stemmer.isStop(termText)) {
                    return token;
                }
            } catch (Exception ex) {
                Logger.getLogger(AmharicStopFilter.class.getName()).log(Level.SEVERE, null, ex);
            }

		}
		// reached EOS -- return null
		return null;
	}
	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	

//	public final String next2() throws IOException {
//		// return the first non-stop word found
//		String termText=null;
//		StringTokenizer st= new StringTokenizer(input.toString());
//		while(st.hasMoreElements()){
//			termText=st.nextToken();
//			if(!stemmer.isStop(termText))
//				return termText;
//		}
//		for (Token token = ((AmharicStopFilter) input).next(); token != null; token = ((AmharicStopFilter) input).next()) {
//			@SuppressWarnings("deprecation")
//			String termText = token.term();//.termText();
//			if (!stemmer.isStop(termText))
//				return token;
//		}
		// reached EOS -- return null
		//return null;
	//}
	//public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		//return false;
	//}
}
