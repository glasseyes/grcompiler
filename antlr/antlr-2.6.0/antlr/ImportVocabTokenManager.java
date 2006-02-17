package antlr;

/**
 * <b>SOFTWARE RIGHTS</b>
 * <p>
 * ANTLR 2.6.0 MageLang Institute
 * <p>
 * We reserve no legal rights to the ANTLR--it is fully in the
 * public domain. An individual or company may do whatever
 * they wish with source code distributed with ANTLR or the
 * code generated by ANTLR, including the incorporation of
 * ANTLR, or its output, into commerical software.
 * <p>
 * We encourage users to develop software with ANTLR. However,
 * we do ask that credit is given to us for developing
 * ANTLR. By "credit", we mean that if you use ANTLR or
 * incorporate any source code into one of your programs
 * (commercial product, research project, or otherwise) that
 * you acknowledge this fact somewhere in the documentation,
 * research report, etc... If you like ANTLR and have
 * developed a nice tool with the output, please mention that
 * you developed it using ANTLR. In addition, we ask that the
 * headers remain intact in our source code. As long as these
 * guidelines are kept, we expect to continue enhancing this
 * system and expect to make other tools available as they are
 * completed.
 * <p>
 * The ANTLR gang:
 * @version ANTLR 2.6.0 MageLang Institute
 * @author Terence Parr, <a href=http://www.MageLang.com>MageLang Institute</a>
 * @author <br>John Lilley, <a href=http://www.Empathy.com>Empathy Software</a>
 */
import java.io.*;
import java.util.Hashtable;
import java.util.Enumeration;
import antlr.collections.impl.Vector;

/** Static implementation of the TokenManager, used for importVocab option 
 */
class ImportVocabTokenManager extends SimpleTokenManager implements Cloneable {
	private String filename;
	protected Grammar grammar;

	ImportVocabTokenManager(Grammar grammar, String filename_, String name_, Tool tool_) {
		// initialize
		super(name_, tool_);
		this.grammar = grammar;
		filename = filename_;
		setReadOnly(true);

		// Read a file with lines of the form ID=number
		try {
			// SAS: changed the following for proper text io
			FileReader fileIn = new FileReader(filename);
			ANTLRTokdefLexer tokdefLexer = new ANTLRTokdefLexer(fileIn);
			ANTLRTokdefParser tokdefParser = new ANTLRTokdefParser(tokdefLexer);
			tokdefParser.setFilename(filename);
			tokdefParser.file(this);
		} 
		catch (ParserException ex) {
			tool.panic("Error parsing importVocab file '" + filename + "': " + ex.toString());
		}
		catch (IOException ex) {
			tool.panic("Error reading importVocab file '" + filename + "'");
		}
	}
	public Object clone() {
		ImportVocabTokenManager tm;
		tm = (ImportVocabTokenManager)super.clone();
		tm.filename = this.filename;
		tm.grammar = this.grammar;
		return tm;
	}
	/** define a token. */
	public void define(TokenSymbol ts) {
		super.define(ts);
	}
	/** define a token.  Intended for use only when reading the importVocab file. */
	public void define(String s, int ttype) {
		TokenSymbol ts=null;
		if ( s.startsWith("\"") ) {
			ts = new StringLiteralSymbol(s);
		}
		else {
			ts = new TokenSymbol(s);
		}	
		ts.setTokenType(ttype);
		super.define(ts);
		maxToken = (ttype+1)>maxToken ? (ttype+1) : maxToken;	// record maximum token type
	}
	/** importVocab token manager is read-only if output would be same as input */
	public boolean isReadOnly() {
		return readOnly;
	}
	/** Get the next unused token type. */
	public int nextTokenType() {
		return super.nextTokenType();	
	}
}