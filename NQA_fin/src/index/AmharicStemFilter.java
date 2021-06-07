package index;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

/**
 * A filter that stems Amharic words.
 */
public final class AmharicStemFilter extends TokenFilter {
	/**
	 * The actual token in the input stream
	 */
	private Token token = null;
	private AmharicStemmer stemmer = null;

	public AmharicStemFilter(TokenStream in) {
		super(in);
		stemmer = new AmharicStemmer();
	}

	/**
	 * @return Returns the next token in the stream, or null at EOS
	 */
	public final Token next() throws IOException {
		if ((token = ((AmharicStemFilter) input).next()) == null) {
			return null;
		} else {
			String s = stemmer.Stem(token.term());
			if (!s.equals(token.term())) {
				return new Token(s, token.startOffset(), token.endOffset(),
						token.type());
			}
			return token;
		}
	}

	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
}