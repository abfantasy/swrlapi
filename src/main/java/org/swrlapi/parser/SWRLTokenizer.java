package org.swrlapi.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tokenizer generates a {@link SWRLParseException} for invalid input and a {@link SWRLIncompleteRuleException} (which
 * is a subclass of {@link SWRLParseException}) for valid but incomplete input.
 * 
 * @see SWRLParser
 */
public class SWRLTokenizer
{
	public final static char AND_CHAR = '\u2227'; // ^
	public final static char IMP_CHAR = '\u2192'; // >
	public final static char RING_CHAR = '\u02da'; // .

	private final StreamTokenizer tokenizer;

	private final Set<String> swrlVariables;
	private final boolean parseOnly;
	private final List<SWRLToken> tokens;
	private int tokenPosition;

	public SWRLTokenizer(String input, boolean parseOnly) throws SWRLParseException
	{
		this.tokenizer = new StreamTokenizer(new StringReader(input));
		this.tokenizer.parseNumbers();
		this.tokenizer.wordChars(':', ':');
		this.tokenizer.wordChars('_', '_');
		this.tokenizer.wordChars('/', '/');
		this.tokenizer.wordChars('#', '#');
		this.tokenizer.ordinaryChar(AND_CHAR);
		this.tokenizer.ordinaryChar(IMP_CHAR);
		this.tokenizer.ordinaryChar(RING_CHAR);
		this.tokenizer.ordinaryChar('-');
		this.tokenizer.ordinaryChar('.');
		this.tokenizer.ordinaryChar('^');
		this.tokenizer.ordinaryChar('<');
		this.tokenizer.ordinaryChar('>');
		this.tokenizer.ordinaryChar('(');
		this.tokenizer.ordinaryChar(')');
		this.tokenizer.ordinaryChar('?');

		// If we want to list:
		// this.tokenizer.ordinaryChar('!');
		// // Skip double quote
		// this.tokenizer.ordinaryChars('$', '/');
		// // Skip colon
		// this.tokenizer.ordinaryChars(';', '@');
		// this.tokenizer.ordinaryChars('[', '^');
		// // Skip underscore
		// this.tokenizer.ordinaryChar('`');
		// this.tokenizer.ordinaryChars('{', '~');

		this.swrlVariables = new HashSet<String>();
		this.parseOnly = parseOnly;

		this.tokens = generateTokens();
		this.tokenPosition = 0;
	}

	public void reset()
	{
		this.tokenPosition = 0;
	}

	public SWRLToken getToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			return this.tokens.get(tokenPosition++);
		else
			throw generateEndOfRuleException("Incomplete rule!");
	}

	public SWRLToken getToken(SWRLToken.SWRLTokenType expectedTokenType, String unexpectedTokenMessage)
			throws SWRLParseException
	{
		if (hasMoreTokens()) {
			SWRLToken token = getToken();

			if (token.getTokenType() == expectedTokenType)
				return token;
			else
				throw new SWRLParseException(unexpectedTokenMessage);
		} else
			throw generateEndOfRuleException(unexpectedTokenMessage);
	}

	public SWRLToken getToken(String noTokenMessage) throws SWRLParseException
	{
		if (hasMoreTokens())
			return getToken();
		else
			throw generateEndOfRuleException(noTokenMessage);
	}

	public boolean hasMoreTokens()
	{
		return this.tokenPosition < this.tokens.size();
	}

	public SWRLToken peekToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			return this.tokens.get(tokenPosition);
		else
			throw generateEndOfRuleException("End of rule reached!");
	}

	public void skipToken() throws SWRLParseException
	{
		if (this.tokenPosition < this.tokens.size())
			tokenPosition++;
		else
			throw generateEndOfRuleException("End of rule reached unexpectedly!");
	}

	public boolean isParseOnly()
	{
		return this.parseOnly;
	}

	public boolean hasVariable(String variableName)
	{
		return this.swrlVariables.contains(variableName);
	}

	public void addVariable(String variableName)
	{
		this.swrlVariables.add(variableName);
	}

	public void checkAndSkipToken(SWRLToken.SWRLTokenType tokenType, String unexpectedTokenMessage)
			throws SWRLParseException
	{
		if (hasMoreTokens()) {
			SWRLToken token = getToken();

			if (token.getTokenType() != tokenType)
				throw new SWRLParseException(unexpectedTokenMessage + ", got '" + token.getValue() + "'");
		} else
			throw generateEndOfRuleException(unexpectedTokenMessage);
	}

	public void checkAndSkipLParen(String unexpectedTokenMessage) throws SWRLParseException
	{
		checkAndSkipToken(SWRLToken.SWRLTokenType.LPAREN, unexpectedTokenMessage);
	}

	public void checkAndSkipRParen(String unexpectedTokenMessage) throws SWRLParseException
	{
		checkAndSkipToken(SWRLToken.SWRLTokenType.RPAREN, unexpectedTokenMessage);
	}

	public void checkAndSkipComma(String unexpectedTokenMessage) throws SWRLParseException
	{
		checkAndSkipToken(SWRLToken.SWRLTokenType.COMMA, unexpectedTokenMessage);
	}

	private List<SWRLToken> generateTokens() throws SWRLParseException
	{
		List<SWRLToken> tokens = new ArrayList<SWRLToken>();
		SWRLToken token = generateToken();
		while (token.getTokenType() != SWRLToken.SWRLTokenType.END_OF_INPUT) {
			tokens.add(token);
			token = generateToken();
		}
		return tokens;
	}

	private SWRLToken generateToken() throws SWRLParseException
	{
		try {
			return convertToken2SWRLToken(this.tokenizer.nextToken());
		} catch (IOException e) {
			throw new SWRLParseException("Error tokenizing " + e.getMessage());
		}
	}

	private SWRLToken convertToken2SWRLToken(int tokenType) throws SWRLParseException, IOException
	{
		switch (tokenType) {
		case StreamTokenizer.TT_EOF:
			return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
		case StreamTokenizer.TT_EOL:
			return new SWRLToken(SWRLToken.SWRLTokenType.END_OF_INPUT, "");
		case StreamTokenizer.TT_NUMBER: {
			double value = this.tokenizer.nval;
			if (value % 1 == 0.0) // Appears to be no way of determining whether double/float used originally
				return new SWRLToken(SWRLToken.SWRLTokenType.LONG, "" + this.tokenizer.nval);
			else
				return new SWRLToken(SWRLToken.SWRLTokenType.DOUBLE, "" + this.tokenizer.nval);
		}
		case StreamTokenizer.TT_WORD:
			return new SWRLToken(SWRLToken.SWRLTokenType.SHORTNAME, this.tokenizer.sval);
		case '"':
			return new SWRLToken(SWRLToken.SWRLTokenType.STRING, this.tokenizer.sval);
		case ',':
			return new SWRLToken(SWRLToken.SWRLTokenType.COMMA, ",");
		case '?':
			return new SWRLToken(SWRLToken.SWRLTokenType.QUESTION, ",");
		case '(':
			return new SWRLToken(SWRLToken.SWRLTokenType.LPAREN, "(");
		case ')':
			return new SWRLToken(SWRLToken.SWRLTokenType.RPAREN, ")");
		case '.':
		case RING_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.RING, ".");
		case '^':
		case AND_CHAR: {
			int nextTokenType = tokenizer.nextToken();
			if (nextTokenType == '^' || nextTokenType == AND_CHAR) {
				return new SWRLToken(SWRLToken.SWRLTokenType.TYPE_QUAL, "^^");
			} else { // Not ^^
				this.tokenizer.pushBack();
				return new SWRLToken(SWRLToken.SWRLTokenType.AND, "^");
			}
		}
		case '<': {
			int nextTokenType = this.tokenizer.nextToken();
			if (nextTokenType == StreamTokenizer.TT_WORD) {
				String iri = this.tokenizer.sval;
				nextTokenType = this.tokenizer.nextToken();
				if (nextTokenType == '>')
					return new SWRLToken(SWRLToken.SWRLTokenType.SHORTNAME, iri);
				else if (nextTokenType == StreamTokenizer.TT_EOF)
					throw generateEndOfRuleException("Expecting '>' after IRI");
				else
					throw new SWRLParseException("Expecting IRI after '<'");
			} else if (nextTokenType == StreamTokenizer.TT_EOF)
				throw generateEndOfRuleException("Expecting IRI after '<'");
			else
				throw new SWRLParseException("Expecting IRI after '<'"); // Some other token
		}
		case IMP_CHAR:
			return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
		case '-': {
			int nextTokenType = this.tokenizer.nextToken();
			if (nextTokenType == '>')
				return new SWRLToken(SWRLToken.SWRLTokenType.IMP, "->");
			else if (nextTokenType == StreamTokenizer.TT_EOF)
				throw generateEndOfRuleException("Expecting '>' after '-'");
			else
				throw new SWRLParseException("Expecting '>' after '-' for implication");
		}
		default:
			throw new SWRLParseException("Unexpected character '" + String.valueOf(Character.toChars(tokenType)) + "'");
		}
	}

	private SWRLParseException generateEndOfRuleException(String message)
	{
		if (!this.isParseOnly())
			return new SWRLParseException(message);
		else
			return new SWRLIncompleteRuleException(message);
	}
}