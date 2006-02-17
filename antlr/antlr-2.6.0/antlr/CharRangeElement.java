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
class CharRangeElement extends AlternativeElement {
	String label;
	protected char begin=0;
	protected char end  =0;
	protected String beginText;
	protected String endText;


	public CharRangeElement(LexerGrammar g, Token t1, Token t2, int autoGenType) {
		super(g);
		begin = (char)ANTLRLexer.tokenTypeForCharLiteral(t1.getText());
		beginText = t1.getText();
		end   = (char)ANTLRLexer.tokenTypeForCharLiteral(t2.getText());
		endText = t2.getText();
		line = t1.getLine();
		// track which characters are referenced in the grammar
		for (int i=begin; i<=end; i++) {
			g.charVocabulary.add(i);
		}
		this.autoGenType = autoGenType;
	}
	public void generate() {
		grammar.generator.gen(this);
	}
	public String getLabel() {
		return label;
	}
	public Lookahead look(int k) {
		return grammar.theLLkAnalyzer.look(k, this);
	}
	public void setLabel(String label_) { 
		label = label_; 
	}
	public String toString() {
		if ( label!=null ) return " "+label+":"+beginText+".."+endText;
		else return " "+beginText+".."+endText;
	}
}